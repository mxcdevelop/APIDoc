package com.mxc.contract.demo.request.identified;

import com.alibaba.fastjson.annotation.JSONField;
import com.mxc.contract.demo.request.PageReqDTO;
import lombok.Data;

@Data
public class FundingRecordReq extends PageReqDTO {
    private String symbol;
    @JSONField(name = "position_id")
    private Integer positionId;
}
