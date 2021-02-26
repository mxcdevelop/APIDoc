package com.mxc.contract.ws;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.mxc.contract.ws.common.Channel;
import com.mxc.contract.ws.common.Command;
import com.mxc.contract.ws.common.Extensionkey;
import com.mxc.contract.ws.common.Interval;
import com.mxc.contract.ws.config.ApiConfig;
import com.mxc.contract.ws.dto.message.Dto;
import com.mxc.contract.ws.dto.message.MessageEventDto;
import com.mxc.contract.ws.dto.req.FilterDto;
import com.mxc.contract.ws.dto.req.SubscribeParam;
import com.mxc.contract.ws.event.MessageEvent;
import com.mxc.contract.ws.event.handler.MessageEventBus;
import com.mxc.contract.ws.utils.GZIPUtils;
import com.mxc.contract.ws.utils.SignatureUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.enums.ReadyState;
import org.java_websocket.handshake.ServerHandshake;
import org.reflections.Reflections;

import java.io.Closeable;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.mxc.contract.ws.common.ResponseConstants.RESPONSE_COMMON;
import static com.mxc.contract.ws.common.ResponseConstants.RESPONSE_SUCCESS;
import static com.mxc.contract.ws.config.ApiConfig.ACCESS_KEY;
import static com.mxc.contract.ws.config.ApiConfig.SECRET_KEY;


/**
 * wrapped WebSocketClient,apply ability to request server,and receive data from server and trigger message event
 */
@Slf4j
public class Client implements Closeable {
    private WebSocketClient webSocketClient;

    private final Map<Channel, DtoWrapper> channelDtoMap = new HashMap<>();
    private final MessageEventBus eventBus;
    /**
     * the fixed message keys
     */
    private final Set<String> commonMessageKeys = Sets.newHashSet("channel", "data", "ts");

    /**
     * the flag of signed in
     */
    private volatile AtomicBoolean authed = new AtomicBoolean(false);

    /**
     * whether server gzip data
     */
    private Boolean gzip = false;

    private volatile AtomicBoolean sendGzip = new AtomicBoolean(false);
    private ThreadPoolExecutor pool = new ThreadPoolExecutor(
            2,
            5,
            30L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000));

    public Client(MessageEventBus eventBus, Boolean gzip) {
        this.eventBus = eventBus;
        this.gzip = gzip;
        loadEventDtos();
        connect();
    }

    private void loadEventDtos() {
        Reflections reflections = new Reflections("com.mxc.contract.ws.dto");
        Set<Class<?>> set = reflections.getTypesAnnotatedWith(MessageEventDto.class);
        set.forEach(clazz -> {
            MessageEventDto annotation = clazz.getAnnotation(MessageEventDto.class);
            channelDtoMap.put(annotation.channel(), new DtoWrapper((Class<? extends Dto>) clazz, annotation.isList()));
        });
    }

    private void connect() {
        try {
            webSocketClient = initClient();
            webSocketClient.connect();
            while (!ReadyState.OPEN.equals(webSocketClient.getReadyState())) {
                //waiting
                Thread.sleep(50);
            }
            System.out.println("connect server success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //heart beat to keep alive
        pool.submit(() -> {
            try {
                while (true) {
                    sendRequest(new Request(Command.PING.getMethod(), null, this.gzip));
                    TimeUnit.SECONDS.sleep(30);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private WebSocketClient initClient() throws URISyntaxException {
        return new WebSocketClient(new URI(ApiConfig.WSS_URL)) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                System.out.println("server handshake status:" + serverHandshake.getHttpStatus());
            }

            @Override
            public void onMessage(String message) {
                handleMessage(message);
            }

            @Override
            public void onMessage(ByteBuffer message) {
                // if send gzip param , server push binary message only
                handleMessage(GZIPUtils.uncompressToString(message.array(), "utf-8"));
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                System.out.println("close client");
                if (webSocketClient != null && !webSocketClient.isOpen()) {
                    pool.submit(() -> {
                        ReadyState state = webSocketClient.getReadyState();
                        if (state.equals(ReadyState.NOT_YET_CONNECTED)) {
                            try {
                                webSocketClient.connectBlocking();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return;
                        }

                        if (state.equals(ReadyState.CLOSING)
                                || state.equals(ReadyState.CLOSED)) {
                            try {
                                webSocketClient.reconnectBlocking();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        };
    }

    /**
     * subscribe public channel
     *
     * @param command Command for subscribe
     * @param param
     */
    public void sub(Command command, SubscribeParam param) {
        sub(command, param, null);
    }

    /**
     * unsubscribe public channel
     *
     * @param command Command for unsubscribe
     * @param param
     */
    public void sub(Command command, SubscribeParam param, Boolean gzip) {
        sendRequest(new Request(command.getMethod(), param, gzip));
    }

    public void unsub(Command command, SubscribeParam param) {
        sendRequest(new Request(command.getMethod(), param, null));
    }

    /**
     * filter the channels that you want to received<br/>
     * if empty or null, all type and contact channels will be subscribed<br/>
     * if not empty only channels that contains in dtoList will will be subscribed
     *
     * @param dtoList
     */
    public void filterEvent(List<FilterDto> dtoList) {
        if (!authed.get()) {
            throw new IllegalStateException("did not signed in");
        }
        Map<String, Object> map = new HashMap<>(2);
        map.put("filters", dtoList);
        sendRequest(new Request(Command.FILTER.getMethod(), map, null));
    }

    /**
     * close client
     */
    @Override
    public void close() {
        if (webSocketClient.isClosed()) {
            webSocketClient.close();
        }
    }

    private void sendRequest(Request request) {
        if (webSocketClient.isClosed()) {
            throw new IllegalStateException("client is closed");
        }
        String param = JSONObject.toJSONString(request);
        //System.out.println("request param:" + param);
        pool.submit(() -> webSocketClient.send(param));
    }

    /**
     * @param subscribe if true or null subscribe all personal data channels,or subscribe none personal data channel
     */
    public void login(Boolean subscribe) {
        SignatureUtils.SignVo signVo = new SignatureUtils.SignVo();
        long epochSecond = System.currentTimeMillis();
        signVo.setReqTime(epochSecond + "");
        signVo.setSecretKey(SECRET_KEY);
        signVo.setAccessKey(ACCESS_KEY);
        signVo.setRequestParam(null);
        Map<String, Object> map = new HashMap<>();
        map.put("method", "login");
        if (subscribe != null) {
            map.put("subscribe", subscribe);
        }
        Map<String, String> jsonObject = new HashMap<>();
        jsonObject.put("apiKey", ACCESS_KEY);
        jsonObject.put("reqTime", "" + epochSecond);
        jsonObject.put("signature", SignatureUtils.signature(signVo));
        map.put("param", jsonObject);
        webSocketClient.send(JSONObject.toJSONString(map));
    }

    private void handleMessage(String message) {
        JSONObject jsonOfMessage = JSONObject.parseObject(message);
        String channel = jsonOfMessage.getString("channel");
        // command response
        if (channel.startsWith(RESPONSE_COMMON)) {
            commandResponseHandle(message, jsonOfMessage, channel);
            return;
        }

        //ignore
        if (Command.RESPONSE_PONG.getMethod().equals(channel)) {
            return;
        }

        Optional<Channel> channelOptional = Channel.of(channel);
        //unknown data
        if (!channelOptional.isPresent()) {
            System.out.println("unknown data:" + message);
            return;
        }
        //push data
        pushDataHandle(message, jsonOfMessage, channelOptional.get());
    }

    private void commandResponseHandle(String message, JSONObject jsonOfMessage, String channel) {
        if (Channel.RESPONSE_ERROR.getChannel().equals(channel)) {
            System.out.println("command handle error:" + message);
            return;
        }

        String data = jsonOfMessage.getString("data");
        //login
        if (Command.RESPONSE_LOGIN.getMethod().equals(channel)) {
            if (RESPONSE_SUCCESS.equals(data)) {
                authed.compareAndSet(false, true);
            }
            return;
        }

        //ignore other success response
        System.out.println(message);
    }

    private void pushDataHandle(String message, JSONObject jsonOfMessage, Channel type) {
        Optional.ofNullable(channelDtoMap.get(type)).ifPresent(dtoWrapper -> {
            MessageEvent messageEvent = new MessageEvent();
            messageEvent.setType(type);
            messageEvent.setTs(jsonOfMessage.getLong("ts"));
            Dto dto = dtoWrapper.isListData() ? JSONObject.parseObject(message, dtoWrapper.getClazz()) : jsonOfMessage.getJSONObject("data").toJavaObject(dtoWrapper.getClazz());
            messageEvent.setData(dto);
            Set<String> keys = jsonOfMessage.keySet();
            if (keys.size() > commonMessageKeys.size()) {
                keys.forEach(key -> {
                    if (commonMessageKeys.contains(key)) {
                        return;
                    }
                    Extensionkey.of(key)
                            .ifPresent(extensionkey -> messageEvent.putExtension(extensionkey, jsonOfMessage.get(key)));
                });
            }
            eventBus.trigger(messageEvent);
        });
    }

    @Data
    @AllArgsConstructor
    private class DtoWrapper {
        private Class<? extends Dto> clazz;
        private boolean listData;
    }

    @Data
    @AllArgsConstructor
    private class Request {
        private String method;
        private Object param;
        private Boolean gzip;
    }


    public static void main(String[] args) {
        MessageEventBus eventBus = new MessageEventBus(true);
        Client client = new Client(eventBus, false);
        client.sub(Command.SUB_KLINE, new SubscribeParam("BTC_USDT", Interval.Min15));
        client.sub(Command.SUB_FAIR_PRICE, new SubscribeParam("BTC_USDT"));
        client.sub(Command.SUB_INDEX_PRICE, new SubscribeParam("BTC_USDT"));
        client.sub(Command.SUB_FUNDING_RATE, new SubscribeParam("BTC_USDT"));
        client.sub(Command.SUB_DEAL, new SubscribeParam("BTC_USDT"));
        client.sub(Command.SUB_DEPTH, new SubscribeParam("BTC_USDT", false));
        client.sub(Command.SUB_FULL_DEPTH, new SubscribeParam("BTC_USDT", 10));
        client.sub(Command.SUB_TICKERS, null);
        client.sub(Command.SUB_TICKER, new SubscribeParam("BTC_USDT"));
    }
}
