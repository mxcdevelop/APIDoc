package com.mxc.contract.demo.response.open;

import com.alibaba.fastjson.annotation.JSONField;
import com.mxc.contract.demo.response.PageRespDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
public class ContractRiskReserveHistoryResp extends PageRespDTO {
    @JSONField(name = "resultList")
    private List<RiskReserveDTO> resultList;

    @NoArgsConstructor
    @Data
    public static class RiskReserveDTO {
        private String symbol;
        private String currency;
        private BigDecimal available;
        private Long snapshotTime;
    }
}
