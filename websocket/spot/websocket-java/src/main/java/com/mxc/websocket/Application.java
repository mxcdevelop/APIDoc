package com.mxc.websocket;

import com.alibaba.fastjson.JSONObject;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.util.Arrays;

public class Application {
    public static void main(String[] args) {
        String url = "https://www.mxc.com";
        try {
            IO.Options options = new IO.Options();
            options.transports = new String[]{"websocket","polling"};
            //失败重试次数
            options.reconnectionAttempts = 500;
            //失败重连的时间间隔
            options.reconnectionDelay = 6000;
            //连接超时时间(ms)
            options.timeout = 6000;
            final Socket socket = IO.socket(url, options);

            socket.on(Socket.EVENT_CONNECTING, objects -> System.out.println("client: " + "连接中"));
            socket.on(Socket.EVENT_CONNECT_TIMEOUT, objects -> System.out.println("client: " + "连接超时"));
            socket.on(Socket.EVENT_CONNECT_ERROR, objects -> System.out.println("client: " + "连接失败"));

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("symbol","ETH_USDT");

            //监听自定义msg事件,获取币种信息
            socket.on("rs.symbol", objects -> System.out.println("client: 收到msg->" + Arrays.toString(objects)));
            socket.emit("get.symbol", jsonObject);

            //监听交易信息
            socket.on("push.symbol", objects -> System.out.println("client: 收到msg->" + Arrays.toString(objects)));
            socket.emit("sub.symbol",jsonObject);



            socket.connect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
