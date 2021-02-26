package com.mxc.contract.ws.dto.message;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.mxc.contract.ws.common.Channel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@MessageEventDto(channel = Channel.PUSH_PERSONAL_ORDER_DEAL)
public class OrderDealDetailDto implements Dto {
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;
    /**
     * the name of the contact
     */
    private String symbol;
    /**
     * order direction 1 open,2 close,3 open,4 close
     */
    private Integer side;
    /**
     * transaction volume
     */
    private BigDecimal vol;
    /**
     * transaction price
     */
    private BigDecimal price;
    /**
     * currency
     */
    private String feeCurrency;
    private BigDecimal fee;
    private Date timestamp;
    private BigDecimal profit;
    /**
     * is it taker order
     */
    private boolean isTaker;
    /**
     * order category:1limit order, 2 system take-over delegate, 3 close delegate 4 ADL reduction
     */
    private int category;
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long orderId;
}
