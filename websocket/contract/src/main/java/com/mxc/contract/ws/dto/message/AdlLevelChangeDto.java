package com.mxc.contract.ws.dto.message;

import com.mxc.contract.ws.common.Channel;
import lombok.Data;

@Data
@MessageEventDto(channel = Channel.PUSH_PERSONAL_ADL_LEVEL)
public class AdlLevelChangeDto implements Dto {
    private Long positionId;
    /**
     * the current adl level ：1-5
     */
    private Integer adlLevel;
}
