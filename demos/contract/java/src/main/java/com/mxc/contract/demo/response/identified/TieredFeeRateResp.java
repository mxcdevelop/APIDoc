package com.mxc.contract.demo.response.identified;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class TieredFeeRateResp {
    private Integer level;
    private BigDecimal dealAmount;
    private BigDecimal walletBalance;
    private BigDecimal makerFee;
    private BigDecimal takerFee;
    private BigDecimal makerFeeDiscount;
    private BigDecimal takerFeeDiscount;
}
