package com.mxc.contract.demo.response.open;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ContractFundingRateResp {
    //合约名
    private String symbol;
    //资金费率
    private BigDecimal fundingRate;
    //资金费率上限
    private BigDecimal maxFundingRate;
    //资金费率下限
    private BigDecimal minFundingRate;
    //收取周期
    private Integer collectCycle;
    //下次收取时间
    private Long nextSettleTime;
    //系统时间戳
    private Long timestamp;
}
