package com.mxc.contract.demo.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.collections.MapUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.util.Assert;

import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 非签名的get 传参数
 */
public class UrlUtils {
    public static String requestParamOfGet(Object param) {
        Assert.notNull(param);
        String json = JSONObject.toJSONString(param);
        Map<String, String> paramMap = JSONObject.parseObject(json, new TypeReference<Map<String, String>>() {
        });
        return requestParamOfGet(paramMap);
    }

    public static String requestParamOfGet(Map<String, String> map) {
        Assert.notNull(map);
        if (MapUtils.isEmpty(map)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        map.forEach((k, v) -> {
            if (Objects.nonNull(v)) {
                try {
                    sb.append(k).append("=").append(URLEncoder.encode(v, "UTF-8")).append("&");
                } catch (Exception ignore) {

                }

            }
        });
        return sb.substring(0, sb.length() - 1);
    }

}
