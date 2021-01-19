package com.mxc.example.identified;

/**
 * 获取风险限额
 */
public class AccountRiskLimitTest extends BasePrivateApiTest {
    @Override
    public void test() {
        System.out.println(privateApi.getAccountRiskLimit(symbol).getData());
    }
}
