package com.mxc.contract.demo.request.open;

import lombok.Data;

@Data
public class ContractKlineDTO {
    //合约名
    private String symbol;
    //间隔: Min1、Min5、Min15、Min30、Min60、Hour4、Hour8、Day1、Week1、Month1
    private String interval;
    //开始时间戳
    private Long start;
    //结束时间戳
    private Long end;
}
