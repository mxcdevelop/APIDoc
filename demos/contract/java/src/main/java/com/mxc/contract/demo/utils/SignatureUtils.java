package com.mxc.contract.demo.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class SignatureUtils {

    public static String requestParamOfGet(Object param) {
        Assert.notNull(param);
        String json = JSONObject.toJSONString(param);
        Map<String, String> paramMap = JSONObject.parseObject(json, new TypeReference<LinkedHashMap<String, String>>() {
        });
        return getRequestParamString(paramMap);
    }

    /**
     * 获取get请求参数字符串
     *
     * @param param get/delete请求参数map
     * @return
     */
    public static String getRequestParamString(Map<String, String> param) {
        if (MapUtils.isEmpty(param)) {
            return "";
        }
        StringBuilder sb = new StringBuilder(1024);
        SortedMap<String, String> map = new TreeMap<>(param);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = StringUtils.isBlank(entry.getValue()) ? "" : entry.getValue();
            sb.append(key).append('=').append(urlEncode(value)).append('&');
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 加签
     *
     * @param signVo
     * @return
     */
    public static String signature(SignVo signVo) {
        return sign(signVo);
    }

    /**
     * 验签
     *
     * @param signStr 签名字符串
     * @param signVo  签名参数
     * @return
     */
    public static boolean validSignature(String signStr, SignVo signVo) {
        if (StringUtils.isAnyBlank(signStr, signVo.getAccessKey(), signVo.getSecretKey(), signVo.getReqTime())) {
            return false;
        }
        return signStr.equals(sign(signVo));
    }

    private static String sign(SignVo signVo) {
        if (signVo.getRequestParam() == null) {
            signVo.setRequestParam("");
        }
        String str = signVo.getAccessKey() + signVo.getReqTime() + signVo.getRequestParam();
        return actualSignature(str, signVo.getSecretKey());
    }

    public static String actualSignature(String inputStr, String key) {
        Mac hmacSha256;
        try {
            hmacSha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secKey =
                    new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            hmacSha256.init(secKey);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No such algorithm: " + e.getMessage());
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Invalid key: " + e.getMessage());
        }
        byte[] hash = hmacSha256.doFinal(inputStr.getBytes(StandardCharsets.UTF_8));
        return Hex.encodeHexString(hash);
    }

    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("UTF-8 encoding not supported!");
        }
    }

    @Getter
    @Setter
    public static class SignVo {
        private String reqTime;
        private String accessKey;
        private String secretKey;
        private String requestParam; //get请求参数根据字典顺序排序,使用&拼接字符串,post为json字符串
    }



}
