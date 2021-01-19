package com.mxc.contract.demo.request.identified;

import lombok.Data;

@Data
public class ChangeRiskLevelReq {
    private String symbol;
    private Integer level;
    //1:多仓，2:空仓
    private Integer positionType;
}
