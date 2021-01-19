package com.mxc.contract.demo.request.identified;

import lombok.Data;

@Data
public class ChangeLeverageReq {
    private Long positionId;
    private Integer leverage;
}
