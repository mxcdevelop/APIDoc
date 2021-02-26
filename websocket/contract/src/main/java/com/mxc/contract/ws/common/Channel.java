package com.mxc.contract.ws.common;

import org.apache.commons.lang3.EnumUtils;

import java.util.Optional;

/**
 * enum for push message channel
 */
public enum Channel {
    //public channel
    PUSH_KLINE("push.kline"),
    PUSH_FAIR_PRICE("push.fair.price"),
    PUSH_INDEX_PRICE("push.index.price"),
    PUSH_FUNDING_RATE("push.funding.rate"),
    PUSH_DEAL("push.deal"),
    PUSH_DEPTH("push.depth"),
    PUSH_FULL_DEPTH("push.depth.full"),
    PUSH_TICKERS("push.tickers"),
    PUSH_TICKER("push.ticker"),
    //private channel
    PUSH_PERSONAL_ORDER("push.personal.order"),
    PUSH_PERSONAL_ASSET("push.personal.asset"),
    PUSH_PERSONAL_POSITION("push.personal.position"),
    PUSH_PERSONAL_PLANORDER("push.personal.plan.order"),
    PUSH_PERSONAL_RISK_LIMIT("push.personal.risk.limit"),
    PUSH_PERSONAL_ADL_LEVEL("push.personal.adl.level"),
    PUSH_PERSONAL_STOP_ORDER("push.personal.stop.order"),
    PUSH_PERSONAL_STOP_PLAN_ORDER("push.personal.stop.planorder"),
    PUSH_PERSONAL_ORDER_DEAL("push.personal.order.deal"),


    //response
    RESPONSE_ERROR("rs.error");
    String channel;

    Channel(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }

    public static Optional<Channel> of(String channel) {
        return EnumUtils.getEnumList(Channel.class).stream().filter(eventType -> eventType.channel.equals(channel)).findFirst();
    }
}
