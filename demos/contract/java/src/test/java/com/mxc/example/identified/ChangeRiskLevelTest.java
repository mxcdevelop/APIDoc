package com.mxc.example.identified;

import com.mxc.contract.demo.request.identified.ChangeRiskLevelReq;

/**
 * 修改风险等级
 */
public class ChangeRiskLevelTest extends BasePrivateApiTest {
    @Override
    public void test() {
        ChangeRiskLevelReq changeRiskLevelReq = new ChangeRiskLevelReq();
        changeRiskLevelReq.setSymbol(symbol);
        changeRiskLevelReq.setLevel(1);
        changeRiskLevelReq.setPositionType(1);
        System.out.println(privateApi.changeRiskLevel(changeRiskLevelReq));
    }
}
