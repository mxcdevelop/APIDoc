package com.mxc.example.identified;

import com.mxc.contract.demo.request.identified.HistoryOrdersReq;

/**
 * 获取用户所有历史订单
 */
public class HistoryOrdersTest extends BasePrivateApiTest{
    @Override
    public void test() {
        HistoryOrdersReq ordersReq = new HistoryOrdersReq();
        System.out.println(privateApi.getHistoryOrders(new HistoryOrdersReq()).getData());
        ordersReq.setSymbol(symbol);
        System.out.println(privateApi.getHistoryOrders(new HistoryOrdersReq()).getData());
    }
}
