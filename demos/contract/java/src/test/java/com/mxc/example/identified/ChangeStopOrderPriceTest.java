package com.mxc.example.identified;

import com.mxc.contract.demo.request.identified.StoporderChangePriceReq;

import java.math.BigDecimal;

/**
 * 修改限价单止盈止损价格
 */
public class ChangeStopOrderPriceTest extends BasePrivateApiTest{
    @Override
    public void test() {
        StoporderChangePriceReq changePriceReq = new StoporderChangePriceReq();
        changePriceReq.setOrderId(123L);
        changePriceReq.setStopLossPrice(new BigDecimal("100"));
        changePriceReq.setStopLossPrice(new BigDecimal("80"));
        System.out.println(privateApi.stopOrderChangePrice(changePriceReq));
    }
}
