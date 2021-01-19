package com.mxc.contract.demo.response.identified;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class PositionDTO {
    private Long positionId;
    private String symbol;
    //仓位类型， 1多 2空
    private Integer positionType;
    //开仓类型， 1逐仓 2全仓
    private Integer openType;
    //仓位状态,1持仓中2系统代持3已平仓
    private Integer state;
    private BigDecimal holdVol;
    //冻结量
    private BigDecimal frozenVol;
    //平仓量
    private BigDecimal closeVol;
    //持仓均价
    private BigDecimal holdAvgPrice;
    //开仓均价
    private BigDecimal openAvgPrice;
    //平仓均价
    private BigDecimal closeAvgPrice;
    //逐仓时的爆仓价
    private BigDecimal liquidatePrice;
    //原始初始保证金
    private BigDecimal oim;
    //初始保证金， 逐仓时可以加减此项以调节爆仓价
    private Integer im;
    //资金费, 正数表示得到，负数表示支出
    private BigDecimal holdFee;
    //已实现盈亏
    private BigDecimal realised;
    //杠杆率
    private Integer leverage;
    private Long createTime;
    private Long updateTime;
    private Boolean autoAddIm;
}
