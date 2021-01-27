package com.mxc.example.identified;


/**
 * 取消某合约下所有订单
 */
public class CancelAllOrderTest extends BasePrivateApiTest {
    @Override
    public void test() {
        System.out.println(privateApi.cancelAllOrders(symbol));
    }
}
