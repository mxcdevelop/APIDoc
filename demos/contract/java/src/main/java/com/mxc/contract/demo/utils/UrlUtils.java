package com.mxc.contract.demo.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.collections.MapUtils;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Objects;

public class UrlUtils {
    public static String requestParamOfGet(Object param) {
        Assert.notNull(param);
        String json = JSONObject.toJSONString(param);
        Map<String, Object> paramMap = JSONObject.parseObject(json, new TypeReference<Map<String, Object>>() {
        });
        return requestParamOfGet(paramMap);
    }

    public static String requestParamOfGet(Map<String, Object> map) {
        Assert.notNull(map);
        if (MapUtils.isEmpty(map)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        map.forEach((k, v) -> {
            if (Objects.nonNull(v)) {
                sb.append(k).append("=").append(v).append("&");
            }
        });
        return sb.substring(0, sb.length() - 1);
    }
}
