package com.mxc.example.open;

import com.mxc.contract.demo.response.Result;
import com.mxc.contract.demo.response.open.ContractDealsResp;

import java.util.List;

public class ContractDealsTest extends BasePublicApiTest {
    @Override
    public void test() {
        Result<List<ContractDealsResp>> contractDeals = publicApi.getContractDeals(symbol, 5);
        System.out.println(contractDeals);
    }
}
