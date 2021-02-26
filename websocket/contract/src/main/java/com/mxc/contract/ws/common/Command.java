package com.mxc.contract.ws.common;

/**
 * command for request server
 */
public enum Command {
    //request
    FILTER("personal.filter"),
    PING("ping"),

    //response
    RESPONSE_PONG("pong"),
    RESPONSE_LOGIN("rs.login"),
    
    //subscribe
    SUB_KLINE("sub.kline"),
    SUB_FAIR_PRICE("sub.fair.price"),
    SUB_INDEX_PRICE("sub.index.price"),
    SUB_FUNDING_RATE("sub.funding.rate"),
    SUB_DEAL("sub.deal"),
    SUB_DEPTH("sub.depth"),
    SUB_FULL_DEPTH("sub.depth.full"),
    SUB_TICKERS("sub.tickers"),
    SUB_TICKER("sub.ticker"),

    //unsubscribe
    UNSUB_KLINE("unsub.kline"),
    UNSUB_FAIR_PRICE("unsub.fair.price"),
    UNSUB_INDEX_PRICE("unsub.index.price"),
    UNSUB_FUNDING_RATE("unsub.funding.rate"),
    UNSUB_DEAL("unsub.deal"),
    UNSUB_DEPTH("unsub.depth"),
    UNSUB_FULL_DEPTH("unsub.depth.full"),
    UNSUB_TICKERS("unsub.tickers"),
    UNSUB_TICKER("unsub.ticker"),

    ;
    String method;

    Command(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
