package com.mxc.contract.demo.response.open;

import com.alibaba.fastjson.annotation.JSONField;
import com.mxc.contract.demo.response.PageRespDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
public class ContractFundingRateHistoryResp extends PageRespDTO {
    private List<FundingRateDTO> resultList;

    @NoArgsConstructor
    @Data
    public static class FundingRateDTO {
        private String symbol;
        private BigDecimal fundingRate;
        private Long settleTime;
    }
}
