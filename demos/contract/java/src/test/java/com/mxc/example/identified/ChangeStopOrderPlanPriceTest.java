package com.mxc.example.identified;

import com.mxc.contract.demo.request.identified.StopOrderChangePlanPriceReq;

import java.math.BigDecimal;

/**
 * 修改止盈止损委托单止盈止损价格
 */
public class ChangeStopOrderPlanPriceTest extends BasePrivateApiTest {
    @Override
    public void test() {
        StopOrderChangePlanPriceReq planPriceReq = new StopOrderChangePlanPriceReq();
        planPriceReq.setStopPlanOrderId(123L);
        planPriceReq.setTakeProfitPrice(new BigDecimal("100"));
        planPriceReq.setStopLossPrice(new BigDecimal("80"));
        System.out.println(privateApi.stopOrderChangePlanPriceReq(planPriceReq).isSuccess());
    }
}
