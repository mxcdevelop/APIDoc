package com.mxc.example.identified;

import com.mxc.contract.demo.request.identified.OpenOrdersReq;

/**
 * 获取用户当前未结束订单
 */
public class OpenOrdersUnfinishedTest extends BasePrivateApiTest{
    @Override
    public void test() {
        OpenOrdersReq openOrdersReq = new OpenOrdersReq();
        openOrdersReq.setSymbol(symbol);
        System.out.println(privateApi.getOpenOrders(new OpenOrdersReq()).getData());
    }
}
