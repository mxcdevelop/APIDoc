package com.mxc.example.open;


import com.mxc.contract.demo.response.Result;
import com.mxc.contract.demo.response.open.ContractFundingRateResp;

public class ContractFundingRateTest extends BasePublicApiTest {
    @Override
    public void test() {
        Result<ContractFundingRateResp> contractFundingRate = publicApi.getContractFundingRate(symbol);
        System.out.println(contractFundingRate.getData());
    }
}
