package com.mxc.contract.ws.dto.message;

import com.mxc.contract.ws.common.Channel;
import lombok.Data;

import java.math.BigDecimal;

@Data
@MessageEventDto(channel = Channel.PUSH_PERSONAL_ASSET)
public class AssetChangeDto implements Dto {
    private String currency;
    /**
     * available balance
     */
    private BigDecimal availableBalance;
    /**
     * frozen balance
     */
    private BigDecimal frozenBalance;
    /**
     * position margin
     */
    private BigDecimal positionMargin;
    /**
     * reward
     */
    private BigDecimal bonus;

}
