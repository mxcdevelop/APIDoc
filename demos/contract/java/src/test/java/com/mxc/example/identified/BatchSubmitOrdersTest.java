package com.mxc.example.identified;

import com.google.common.collect.Lists;
import com.mxc.contract.demo.request.identified.SubmitOrderReq;

import java.math.BigDecimal;

/**
 *批量下单
 */
public class BatchSubmitOrdersTest extends BasePrivateApiTest{
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
        System.out.println(privateApi.submitBatchOrder(Lists.newArrayList(submitOrderReq)));
    }
}
