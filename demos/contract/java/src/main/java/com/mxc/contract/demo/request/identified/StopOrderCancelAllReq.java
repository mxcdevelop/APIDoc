package com.mxc.contract.demo.request.identified;

import lombok.Data;

@Data
public class StopOrderCancelAllReq {
    //仓位id,传入positionId，只取消对应仓位的委托单，不传则判断symbol
    private Long positionId;
    //合约名,传入symbol只取消该合约下的委托单，不传取消所有合约下的委托单
    private String symbol;
}
