package com.mxc.contract.demo.response.identified;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class SubmitBatchRespDTO {
    private String symbol;
    private BigDecimal price;
    private BigDecimal vol;
    private Integer leverage;
    private Integer side;
    private Integer type;
    private Integer openType;
    private String externalOid;
}
