package com.mxc.contract.ws.dto.message;

import com.mxc.contract.ws.common.Channel;
import lombok.Data;

import java.math.BigDecimal;

@Data
@MessageEventDto(channel = Channel.PUSH_PERSONAL_RISK_LIMIT)
public class ContractUserRiskLimitDto implements Dto {
    /**
     * current risk level
     */
    private Integer level;
    /**
     * maximum position volume
     */
    private BigDecimal maxVol;
    /**
     * maximum leverage ratio
     */
    private Integer maxLeverage;
    /**
     * maintenance margin rate
     */
    private BigDecimal mmr;
    /**
     * initial margin rate
     */
    private BigDecimal imr;
    /**
     * the name of the contract
     */
    private String symbol;
    /**
     * 1:long，2:short
     */
    private int positionType;
}
