package com.mxc.example.identified;

import com.mxc.contract.demo.request.identified.StopOrderCancelAllReq;

/**
 * 取消所有止盈止损委托单
 */
public class CancelAllStopOrderTest extends BasePrivateApiTest{
    @Override
    public void test() {
        System.out.println(privateApi.cancelAllStopOrders(new StopOrderCancelAllReq()).isSuccess());
    }
}
