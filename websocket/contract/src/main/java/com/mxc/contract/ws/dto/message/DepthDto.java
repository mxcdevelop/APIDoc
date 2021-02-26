package com.mxc.contract.ws.dto.message;

import com.alibaba.fastjson.annotation.JSONField;
import com.mxc.contract.ws.common.Channel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
@MessageEventDto(channel = Channel.PUSH_DEPTH)
public class DepthDto implements Dto {
    /**
     * the version number
     */
    @JSONField(name = "version")
    private Integer version;
    /**
     * seller depth
     */
    @JSONField(name = "asks")
    private List<List<BigDecimal>> asks;
    /**
     * buyer depth
     */
    @JSONField(name = "bids")
    private List<List<BigDecimal>> bids;
}
