package com.mxc.contract.ws.dto.message;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.mxc.contract.ws.common.Channel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@MessageEventDto(channel = Channel.PUSH_PERSONAL_STOP_PLAN_ORDER)
public class ContractStopPlanOrderDto implements Dto {
    private Long id;
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long orderId;
    /**
     * the name of the contract
     */
    private String symbol;
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long positionId;
    /**
     * stop-loss price
     */
    private BigDecimal stopLossPrice;
    /**
     * take-profit price
     */
    private BigDecimal takeProfitPrice;
    /**
     * state:,0: untriggered,3: executed, 5: cancelled, 6: execution failed
     */
    private int state;
    /**
     * trigger direction, 0: untriggered , 1: taker-profit , 2: stop-loss
     */
    private int triggerSide;
    /**
     * position type,1: long, 2: short
     */
    private int positionType;

    /**
     * trigger volume
     */
    private BigDecimal vol;
    /**
     * actual number of orders
     */
    private BigDecimal realityVol;
    /**
     * placed order id
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long placeOrderId;
    /**
     * 0: normal, other reference errorCode
     */
    private Integer errorCode;
    private Integer version;
    /**
     * whether the order status is the end-state identifier (for query),0. Non-terminal, 1. Terminal
     */
    private Integer isFinished;
    private Date createTime;
    private Date updateTime;
}
