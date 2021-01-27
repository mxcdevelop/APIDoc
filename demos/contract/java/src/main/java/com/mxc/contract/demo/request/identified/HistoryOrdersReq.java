package com.mxc.contract.demo.request.identified;

import com.alibaba.fastjson.annotation.JSONField;
import com.mxc.contract.demo.request.PageReqDTO;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class HistoryOrdersReq {
    private String symbol;
    //订单状态,1:待报,2未完成,3已完成,4已撤销,5无效;多个用 ',' 隔开
    private String states;
    private Integer category;
    @JSONField(name = "start_time")
    private Long startTime;
    @JSONField(name = "end_time")
    private Long endTime;
    //订单方向 1开多,2平空,3开空,4平多
    private Integer side;

    @Min(1)
    @JSONField(name = "page_num")
    private int pageNum = 1;
    @Min(0)
    @Max(100)
    @JSONField(name = "page_size")
    private int pageSize = 20;
}
