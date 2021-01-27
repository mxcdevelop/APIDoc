package com.mxc.example.open;

import com.mxc.contract.demo.request.open.ContractKlineReq;
import com.mxc.contract.demo.response.Result;
import com.mxc.contract.demo.response.open.ContractKlineDTO;
import org.springframework.beans.BeanUtils;

public class ContractKlineTest extends BasePublicApiTest {
    @Override
    public void test() {
        ContractKlineReq req = new ContractKlineReq();
        BeanUtils.copyProperties(getContractKlineDto(), req);
        Result<ContractKlineDTO> contractKline = publicApi.getContractKline(req);
        System.out.println(contractKline);
    }
}
