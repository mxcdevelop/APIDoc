package com.mxc.example.open;

import com.mxc.contract.demo.api.PublicApi;
import com.mxc.contract.demo.request.open.ContractHistoryReq;
import com.mxc.contract.demo.request.open.ContractKlineDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Calendar;

@RunWith(JUnit4.class)
public abstract class BasePublicApiTest {
    PublicApi publicApi = new PublicApi("https://contract.mxc.com");

    public String symbol = "BTC_USDT";

    public ContractKlineDTO getContractKlineDto() {
        ContractKlineDTO req = new ContractKlineDTO();
        req.setSymbol(symbol);
        req.setInterval("Min5");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        req.setStart(calendar.getTimeInMillis());
        req.setEnd(System.currentTimeMillis());
        return req;
    }

    public ContractHistoryReq getContractHistoryReq(){
        ContractHistoryReq req = new ContractHistoryReq();
        req.setSymbol(symbol);
        return req;
    }
    @Test
    public abstract void test();
}
