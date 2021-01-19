package com.mxc.example.identified;

/**
 * 根据订单号查询订单
 */
public class QueryOrderByIdTest extends BasePrivateApiTest {
    @Override
    public void test() {
        System.out.println(privateApi.getOderByOrderId(System.currentTimeMillis()).getData());
    }
}
