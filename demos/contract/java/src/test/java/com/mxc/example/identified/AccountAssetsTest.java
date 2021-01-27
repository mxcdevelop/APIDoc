package com.mxc.example.identified;

/**
 * 获取用户所有资产信息
 */
public class AccountAssetsTest extends BasePrivateApiTest {
    @Override
    public void test() {
        System.out.println(privateApi.getAccountAssets());
    }
}
