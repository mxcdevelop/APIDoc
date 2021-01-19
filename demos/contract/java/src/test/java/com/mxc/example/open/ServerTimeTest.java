package com.mxc.example.open;


import com.mxc.contract.demo.response.Result;

public class ServerTimeTest extends BasePublicApiTest {

    @Override
    public void test() {
        Result<Long> systemTime = publicApi.getSystemTime();
        System.out.println(systemTime.getData());
    }
}
