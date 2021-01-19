package com.mxc.contract.demo.response.identified;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DealDetailDTO {
    private String id;
    private String symbol;
    //订单方向 1开多,2平空,3开空,4平多
    private Integer side;
    private Integer vol;
    private Double price;
    private String feeCurrency;
    private Double fee;
    private Long timestamp;
    private Integer profit;
    //订单类别:1限价委托,2强平代管委托,4ADL减仓
    private Integer category;
    private String orderId;
    private Boolean taker;
}
