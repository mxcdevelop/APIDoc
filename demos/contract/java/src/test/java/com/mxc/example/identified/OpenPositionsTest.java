package com.mxc.example.identified;

/**
 * 获取用户当前持仓
 */
public class OpenPositionsTest extends BasePrivateApiTest{
    @Override
    public void test() {
        System.out.println(privateApi.getOpenPositions(null));
        System.out.println(privateApi.getOpenPositions(symbol));
    }
}
