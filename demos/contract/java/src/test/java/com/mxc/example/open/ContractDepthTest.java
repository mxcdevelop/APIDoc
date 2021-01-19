package com.mxc.example.open;

import com.mxc.contract.demo.response.Result;
import com.mxc.contract.demo.response.open.ContractDepthResp;

public class ContractDepthTest extends BasePublicApiTest {
    @Override
    public void test() {
        Result<ContractDepthResp> contractDepth = publicApi.getContractDepth(symbol, 2);
        System.out.println(contractDepth.getData());
    }
}
