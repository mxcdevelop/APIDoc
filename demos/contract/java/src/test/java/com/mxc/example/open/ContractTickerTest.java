package com.mxc.example.open;

public class ContractTickerTest extends BasePublicApiTest {
    @Override
    public void test() {
        System.out.println(publicApi.getContractTicker(symbol).getData());
        System.out.println(publicApi.getContractTicker(null).getData());
    }
}
