package com.mxc.contract.demo.request.identified;

import com.mxc.contract.demo.request.PageReqDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PlanOrdersReq extends PageReqDTO {
    private String symbol;
    //状态,1:未触发,2:已取消,3:已执行,4:已失效,5:执行失败;多个用 ',' 隔开
    private String states;
}
