package com.mxc.example.open;

import com.mxc.contract.demo.response.Result;
import com.mxc.contract.demo.response.open.ContractIndexPriceResp;

public class ContractIndexPriceTest extends BasePublicApiTest {
    @Override
    public void test() {
        Result<ContractIndexPriceResp> contractIndexPrice = publicApi.getContractIndexPrice(symbol);
        System.out.println(contractIndexPrice.getData());
    }
}
