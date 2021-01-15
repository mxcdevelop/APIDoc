package com.mxc.api.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OpenOrderResp {

    private String id;

    private String symbol;

    private String price;

    private String quantity;

    @JsonProperty("remain_quantity")
    private String remainQuantity;

    @JsonProperty("remain_amount")
    private String remainAmount;

    @JsonProperty("create_time")
    private Long createTime;

    private String state;

    private String type;
    @JsonIgnore
    private String memberId;
}
