package com.mxc.example.identified;

import com.google.common.collect.Lists;
import com.mxc.contract.demo.request.identified.StopOrderCancelDTO;

/**
 * 取消止盈止损委托单
 */
public class CancelStopOrderTest extends BasePrivateApiTest{
    @Override
    public void test() {
        StopOrderCancelDTO stopOrderCancelDTO = new StopOrderCancelDTO();
        stopOrderCancelDTO.setStopPlanOrderId(123L);
        System.out.println(privateApi.cancelStopOrders(Lists.newArrayList(stopOrderCancelDTO)).isSuccess());
    }
}
