package com.mxc.example.identified;

import com.mxc.contract.demo.request.identified.FundingRecordReq;

/**
 * 获取用户资金费用明细
 */
public class FundingRecordsTest extends BasePrivateApiTest {
    @Override
    public void test() {
        System.out.println(privateApi.getFundingRecords(new FundingRecordReq()).getData());
    }
}
