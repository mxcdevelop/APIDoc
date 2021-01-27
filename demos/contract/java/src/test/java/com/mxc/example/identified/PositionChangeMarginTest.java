package com.mxc.example.identified;

import com.mxc.contract.demo.request.identified.ChangeMarginReq;

import java.math.BigDecimal;

/**
 * 增加或减少仓位保证金
 */
public class PositionChangeMarginTest extends BasePrivateApiTest {
    @Override
    public void test() {
        ChangeMarginReq changeMarginReq = new ChangeMarginReq();
        changeMarginReq.setPositionId(1L);
        changeMarginReq.setType("ADD");
        changeMarginReq.setAmount(new BigDecimal("1000"));
        System.out.println(privateApi.changeMargin(changeMarginReq));
    }
}
