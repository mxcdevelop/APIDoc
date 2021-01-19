package com.mxc.example.identified;

import com.mxc.contract.demo.request.identified.PlanOrderReq;

import java.math.BigDecimal;

/**
 * 计划委托下单
 */
public class SubmitPlanOrderTest extends BasePrivateApiTest {
    @Override
    public void test() {
        PlanOrderReq planOrderReq = new PlanOrderReq();
        planOrderReq.setSymbol(symbol);
        planOrderReq.setVol(new BigDecimal("1"));
        planOrderReq.setLeverage(10);
        planOrderReq.setSide(1);
        planOrderReq.setOpenType(1);
        planOrderReq.setTriggerPrice(new BigDecimal("100"));
        planOrderReq.setTriggerType(1);
        planOrderReq.setExecuteCycle(1);
        planOrderReq.setOrderType(1);
        planOrderReq.setTrend(1);

        System.out.println(privateApi.submitPlanOrder(planOrderReq).isSuccess());
    }
}
