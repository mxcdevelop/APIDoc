package com.mxc.contract.demo.response.identified;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StopOrderDetailsResp {
    private Integer id;
    //止盈止损订单id
    private Long stopOrderId;
    //仓位id
    private String positionId;
    private String orderId;
    private String symbol;
    private Integer stopLossPrice;
    private Integer takeProfitPrice;
    private Integer state;
    private Integer vol;
    private BigDecimal realityVol;
    private BigDecimal triggerPrice;
    //下单id
    private Long placeOrderId;
    private Integer errorCode;
    //仓位类型,1:多仓，2:空仓
    private Integer positionType;
    private Long createTime;
    private Long updateTime;
}
