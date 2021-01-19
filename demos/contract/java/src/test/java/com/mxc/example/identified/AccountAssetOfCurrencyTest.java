package com.mxc.example.identified;

/**
 * 获取用户单个币种资产信息
 */
public class AccountAssetOfCurrencyTest extends BasePrivateApiTest{
    @Override
    public void test() {
        System.out.println(privateApi.getAccountAsset(currency).getData());
    }
}
