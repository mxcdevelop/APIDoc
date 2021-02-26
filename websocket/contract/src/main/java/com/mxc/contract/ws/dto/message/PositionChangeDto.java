package com.mxc.contract.ws.dto.message;

import com.mxc.contract.ws.common.Channel;
import lombok.Data;

import java.math.BigDecimal;

@Data
@MessageEventDto(channel = Channel.PUSH_PERSONAL_POSITION)
public class PositionChangeDto implements Dto {
    private Long positionId;
    /**
     * the name of the contract
     */
    private String symbol;
    /**
     * position state,1 holding ,2 system holding ,3 closed
     */
    private Integer state;
    /**
     * hold volume
     */
    private BigDecimal holdVol;
    /**
     * frozen volume
     */
    private BigDecimal frozenVol;
    /**
     * close volume
     */
    private BigDecimal closeVol;
    /**
     * hold average price
     */
    private BigDecimal holdAvgPrice;
    /**
     * open average price
     */
    private BigDecimal openAvgPrice;
    /**
     * close average price
     */
    private BigDecimal closeAvgPrice;
    /**
     * liquidate price
     */
    private BigDecimal liquidatePrice;
    /**
     * original initial margin
     */
    private BigDecimal oim;
    /**
     * initial margin， add or subtract this item can be used to adjust the liquidate price
     */
    private BigDecimal im;
    /**
     * hold fee, positive means u get it, negative means lose it
     */
    private BigDecimal holdFee;

    /**
     * realized profit and loss
     */
    private BigDecimal realised;
    /**
     * the value of ADL is 1-5. If it is empty, wait for the refresh
     */
    private Integer adlLevel;
    /**
     * 1 long ,2 short
     */
    private int positionType;
    /**
     * 1 isolated, 2 cross
     */
    private int openType;
    private Integer leverage;
    /**
     * auto add initial margin
     */
    private Boolean autoAddIm;
}
