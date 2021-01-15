package com.mxc.api.basic;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mxc.api.MxcApiDemo;
import com.mxc.api.request.PlaceOrderReq;
import com.mxc.api.response.MarketDepthWrapper;
import com.mxc.api.response.OpenOrderResp;
import com.mxc.api.response.Result;
import okhttp3.*;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class RestClientWrapper {
    private static final int CONN_TIMEOUT = 20;
    private static final int READ_TIMEOUT = 20;
    private static final int WRITE_TIMEOUT = 20;

    private String secretKey;
    private String accessKey;

    public RestClientWrapper(String secretKey, String accessKey) {
        this.secretKey = secretKey;
        this.accessKey = accessKey;
    }

    private static final OkHttpClient OK_HTTP_CLIENT = createOkHttpClient();

    public Result<MarketDepthWrapper> depth(Map<String, String> params) {
        return get("/open/api/v2/market/depth", params, false, new TypeReference<Result<MarketDepthWrapper>>() {
        });
    }

    public Result<List<OpenOrderResp>> openOrders(Map<String, String> params) {
        return get("/open/api/v2/order/open_orders", params, true, new TypeReference<Result<List<OpenOrderResp>>>() {
        });
    }

    public Result<String> placeOrder(PlaceOrderReq req) {
        return post("/open/api/v2/order/place", req, new TypeReference<Result<String>>() {
        });
    }

    public Result<Map<String, String>> cancel(Map<String, String> params) {
        return delete("/open/api/v2/order/cancel", params, true, new TypeReference<Result<Map<String, String>>>() {
        });
    }


    private <T> T get(String uri, Map<String, String> params, boolean needSign, TypeReference<T> ref) {
        if (params == null) {
            params = new HashMap<>();
        }
        return call("GET", uri, null, params, needSign, ref);
    }

    private <T> T delete(String uri, Map<String, String> params, boolean needSign, TypeReference<T> ref) {
        if (params == null) {
            params = new HashMap<>();
        }
        return call("DELETE", uri, null, params, needSign, ref);
    }

    private <T> T post(String uri, Object object, TypeReference<T> ref) {
        return call("POST", uri, object, new HashMap<>(), true, ref);
    }

    private String createSignature(String method, String uri, Map<String, String> params) {
        StringBuilder sb = new StringBuilder(1024);
        sb.append(method.toUpperCase()).append('\n')
                .append(uri).append('\n');
        SortedMap<String, String> map = new TreeMap<>(params);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append('=').append(SignatureUtil.urlEncode(value)).append('&');
        }
        sb.deleteCharAt(sb.length() - 1);

        return SignatureUtil.actualSignature(sb.toString(), secretKey);
    }

    private <T> T call(String method, String uri, Object object, Map<String, String> params, boolean needSign,
                       TypeReference<T> ref) {
        try {
            params.put("api_key", this.accessKey);
            if (needSign) {
                params.put("req_time", Instant.now().getEpochSecond() + "");
                params.put("recv_window", "60");
                params.put("sign", createSignature(method, uri, params));
            }
            Request.Builder builder;
            if ("POST".equals(method)) {
                RequestBody body = RequestBody.create(MediaType.parse("application/json"), JsonUtil.writeValue(object));
                builder = new Request.Builder().url(MxcApiDemo.REQUEST_HOST + uri + "?" + toQueryString(params)).post(body);
            } else if ("DELETE".equals(method)) {
                builder = new Request.Builder().url(MxcApiDemo.REQUEST_HOST + uri + "?" + toQueryString(params)).delete();
            } else {
                builder = new Request.Builder().url(MxcApiDemo.REQUEST_HOST + uri + "?" + toQueryString(params)).get();
            }
            Request request = builder.build();
            Response response = OK_HTTP_CLIENT.newCall(request).execute();
            return JsonUtil.readValue(response.body().string(), ref);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String toQueryString(Map<String, String> params) {
        return params.entrySet().stream().map((entry) -> entry.getKey() + "=" + SignatureUtil.urlEncode(entry.getValue()))
                .collect(Collectors.joining("&"));
    }

    private static OkHttpClient createOkHttpClient() {
        return new OkHttpClient.Builder().connectTimeout(CONN_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS).writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }
}
