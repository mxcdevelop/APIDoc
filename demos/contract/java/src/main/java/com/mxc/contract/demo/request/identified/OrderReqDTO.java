package com.mxc.contract.demo.request.identified;

import lombok.Data;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Data
public class OrderReqDTO {
    private String symbol;
    private BigDecimal price;
    private BigDecimal vol;
    private Integer leverage;
    private Integer side;
    //订单类型,1:限价单,2:Post Only只做Maker,3:立即成交或立即取消,4:全部成交或者全部取消,5:市价单,6:市价转现价
    private Integer type;
    //开仓类型,1:逐仓,2:全仓
    private Integer openType;
    @Nullable
    private String externalOid;
    //止损价
    @Nullable
    private BigDecimal stopLossPrice;
    //止盈价
    @Nullable
    private BigDecimal takeProfitPrice;
}
