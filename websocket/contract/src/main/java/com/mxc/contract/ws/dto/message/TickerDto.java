package com.mxc.contract.ws.dto.message;

import com.mxc.contract.ws.common.Channel;
import lombok.Data;

import java.math.BigDecimal;


@Data
@MessageEventDto(channel = Channel.PUSH_TICKER)
public class TickerDto implements Dto {
    /**
     * the name of the contract
     */
    private String symbol;
    /**
     * the last price
     */
    private BigDecimal lastPrice;
    /**
     * rise/fall rate
     */
    private BigDecimal riseFallRate;
    /**
     * fair price
     */
    private BigDecimal fairPrice;
    /**
     * index price
     */
    private BigDecimal indexPrice;
    /**
     * 24 hours trading volume, according to the statistics count
     */
    private BigDecimal volume24;
    /**
     * max price of bidding
     */
    private BigDecimal maxBidPrice;
    /**
     * min price of asking
     */
    private BigDecimal minAskPrice;

    private long timestamp;
}
