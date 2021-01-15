package com.mexc.open.api.controller.api;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestGetAccount {

    public static void main(String[] args) {
        SignUtil util = new SignUtil();

        Map<String, String> params = new HashMap<>();
        params.put("api_key", "mxcVSRmGUlzZ4kTjko");
        params.put("req_time", "1529049965959");
        params.put("sign", util.sign(params, ""));
        StringBuilder sb = new StringBuilder();
        params.entrySet()
                .forEach(entry -> {
                    if (sb.length() > 0) {
                        sb.append("&");
                    }
                    sb.append(entry.getKey()).append("=");
                    sb.append(entry.getValue());
                });

        HttpGet get = new HttpGet("https://www.mxc.com/open/api/v1/private/account/info?" + sb.toString());
        //HttpGet get = new HttpGet("https://mexc.p2.work/open/api/v1/private/account/info?" + sb.toString());
        //HttpGet get = new HttpGet("http://localhost:8200/open/api/v1/private/account/info?" + sb.toString());

        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        RequestConfig config = RequestConfig.custom().build();
        ConnectionKeepAliveStrategy keepAliveStrategy = new DefaultConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response,
                                             HttpContext context) {
                long time = super.getKeepAliveDuration(response, context);
                return time == -1 ? 300000L : time;
            }
        };

        CloseableHttpClient httpClient = HttpClients.custom()
                .setKeepAliveStrategy(keepAliveStrategy)
                .setConnectionManager(connManager)
                .setDefaultRequestConfig(config).build();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String content = EntityUtils.toString(entity, Consts.UTF_8);
                System.out.println(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {}
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (Exception e) {}
        }
    }
}
