package com.mxc.contract.ws.event.handler;

import com.mxc.contract.ws.common.Channel;
import com.mxc.contract.ws.event.MessageEvent;

/**
 * interface for handle message event
 */
public interface MessageEventHandler {
    /**
     * the target channel
     *
     * @return
     */
    Channel getEventType();

    void handle(MessageEvent event);
}
