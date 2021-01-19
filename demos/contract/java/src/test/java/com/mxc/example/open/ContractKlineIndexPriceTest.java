package com.mxc.example.open;

import com.mxc.contract.demo.request.open.ContractKlineIndexPriceReq;
import com.mxc.contract.demo.response.Result;
import com.mxc.contract.demo.response.open.ContractKlineIndexPriceResp;
import org.springframework.beans.BeanUtils;

public class ContractKlineIndexPriceTest extends BasePublicApiTest {
    @Override
    public void test() {
        ContractKlineIndexPriceReq req = new ContractKlineIndexPriceReq();
        BeanUtils.copyProperties(getContractKlineDto(), req);
        Result<ContractKlineIndexPriceResp> contractKlineIndexPrice = publicApi.getContractKlineIndexPrice(req);
        System.out.println(contractKlineIndexPrice.getData());
    }
}
