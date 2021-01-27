package com.mxc.example.identified;

import com.mxc.contract.demo.request.identified.FundingRecordReq;

/**
 * 获取用户资金费用明细
 */
public class FundingRecordsTest extends BasePrivateApiTest {
    @Override
    public void test() {
        FundingRecordReq fundingRecordReq = new FundingRecordReq();
        fundingRecordReq.setSymbol(symbol);
        fundingRecordReq.setPositionId(1);
        System.out.println(privateApi.getFundingRecords(fundingRecordReq));
    }
}
