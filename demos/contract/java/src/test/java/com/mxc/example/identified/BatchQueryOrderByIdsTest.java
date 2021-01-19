package com.mxc.example.identified;

import com.google.common.collect.Lists;

/**
 * 根据订单号批量查询订单
 */
public class BatchQueryOrderByIdsTest extends BasePrivateApiTest{
    @Override
    public void test() {
        System.out.println(privateApi.batchQueryByOrderIds(Lists.newArrayList(1L, 2L, 3L)).getData());
    }
}
