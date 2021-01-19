package com.mxc.example.identified;

import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;

/**
 * 根据外部号查询订单
 */
public class QueryExternalOrderTest extends BasePrivateApiTest{
    @Override
    @Test(expected = HttpClientErrorException.class)
    public void test() {
        System.out.println(privateApi.getOderByExternalId(symbol, "123").getData());
    }
}
