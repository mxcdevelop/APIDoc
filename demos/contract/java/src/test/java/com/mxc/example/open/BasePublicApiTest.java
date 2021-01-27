package com.mxc.example.open;

import com.mxc.contract.demo.api.PublicApi;
import com.mxc.contract.demo.request.open.ContractHistoryReq;
import com.mxc.contract.demo.request.open.ContractKlineDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Calendar;
import java.util.Date;

@RunWith(JUnit4.class)
public abstract class BasePublicApiTest {
    PublicApi publicApi = new PublicApi("https://contract.mxc.ai");

    public String symbol = "BTC_USDT";

    public ContractKlineDTO getContractKlineDto() {
        ContractKlineDTO req = new ContractKlineDTO();
        req.setSymbol(symbol);
        req.setInterval("Min15");
        Long end = System.currentTimeMillis() / 1000;
        req.setStart(end - 24 * 60 * 60);
        req.setEnd(end);
        return req;
    }

    public ContractHistoryReq getContractHistoryReq() {
        ContractHistoryReq req = new ContractHistoryReq();
        req.setSymbol(symbol);
        return req;
    }

    @Test
    public abstract void test();
}
