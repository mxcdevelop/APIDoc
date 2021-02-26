package com.mxc.contract.ws.event.handler;

import com.alibaba.fastjson.JSONObject;
import com.mxc.contract.ws.common.Channel;
import com.mxc.contract.ws.event.MessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * the dispatcher for message event
 */
@Slf4j
public class MessageEventBus {
    private Map<Channel, List<MessageEventHandler>> handlers = new ConcurrentHashMap<>();


    private MessageEventBus() {

    }

    /**
     * @param autoScanHandlers whether scan the implements of MessageEventHandler
     */
    public MessageEventBus(boolean autoScanHandlers) {
        if (autoScanHandlers) {
            scanHandlers();
        }
    }

    private void scanHandlers() {
        Reflections reflections = new Reflections("com.mxc.contract.ws.event");
        Set<Class<? extends MessageEventHandler>> handlerClazz = reflections.getSubTypesOf(MessageEventHandler.class);
        handlerClazz.forEach(clazz -> {
            try {
                Constructor<? extends MessageEventHandler> constructor = clazz.getConstructor();
                MessageEventHandler handler = constructor.newInstance();
                Channel channel = handler.getEventType();
                List<MessageEventHandler> handlers = this.handlers.computeIfAbsent(channel, type -> new ArrayList<>());
                handlers.add(handler);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * register handler for message event from channel
     *
     * @param channel
     * @param handler
     */
    public void subscribe(Channel channel, MessageEventHandler handler) {
        List<MessageEventHandler> handlers = this.handlers.computeIfAbsent(channel, c -> new ArrayList<>());
        handlers.add(handler);
    }

    /**
     * unregister handler for message event from channel
     *
     * @param channel
     * @param handler
     */
    public void unsubscribe(Channel channel, MessageEventHandler handler) {
        List<MessageEventHandler> handlers = this.handlers.computeIfAbsent(channel, c -> new ArrayList<>());
        handlers.remove(handler);
    }

    /**
     * trigger event and loop handle it
     *
     * @param messageEvent
     */
    public void trigger(MessageEvent messageEvent) {
        List<MessageEventHandler> handlerList = handlers.get(messageEvent.getType());
        if (CollectionUtils.isEmpty(handlerList)) {
            System.out.println("no handlers for chnnel:" + messageEvent.getType().getChannel() + " event:" + JSONObject.toJSONString(messageEvent));
            return;
        }
        handlerList.forEach(handler -> handler.handle(messageEvent));
    }
}
