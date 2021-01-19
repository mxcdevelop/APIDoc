package com.mxc.example.identified;

import com.mxc.contract.demo.request.identified.ChangeLeverageReq;

/**
 * 修改杠杆倍数
 */
public class PositionChangeLeverageTest extends BasePrivateApiTest {
    @Override
    public void test() {
        ChangeLeverageReq req = new ChangeLeverageReq();
        req.setPositionId(1L);
        req.setLeverage(100);
        System.out.println(privateApi.changeLeverage(req).isSuccess());
    }
}
