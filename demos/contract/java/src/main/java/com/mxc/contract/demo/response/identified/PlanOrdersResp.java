package com.mxc.contract.demo.response.identified;

import com.alibaba.fastjson.annotation.JSONField;
import com.mxc.contract.demo.response.PageRespDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PlanOrdersResp extends PageRespDTO {

    private List<PlanOrderDTO> resultList;

    @NoArgsConstructor
    @Data
    public static class PlanOrderDTO {
        private String id;
        private String symbol;
        //订单方向, 1开多,3开空
        private Integer side;
        //触发价
        private BigDecimal triggerPrice;
        private BigDecimal price;
        private BigDecimal vol;
        //开仓类型,1:逐仓,2:全仓
        private Integer openType;
        //触发类型,1:大于等于，2:小于等于
        private Integer triggerType;
        //状态,1:未触发,2:已取消,3:已执行,4:已失效,5:执行失败
        private Integer state;
        //执行周期,单位:小时
        private Integer executeCycle;
        //触发价格类型,1:最新价，2:合理价，3:指数价
        private Integer trend;
        //订单类型,1:限价单,2:Post Only只做Maker,3:立即成交或立即取消,4:全部成交或者全部取消,5:市价单
        private Integer orderType;
        private String orderId;
        //执行失败时错误码，0：正常
        private Integer errorCode;
        private Long createTime;
        private Long updateTime;
    }
}
