package com.mxc.contract.demo.response.identified;


import com.alibaba.fastjson.annotation.JSONField;
import com.mxc.contract.demo.response.PageRespDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
public class FundingRecordsResp extends PageRespDTO {

    private List<FundingRecordDTO> resultList;

    @NoArgsConstructor
    @Data
    public static class FundingRecordDTO {
        private Integer id;
        private String symbol;
        //1:多仓,2:空仓
        private Integer positionType;
        //仓位价值
        private BigDecimal positionValue;
        //费用
        private BigDecimal funding;
        //资金费率
        private BigDecimal rate;
        private Long settleTime;
    }
}
