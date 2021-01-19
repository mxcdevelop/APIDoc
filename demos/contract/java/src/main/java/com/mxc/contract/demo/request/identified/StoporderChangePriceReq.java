package com.mxc.contract.demo.request.identified;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StoporderChangePriceReq {
    //限价单订单id
    private Long orderId;
    //止损价，止盈价和止损价同时为空或者同时为0时，则表示取消订单的止盈止损
    private BigDecimal stopLossPrice;
    //止盈价，止盈价和止损价同时为空或者同时为0时，则表示取消订单的止盈止损
    private BigDecimal takeProfitPrice;
}
