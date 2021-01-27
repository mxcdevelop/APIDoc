package com.mxc.contract.demo.request.identified;

import com.alibaba.fastjson.annotation.JSONField;
import com.mxc.contract.demo.request.PageReqDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class PlanOrdersReq {
    private String symbol;
    //状态,1:未触发,2:已取消,3:已执行,4:已失效,5:执行失败;多个用 ',' 隔开
    private String states;
    @Min(1)
    @JSONField(name = "page_num")
    private int pageNum = 1;
    @Min(0)
    @Max(100)
    @JSONField(name = "page_size")
    private int pageSize = 20;
}
