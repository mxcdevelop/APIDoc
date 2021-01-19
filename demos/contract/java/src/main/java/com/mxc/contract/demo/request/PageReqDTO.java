package com.mxc.contract.demo.request;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class PageReqDTO {
    @Min(1)
    @JSONField(name = "page_num")
    private int pageNum = 1;
    @Min(0)
    @Max(100)
    @JSONField(name = "page_size")
    private int pageSize = 20;
}
