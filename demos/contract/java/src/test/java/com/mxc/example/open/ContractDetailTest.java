package com.mxc.example.open;

import com.mxc.contract.demo.response.Result;
import com.mxc.contract.demo.response.open.ContractResp;

import java.util.List;

public class ContractDetailTest extends BasePublicApiTest {
    @Override
    public void test() {
        Result<List<ContractResp>> contractDetail = publicApi.getContractDetail(symbol);
        System.out.println(contractDetail);
    }
}
