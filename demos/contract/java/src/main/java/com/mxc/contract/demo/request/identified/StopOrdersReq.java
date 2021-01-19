package com.mxc.contract.demo.request.identified;

import com.alibaba.fastjson.annotation.JSONField;
import com.mxc.contract.demo.request.PageReqDTO;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class StopOrdersReq extends PageReqDTO {
    private String symbol;
    @JSONField(name = "is_finished")
    private Integer finished;
}
