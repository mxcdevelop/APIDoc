package com.mxc.contract.demo.response.identified;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class RiskLimitDTO {
    private Integer positionType;
    private Integer level;
    private BigDecimal maxVol;
    private Integer maxLeverage;
    //维持保证金率
    private BigDecimal mmr;
    //初始保证金率
    private BigDecimal imr;
    private String symbol;
}
