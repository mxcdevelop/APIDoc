package com.mxc.contract.ws.dto.message;

import com.mxc.contract.ws.common.Channel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@MessageEventDto(channel = Channel.PUSH_FUNDING_RATE)
public class FundingRateDto implements Dto {
    /**
     * the name of the contract
     */
    private String symbol;
    /**
     * funding rate
     */
    private BigDecimal rate;
    /**
     * next liquidate time
     */
    private Date nextSettleTime;
}
