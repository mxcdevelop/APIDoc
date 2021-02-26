package com.mxc.contract.ws.dto.message;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.mxc.contract.ws.common.Channel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@MessageEventDto(channel = Channel.PUSH_PERSONAL_PLANORDER)
public class ContractPlanOrderDto implements Dto {
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;
    /**
     * the name of the contract
     */
    private String symbol;
    private Integer leverage;
    /**
     * order side :1 open ,2 close ,3 open , 4 close
     */
    private int side;

    /**
     * trigger price
     */
    private BigDecimal triggerPrice;
    /**
     * execute price
     */
    private BigDecimal price;
    /**
     * order volume
     */
    private BigDecimal vol;
    /**
     * open type,1:isolated,2:cross
     */
    private int openType;
    /**
     * trigger type,1: more than or equal, 2: less than or equal
     */
    private int triggerType;
    /**
     * status,1: untriggered, 2: cancelled, 3: executed,4: invalid,5: execution failed
     */
    private int state;
    /**
     * execution cycle, unit: hours
     */
    private Integer executeCycle;
    /**
     * trigger price type,1: latest price, 2: fair price, 3: index price
     */
    private int trend;
    /**
     * order type,1: limit order,2:Post Only Maker,3: close or cancel instantly 4: close or cancel completely,5: Market order
     */
    private int orderType;
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long orderId;
    /**
     * error code on failed execution, 0: normal
     */
    private Integer errorCode;
    private Integer marketOrderLevel;
    private Date createTime;
    private Date updateTime;
}
