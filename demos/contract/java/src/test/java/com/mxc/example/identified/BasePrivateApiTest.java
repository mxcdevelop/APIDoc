package com.mxc.example.identified;

import com.mxc.contract.demo.api.PrivateApi;
import com.mxc.contract.demo.api.PublicApi;
import com.mxc.contract.demo.request.open.ContractHistoryReq;
import com.mxc.contract.demo.request.open.ContractKlineDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Calendar;

@RunWith(JUnit4.class)
public abstract class BasePrivateApiTest {
    //FIXME replace with your keys
    PrivateApi privateApi = new PrivateApi("https://contract.mxc.com", "mxcU1TzSmRDW1o5AsE", "428813b3c76e49c68b2e7379f25c439d");

    public String symbol = "BTC_USDT";
    public String currency = "USDT";

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

    public ContractHistoryReq getContractHistoryReq() {
        ContractHistoryReq req = new ContractHistoryReq();
        req.setSymbol(symbol);
        return req;
    }

    @Test
    public abstract void test();
}
