package com.mxc.example.identified;

import com.mxc.contract.demo.request.identified.AccountTransferRecordReq;

/**
 * 获取用户资产划转记录
 */
public class AccountTransferRecordTest extends BasePrivateApiTest{
    @Override
    public void test() {
        AccountTransferRecordReq req = new AccountTransferRecordReq();
        req.setCurrency(currency);
        req.setState("WAIT");
        req.setType("IN");
        System.out.println(privateApi.getAccountTransferRecord(req));
    }
}
