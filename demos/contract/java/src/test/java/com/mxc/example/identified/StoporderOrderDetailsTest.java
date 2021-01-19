package com.mxc.example.identified;

import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;

/**
 *获取止盈止损订单执行明细
 */
public class StoporderOrderDetailsTest extends BasePrivateApiTest{
    @Override
    @Test(expected = HttpClientErrorException.class)
    public void test() {
        System.out.println(privateApi.getStopOrderDetails(1L).getData());
    }
}
