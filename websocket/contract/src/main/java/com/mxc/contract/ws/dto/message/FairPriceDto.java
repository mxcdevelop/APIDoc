package com.mxc.contract.ws.dto.message;

import com.mxc.contract.ws.common.Channel;
import lombok.Data;

import java.math.BigDecimal;

@Data
@MessageEventDto(channel = Channel.PUSH_FAIR_PRICE)
public class FairPriceDto implements Dto {
    /**
     * the name of the contract
     */
    private String symbol;

    private BigDecimal price;
}
