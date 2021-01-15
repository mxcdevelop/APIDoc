package com.mxc.api;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

public class SignUtil {

    public String sign(Map<String, String> paramMap, String secret) {
        StringBuilder sb = new StringBuilder();
        TreeMap<String, String> params = new TreeMap<>();
        params.putAll(paramMap);
        params.entrySet()
                .forEach(entry -> {
                    if (sb.length() > 0) {
                        sb.append("&");
                    }
                    sb.append(entry.getKey()).append("=");
                    sb.append(entry.getValue());
                });
        sb.append("&").append("api_secret").append("=").append(secret);
        return sign(sb.toString());
    }

    private String sign(String target) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        md.update(target.getBytes());
        byte dg[] = md.digest();
        StringBuilder output = new StringBuilder(dg.length * 2);
        for (byte dgByte : dg) {
            int current = dgByte & 0xff;
            if (current < 16)
                output.append("0");
            output.append(Integer.toString(current, 16));
        }
        return output.toString();
    }
}