package com.mxc.contract.ws.event.handler;

import com.alibaba.fastjson.JSONObject;
import com.mxc.contract.ws.common.Channel;
import com.mxc.contract.ws.dto.message.DepthDto;
import com.mxc.contract.ws.event.MessageEvent;
import com.sun.tools.javac.util.Assert;

/**
 * demo for handling message event
 */
public class DemoMessageEventHandler implements MessageEventHandler {
    @Override
    public Channel getEventType() {
        return Channel.PUSH_DEPTH;
    }

    @Override
    public void handle(MessageEvent event) {
        Assert.check(getEventType().equals(event.getType()));
        DepthDto dto = event.getData();
        //do what you want
        System.out.println("trigger event:" + JSONObject.toJSONString(event));
    }
}
