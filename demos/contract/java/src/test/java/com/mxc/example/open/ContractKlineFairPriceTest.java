package com.mxc.example.open;

import com.mxc.contract.demo.request.open.ContractKlineFairPriceReq;
import com.mxc.contract.demo.response.Result;
import com.mxc.contract.demo.response.open.ContractKlineFairPriceResp;
import org.springframework.beans.BeanUtils;

public class ContractKlineFairPriceTest extends BasePublicApiTest {
    @Override
    public void test() {
        ContractKlineFairPriceReq req = new ContractKlineFairPriceReq();
        BeanUtils.copyProperties(getContractKlineDto(), req);
        Result<ContractKlineFairPriceResp> contractKlineFairPrice = publicApi.getContractKlineFairPrice(req);
        System.out.println(contractKlineFairPrice.getData());
    }
}
