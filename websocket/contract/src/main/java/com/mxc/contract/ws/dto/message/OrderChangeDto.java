package com.mxc.contract.ws.dto.message;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.mxc.contract.ws.common.Channel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@MessageEventDto(channel = Channel.PUSH_PERSONAL_ORDER)
public class OrderChangeDto implements Dto {
    /**
     * orderid
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long orderId;
    /**
     * position id
     */
    private Long positionId;
    /**
     * open type,1:isolated,2:cross
     */
    private Integer openType;
    /**
     * order side :1 open ,2 close ,3 open , 4 close
     */
    private Integer side;
    private BigDecimal price;
    /**
     * trigger volume
     */
    private BigDecimal vol;
    /**
     *
     */
    private BigDecimal remainVol;
    /**
     * transaction average price
     */
    private BigDecimal dealAvgPrice;
    /**
     * transaction volume
     */
    private BigDecimal dealVol;
    private Integer state;
    private Long version;
    /**
     * order category:1 limit order, 2 system take-over delegate, 3 close delegate 4 ADL reduction
     */
    private int category;
    /**
     * 1 limit order,2 post only ,3 immediate or cancel, 4 fill or kill,5 market,6 market limit
     */
    private Integer orderType;
    /**
     * the name of the contract
     */
    private String symbol;
    /**
     * order margin
     */
    private BigDecimal orderMargin;
    /**
     * taker fee
     */
    private BigDecimal takerFee;
    /**
     * maker fee
     */
    private BigDecimal makerFee;
    /**
     * close profit
     */
    private BigDecimal profit;
    /**
     * fee currency
     */
    private String feeCurrency;
    /**
     * used margin
     */
    private BigDecimal usedMargin;
    /**
     * leverage
     */
    private Integer leverage;
    /**
     * error code,0:normal，
     * 1：parameter errors，
     * 2：account balance is insufficient，
     * 3：the position does not exist，
     * 4： position insufficient，
     * 5：For long positions, the order price is less than the close price, while for short positions, the order price is more than the close rice，
     * 6：When opening long, the close price is more than the fair price, while when opening short, the close price is less than the fair price ,
     * 7:exceed the risk limit，
     * 8:system cancelled
     */
    private Integer errorCode;
    /**
     * external order id
     */
    private String externalOid;
    private Integer marketOrderLevel;
    private Date createTime;
    private Date updateTime;
}
