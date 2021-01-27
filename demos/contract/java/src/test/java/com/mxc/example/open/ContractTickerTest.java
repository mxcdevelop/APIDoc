package com.mxc.example.open;

public class ContractTickerTest extends BasePublicApiTest {
    @Override
    public void test() {
        System.out.println(publicApi.getContractTicker(symbol));
        System.out.println(publicApi.getContractTicker(null));
    }
}
