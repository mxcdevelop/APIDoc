package com.mxc.contract.demo.request.identified;

import com.alibaba.fastjson.annotation.JSONField;
import com.mxc.contract.demo.request.PageReqDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderDealsReq extends PageReqDTO {
    @NotNull
    private String symbol;
    //开始时间，不传默认为向前推7天的时间，传了时间，最大跨度为90天
    @JSONField(name = "start_time")
    private Long startTime;
    //结束时间，开始和结束时间的跨度为90天
    @JSONField(name = "end_time")
    private Long endTime;
}
