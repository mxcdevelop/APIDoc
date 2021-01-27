package com.mxc.contract.demo.response.open;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
public class ContractFairPriceResp {
    //交易对
    private String symbol;
    //指数价格
    private BigDecimal indexPrice;
    //系统时间戳
    private Long timestamp;
}
