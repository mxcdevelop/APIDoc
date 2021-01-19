package com.mxc.contract.demo.response.identified;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class AccountAssetDTO {
    private String currency;
    private BigDecimal positionMargin;
    private BigDecimal availableBalance;
    private BigDecimal cashBalance;
    private BigDecimal frozenBalance;
    private BigDecimal equity;
    private BigDecimal unrealized;
    private BigDecimal bonus;
}
