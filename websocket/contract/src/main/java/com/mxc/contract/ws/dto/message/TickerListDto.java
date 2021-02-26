package com.mxc.contract.ws.dto.message;

import com.alibaba.fastjson.annotation.JSONField;
import com.mxc.contract.ws.common.Channel;
import lombok.Data;

import java.util.List;


@Data
@MessageEventDto(channel = Channel.PUSH_TICKERS, isList = true)
public class TickerListDto implements Dto {
    @JSONField(name = "data")
    private List<TickerDto> tickerList;
}
