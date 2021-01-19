package com.mxc.example.identified;

import com.mxc.contract.demo.request.identified.HistoryPositionsReq;

/**
 * 获取用户历史持仓信息
 */
public class HistoryPositionsTest extends BasePrivateApiTest {
    @Override
    public void test() {
        System.out.println(privateApi.getHistoryPositions(new HistoryPositionsReq()).getData());
    }
}
