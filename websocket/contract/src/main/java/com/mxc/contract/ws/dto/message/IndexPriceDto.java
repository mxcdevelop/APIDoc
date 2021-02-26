package com.mxc.contract.ws.dto.message;

import com.mxc.contract.ws.common.Channel;
import lombok.Data;

import java.math.BigDecimal;

@Data
@MessageEventDto(channel = Channel.PUSH_INDEX_PRICE)
public class IndexPriceDto implements Dto {
    /**
     * the name of the contract
     */
    private String symbol;
    /**
     * price
     */
    private BigDecimal price;
}
