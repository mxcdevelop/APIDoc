package com.mxc.example.open;

import com.mxc.contract.demo.response.Result;

import java.util.List;

public class ContractSupportCurrenciesTest extends BasePublicApiTest {
    @Override
    public void test() {
        Result<List<String>> supportCurrencies = publicApi.getSupportCurrencies();
        System.out.println(supportCurrencies);
    }
}
