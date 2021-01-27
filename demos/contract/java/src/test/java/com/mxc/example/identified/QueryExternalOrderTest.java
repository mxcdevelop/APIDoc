package com.mxc.example.identified;

/**
 * 根据外部号查询订单
 */
public class QueryExternalOrderTest extends BasePrivateApiTest{
    @Override
    public void test() {
        System.out.println(privateApi.getOderByExternalId(symbol, "123"));
    }
}
