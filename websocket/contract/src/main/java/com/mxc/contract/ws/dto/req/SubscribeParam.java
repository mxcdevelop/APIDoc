package com.mxc.contract.ws.dto.req;

import com.mxc.contract.ws.common.Interval;
import lombok.Getter;

@Getter
public class SubscribeParam {
    /**
     * the name of the contract
     */
    private String symbol;
    /**
     * limit value ,for full-depth channel
     */
    private Integer limit;
    /**
     * whether compress, for depth channel
     */
    private Boolean compress;
    /**
     * interval {@link Interval}, for k-line channel
     */
    private Interval interval;


    public SubscribeParam(String symbol) {
        this.symbol = symbol;
    }

    public SubscribeParam(String symbol, Integer limit) {
        this.symbol = symbol;
        this.limit = limit;
    }

    public SubscribeParam(String symbol, Boolean compress) {
        this.symbol = symbol;
        this.compress = compress;
    }

    public SubscribeParam(String symbol, Interval interval) {
        this.symbol = symbol;
        this.interval = interval;
    }
}
