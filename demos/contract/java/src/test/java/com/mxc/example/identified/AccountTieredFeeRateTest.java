package com.mxc.example.identified;

/**
 * 获取用户当前手续费率
 */
public class AccountTieredFeeRateTest extends BasePrivateApiTest {
    @Override
    public void test() {
        System.out.println(privateApi.getTieredFeeRate(symbol));
    }
}
