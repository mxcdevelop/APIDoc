package com.mxc.contract.demo.request.identified;

import lombok.Data;

@Data
public class CancelOrderReqDTO {
    private String symbol;
    private String orderId;
}
