package com.mxc.contract.demo.request.identified;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StopOrderChangePlanPriceReq {
    private Long stopPlanOrderId;
    //止损价,和止盈价至少有一个不为空，且必须大于0
    private BigDecimal stopLossPrice;
    //止盈价,和止损价至少有一个不为空，且必须大于0
    private BigDecimal takeProfitPrice;
}
