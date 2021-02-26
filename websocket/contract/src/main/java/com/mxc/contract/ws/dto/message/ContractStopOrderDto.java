package com.mxc.contract.ws.dto.message;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.mxc.contract.ws.common.Channel;
import lombok.Data;

import java.math.BigDecimal;

@Data
@MessageEventDto(channel = Channel.PUSH_PERSONAL_STOP_ORDER)
public class ContractStopOrderDto implements Dto {
    /**
     * the name of the contract
     */
    private String symbol;
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long orderId;
    /**
     * stop-loss price
     */
    private BigDecimal stopLossPrice;
    /**
     * take-profit price
     */
    private BigDecimal takeProfitPrice;
}
