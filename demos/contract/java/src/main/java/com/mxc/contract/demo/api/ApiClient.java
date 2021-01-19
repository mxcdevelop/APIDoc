package com.mxc.contract.demo.api;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.mxc.contract.demo.response.Result;
import com.mxc.contract.demo.utils.SignatureUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class ApiClient {
    private static RestTemplate restTemplate;

    static {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(30000);
        factory.setReadTimeout(10000);
        restTemplate = new RestTemplate(factory);
    }

    public static <T> Result<T> get(String url, TypeReference<Result<T>> typeReference) {
        String body = restTemplate.getForEntity(url, String.class).getBody();
        return JSONObject.parseObject(body, typeReference);
    }

    public static <T> Result<T> get(String url, SignatureUtils.SignVo signVo, TypeReference<Result<T>> typeReference) {
        HttpEntity<Object> entity = new HttpEntity<>(getHeader(signVo));
        ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return JSONObject.parseObject(result.getBody(), typeReference);
    }


    public static <T> Result<T> post(String url, Object params, TypeReference<Result<T>> typeReference) {
        String body = restTemplate.postForEntity(url, JSONObject.toJSON(params), String.class).getBody();
        return JSONObject.parseObject(body, typeReference);
    }

    public static <T> Result<T> post(String url, SignatureUtils.SignVo signVo, TypeReference<Result<T>> typeReference) {
        String body = restTemplate.postForEntity(url, new HttpEntity<>(signVo.getRequestParam(), getHeader(signVo)), String.class).getBody();
        return JSONObject.parseObject(body, typeReference);
    }

    private static HttpHeaders getHeader(SignatureUtils.SignVo signVo) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("ApiKey", signVo.getAccessKey());
        headers.add("Request-Time", "" + signVo.getReqTime());
        headers.add("Signature", SignatureUtils.signature(signVo));
        headers.add("Content-Type", "application/json");
        return headers;
    }
}
