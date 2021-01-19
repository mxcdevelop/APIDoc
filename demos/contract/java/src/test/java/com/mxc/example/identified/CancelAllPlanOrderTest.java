package com.mxc.example.identified;

/**
 * 取消所有计划委托订单
 */
public class CancelAllPlanOrderTest extends BasePrivateApiTest{
    @Override
    public void test() {
        System.out.println(privateApi.cancelAllPlanOrder(null).isSuccess());
        System.out.println(privateApi.cancelAllPlanOrder(symbol).isSuccess());
    }
}
