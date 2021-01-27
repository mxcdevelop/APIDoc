package com.mxc.example.open;

import com.mxc.contract.demo.request.open.ContractKlineIndexPriceReq;
import com.mxc.contract.demo.response.Result;
import com.mxc.contract.demo.response.open.ContractKlineDTO;
import org.springframework.beans.BeanUtils;

public class ContractKlineIndexPriceTest extends BasePublicApiTest {
    @Override
    public void test() {
        ContractKlineIndexPriceReq req = new ContractKlineIndexPriceReq();
        BeanUtils.copyProperties(getContractKlineDto(), req);
        Result<ContractKlineDTO> contractKlineIndexPrice = publicApi.getContractKlineIndexPrice(req);
        System.out.println(contractKlineIndexPrice);
    }
}
