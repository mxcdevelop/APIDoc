package com.mxc.example.identified;

import com.mxc.contract.demo.request.identified.PlanOrdersReq;

/**
 * 获取计划委托订单列表
 */
public class ListPlanOrdersTest extends BasePrivateApiTest{
    @Override
    public void test() {
        System.out.println(privateApi.getPlanOrders(new PlanOrdersReq()).getData());
    }
}
