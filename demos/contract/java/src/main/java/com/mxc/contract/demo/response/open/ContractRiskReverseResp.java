package com.mxc.contract.demo.response.open;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class ContractRiskReverseResp {

    //合约名
    private String symbol;

    //结算货币
    private String currency;

    //余额
    private BigDecimal available;

    //系统时间戳
    private Long timestamp;
}
