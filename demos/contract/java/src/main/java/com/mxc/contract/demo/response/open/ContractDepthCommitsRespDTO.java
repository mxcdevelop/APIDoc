package com.mxc.contract.demo.response.open;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ContractDepthCommitsRespDTO {
    //卖方深度
    private List<List<BigDecimal>> asks;
    //买方深度
    private List<List<BigDecimal>> bids;
    //版本号
    private Long version;
}
