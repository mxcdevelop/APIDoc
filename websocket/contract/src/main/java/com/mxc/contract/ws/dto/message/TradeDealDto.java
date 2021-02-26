package com.mxc.contract.ws.dto.message;

import com.alibaba.fastjson.annotation.JSONField;
import com.mxc.contract.ws.common.Channel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
@MessageEventDto(channel = Channel.PUSH_DEAL, isList = true)
public class TradeDealDto implements Dto {
    @JSONField(name = "data")
    private List<Deal> deals;

    @Data
    public static class Deal {
        /**
         * transaction price
         */
        @JSONField(name = "p")
        private BigDecimal price;

        /**
         * volume
         */
        @JSONField(name = "v")
        private BigDecimal vol;

        /**
         * transaction direction,1:purchase,2:sell
         */
        @JSONField(name = "T")
        private Integer isBid;

        /**
         * open position?, 1: Yes,2: No, vol is the additional position when O is 1
         */
        @JSONField(name = "O")
        private Integer open;

        /**
         * Is it auto-transact ? 1: Yes,2: No
         */
        @JSONField(name = "M")
        private Integer isSelfDeal;

        /**
         * transaction time
         */
        @JSONField(name = "t")
        private Date timestamp;
    }
}
