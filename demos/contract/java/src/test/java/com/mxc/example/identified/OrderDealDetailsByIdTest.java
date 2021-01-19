package com.mxc.example.identified;

/**
 * 根据订单号获取订单成交明细
 */
public class OrderDealDetailsByIdTest extends BasePrivateApiTest{
    @Override
    public void test() {
        System.out.println(privateApi.getDealDetailById(1L).getData());
    }
}
