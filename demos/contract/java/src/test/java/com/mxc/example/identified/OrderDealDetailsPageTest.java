package com.mxc.example.identified;

import com.mxc.contract.demo.request.identified.OrderDealsReq;

/**
 * 获取用户所有订单成交明细
 */
public class OrderDealDetailsPageTest extends BasePrivateApiTest{
    @Override
    public void test() {
        OrderDealsReq orderDealsReq = new OrderDealsReq();
        orderDealsReq.setSymbol(symbol);
        System.out.println(privateApi.getOrderDeals(new OrderDealsReq()).getData());
    }
}
