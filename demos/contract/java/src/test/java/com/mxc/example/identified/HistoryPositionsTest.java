package com.mxc.example.identified;

import com.mxc.contract.demo.request.identified.HistoryPositionsReq;

/**
 * 获取用户历史持仓信息
 */
public class HistoryPositionsTest extends BasePrivateApiTest {
    @Override
    public void test() {
        HistoryPositionsReq historyPositionsReq = new HistoryPositionsReq();
        historyPositionsReq.setSymbol(symbol);
        historyPositionsReq.setType(1);
        System.out.println(privateApi.getHistoryPositions(historyPositionsReq));
    }
}
