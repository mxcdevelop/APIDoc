package com.mxc.contract.demo.response.identified;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OrderRespDTO {
    private String orderId;
    private String symbol;
    private Integer positionId;
    //委托价格
    private BigDecimal price;
    //委托数量
    private BigDecimal vol;
    //杠杆倍数
    private Long leverage;
    //订单方向 1开多,2平空,3开空,4平多
    private Integer side;
    //订单类别:1限价委托,2强平代管委托,4ADL减仓
    private Integer category;
    //1:限价单,2:Post Only只做Maker,3:立即成交或立即取消,4:全部成交或者全部取消，5:市价单,6:市价转现价
    private Integer orderType;
    //成交平均价格
    private BigDecimal dealAvgPrice;
    //成交数量
    private BigDecimal dealVol;
    //委托保证金
    private BigDecimal orderMargin;
    //买单手续费
    private BigDecimal takerFee;
    //卖单手续费
    private BigDecimal makerFee;
    //平仓盈亏
    private BigDecimal profit;
    private String feeCurrency;
    //开仓类型,1逐仓,2全仓
    private Integer openType;
    //订单状态,1:待报,2未完成,3已完成,4已撤销,5无效
    private Integer state;
    //外部订单号
    private String externalOid;
    //错误code,0:正常，1：参数错误，2：账户余额不足，3：仓位不存在，4：仓位可用持仓不足，
    // 5：多仓时， 委托价小于了强平价，空仓时， 委托价大于了强平价，
    // 6：开多时， 强平价大于了合理价，开空时， 强平价小于了合理价 ,7:超过风险限额限制，8：系统撤销
    private Integer errorCode;
    //已经使用的保证金
    private Integer usedMargin;
    private Long createTime;
    private Long updateTime;
}
