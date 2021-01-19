package com.mxc.contract.demo.response.open;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ContractKlineDTO {
    //开盘价
    private List<BigDecimal> open;
    //收盘价
    private List<BigDecimal> close;
    //最高价
    private List<BigDecimal> high;
    //最低价
    private List<BigDecimal> low;
    //成交量
    private List<BigDecimal> vol;

    private List<BigDecimal> amount;
    //时间窗口
    private List<Long> time;
}
