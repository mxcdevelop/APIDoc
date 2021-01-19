package com.mxc.example.identified;

import com.google.common.collect.Lists;

/**
 * 取消订单
 */
public class BatchCancelOrdersTest extends BasePrivateApiTest {
    @Override
    public void test() {
        System.out.println(privateApi.cancelOrders(Lists.newArrayList(1L, 2L, 3L)).isSuccess());
    }
}
