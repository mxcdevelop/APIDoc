package com.mxc.example.identified;

import com.mxc.contract.demo.request.identified.SubmitOrderReq;

import java.math.BigDecimal;

/**
 * 下单
 */
public class SubmitOrderTest extends BasePrivateApiTest {
    @Override
    public void test() {
        SubmitOrderReq submitOrderReq = new SubmitOrderReq();
        submitOrderReq.setSymbol(symbol);
        submitOrderReq.setPrice(new BigDecimal("100"));
        submitOrderReq.setVol(new BigDecimal("10"));
        submitOrderReq.setLeverage(10);
        submitOrderReq.setSide(1);
        submitOrderReq.setType(1);
        submitOrderReq.setOpenType(1);
        System.out.println(privateApi.submitOrder(new SubmitOrderReq()).getData());
    }
}
