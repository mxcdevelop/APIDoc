package com.mxc.example.identified;

import com.mxc.contract.demo.request.identified.CancelOrderWithExternalReq;

/**
 * 根据外部订单号取消订单
 */
public class Cancel_OrderWithExternalTest extends BasePrivateApiTest{
    @Override
    public void test() {
        CancelOrderWithExternalReq req = new CancelOrderWithExternalReq();
        req.setSymbol(symbol);
        req.setExternalOid("123");
        System.out.println(privateApi.cancelWithExternal(new CancelOrderWithExternalReq()).isSuccess());
    }
}
