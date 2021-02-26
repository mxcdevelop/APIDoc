package com.mxc.contract.ws.utils;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SignatureUtils {

    public static String signature(SignVo signVo) {
        return sign(signVo);
    }


    private static String sign(SignVo signVo) {
        if (signVo.getRequestParam() == null) {
            signVo.setRequestParam("");
        }
        String str = signVo.getAccessKey() + signVo.getReqTime() + signVo.getRequestParam();
        return actualSignature(str, signVo.getSecretKey());
    }

    private static String actualSignature(String inputStr, String key) {
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

    @Getter
    @Setter
    public static class SignVo {
        private String reqTime;
        private String accessKey;
        private String secretKey;
        private String requestParam;
    }
}
