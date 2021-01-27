package com.mxc.contract.demo.request.identified;

import com.alibaba.fastjson.annotation.JSONField;
import com.mxc.contract.demo.request.PageReqDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.annotation.Nullable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class OpenOrdersReq  {
    @Nullable
    private String symbol;

    @Min(1)
    @JSONField(name = "page_num")
    private int pageNum = 1;
    @Min(0)
    @Max(100)
    @JSONField(name = "page_size")
    private int pageSize = 20;
}
