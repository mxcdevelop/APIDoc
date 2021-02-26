package com.mxc.contract.ws.common;

import org.apache.commons.lang3.StringUtils;

/**
 * the enum for filter channel
 */
public enum FilterEventEnum {
    FILTER_ORDER("order"),
    FILTER_ORDER_DEAL("order.deal"),
    FILTER_POSITION("position"),
    FILTER_PLAN_ORDER("plan.order"),
    FILTER_STOP_ORDER("stop.order"),
    FILTER_STOP_PLAN_ORDER("stop.planorder"),
    FILTER_RISK_LIMIT("risk.limit"),
    FILTER_ADL("adl.level"),
    FILTER_ASSET("asset");

    String value;

    FilterEventEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static FilterEventEnum parseKey(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        for (FilterEventEnum value : values()) {
            if (value.value.equals(key)) {
                return value;
            }
        }
        return null;
    }
}
