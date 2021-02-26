package com.mxc.contract.ws.dto.message;

import com.mxc.contract.ws.common.Interval;
import lombok.Data;

import java.math.BigDecimal;

import static com.mxc.contract.ws.common.Channel.PUSH_KLINE;


@Data
@MessageEventDto(channel = PUSH_KLINE)
public class KLineDto implements Dto {

    /**
     * the name of the contract
     */
    private String symbol;

    /**
     * interval: Min1、Min5、Min15、Min30、Min60、Hour4、Hour8、Day1、Week1、Month1
     */
    private Interval interval;

    /**
     * trading time，unit：second（s）， the start time of the window（windowStart）
     */
    private Long t;

    /**
     * the opening price
     */
    private BigDecimal o;

    /**
     * the closing price
     */
    private BigDecimal c;

    /**
     * the highest price
     */
    private BigDecimal h;

    /**
     * the lowest price
     */
    private BigDecimal l;

    /**
     * total transaction amount
     */
    private BigDecimal a;

    /**
     * total transaction volume
     */
    private BigDecimal q;
}
