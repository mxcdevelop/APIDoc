package com.mxc.contract.demo.response.open;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class ContractTickerResp {

    //合约名
    private String symbol;

    //最新价
    private BigDecimal lastPrice;

    //买一价
    private BigDecimal bid1;

    //卖一价
    private BigDecimal ask1;

    //24小时成交量，按张数统计
    private BigDecimal volume24;

    //24小时成交额
    private BigDecimal amount24;

    //总持仓量
    private BigDecimal holdVol;

    //24小时最低价
    private BigDecimal lower24Price;

    //24小时内最高价
    private BigDecimal high24Price;

    //涨跌幅
    private BigDecimal riseFallRate;

    //涨跌额
    private BigDecimal riseFallValue;

    //指数价格
    private BigDecimal indexPrice;

    //合理价
    private BigDecimal fairPrice;

    //资金费率
    private BigDecimal fundingRate;

    private BigDecimal maxBidPrice;

    private BigDecimal minAskPrice;
    //成交时间
    private Long timestamp;
}
