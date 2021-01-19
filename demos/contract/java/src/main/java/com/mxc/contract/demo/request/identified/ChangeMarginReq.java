package com.mxc.contract.demo.request.identified;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChangeMarginReq {
    private Long positionId;
    private BigDecimal amount;
    //类型,ADD:增加,SUB:减少
    private String type;
}
