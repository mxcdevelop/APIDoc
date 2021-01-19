package com.mxc.example.open;

import com.mxc.contract.demo.response.Result;
import com.mxc.contract.demo.response.open.ContractDepthCommitsRespDTO;

import java.util.List;

public class ContractDepthCommitsTest extends BasePublicApiTest {
    @Override
    public void test() {
        Result<List<ContractDepthCommitsRespDTO>> contractDepthCommits = publicApi.getContractDepthCommits(symbol, 1);
        System.out.println(contractDepthCommits.getData());
    }
}
