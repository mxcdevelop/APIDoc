package com.mxc.contract.demo.request.identified;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlanOrderReq {
    private String symbol;
    private BigDecimal price;
    private BigDecimal vol;

    private Integer leverage;
    //订单方向, 1开多,3开空
    private Integer side;
    //开仓类型,1:逐仓,2:全仓
    private Integer openType;
    //触发价
    private BigDecimal triggerPrice;
    //触发类型,1:大于等于，2:小于等于
    private Integer triggerType;
    //执行周期,单位:小时
    private Integer executeCycle;
    //订单类型,1:限价单,2:Post Only只做Maker,3:立即成交或立即取消,4:全部成交或者全部取消,5:市价单
    private Integer orderType;
    //触发价格类型,1:最新价，2:合理价，3:指数价
    private Integer trend;
}
