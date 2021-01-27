package com.mxc.example.identified;

import com.google.common.collect.Lists;
import com.mxc.contract.demo.request.identified.CancelOrderReqDTO;

/**
 * 取消计划委托订单
 */
public class CancelPlanOderTest extends BasePrivateApiTest{
    @Override
    public void test() {
        CancelOrderReqDTO dto = new CancelOrderReqDTO();
        dto.setSymbol(symbol);
        dto.setOrderId("123");
        System.out.println(privateApi.cancelPlanOrder(Lists.newArrayList(dto)));
    }
}
