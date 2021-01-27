package com.mxc.example.identified;

import com.mxc.contract.demo.request.identified.StopOrdersReq;

/**
 * 获取止盈止损订单列表
 */
public class ListStopOrdersTest extends BasePrivateApiTest{
    @Override
    public void test() {
        StopOrdersReq stopOrdersReq = new StopOrdersReq();
        stopOrdersReq.setSymbol(symbol);
        stopOrdersReq.setFinished(1);
        System.out.println(privateApi.getStopOrders(stopOrdersReq));
    }
}
