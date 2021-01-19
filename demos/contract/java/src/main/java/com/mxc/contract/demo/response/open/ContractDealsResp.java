package com.mxc.contract.demo.response.open;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ContractDealsResp {
    //成交价
    @JSONField(name = "p")
    private BigDecimal price;

    //数量
    @JSONField(name = "v")
    private BigDecimal vol;

    //成交方向,1:买,2:卖
    @JSONField(name = "T")
    private Integer type;

    //是否是开仓，1:是,2:否,当O为1的时候, vol是新增的持仓量
    @JSONField(name = "O")
    private Integer open;

    //是否为自成交,1:是,2:否
    @JSONField(name = "M")
    private Integer mate;

    @JSONField(name = "t")
    //成交时间
    private Long timestamp;
}
