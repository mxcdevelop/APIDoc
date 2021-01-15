package com.mxc.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PlaceOrderReq {
    private String symbol;
    private String price;
    private String quantity;
    @JsonProperty("trade_type")
    private String tradeType;
    @JsonProperty("order_type")
    private String orderType;
    @JsonProperty("client_order_id")
    private String clientOrderId;
}
