package com.mxc.example.open;

import com.mxc.contract.demo.response.Result;
import com.mxc.contract.demo.response.open.ContractFairPriceResp;

public class ContractFairPriceTest extends BasePublicApiTest {
    @Override
    public void test() {
        Result<ContractFairPriceResp> contractFairPrice = publicApi.getContractFairPrice(symbol);
        System.out.println(contractFairPrice);

    }
}
