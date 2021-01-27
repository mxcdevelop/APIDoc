package com.mxc.contract.demo.response.identified;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class SubmitBatchRespDTO {
    private String externalOid;
    private Long orderId;
    private String errorMsg;
    private Integer errorCode;
}
