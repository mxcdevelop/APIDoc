package com.mxc.contract.demo.response.identified;

import com.mxc.contract.demo.response.PageRespDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
@ToString(callSuper = true)
public class StopOrdersResp extends PageRespDTO {
    private List<StopOrderDTO> resultList;

    @NoArgsConstructor
    @Data
    public static class StopOrderDTO {
        private Integer id;
        private String orderId;
        private String symbol;
        private String positionId;
        private BigDecimal stopLossPrice;
        private BigDecimal takeProfitPrice;
        private Integer state;
        private Integer triggerSide;
        private Integer positionType;
        private BigDecimal vol;
        private BigDecimal realityVol;
        private Integer version;
        private Integer isFinished;
        private Long createTime;
        private Long updateTime;
    }
}
