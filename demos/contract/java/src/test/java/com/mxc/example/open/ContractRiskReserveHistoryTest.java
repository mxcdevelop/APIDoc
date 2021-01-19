package com.mxc.example.open;

public class ContractRiskReserveHistoryTest extends BasePublicApiTest {
    @Override
    public void test() {
        System.out.println(publicApi.getContractRiskReserveHistory(getContractHistoryReq()).getData());
    }
}
