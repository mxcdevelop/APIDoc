package com.mxc.example.open;

public class ContractFundingRateHistoryTest extends BasePublicApiTest {
    @Override
    public void test() {
        System.out.println(publicApi.getContractFundingRateHistory(getContractHistoryReq()));
    }
}
