package com.mexc.open.api.controller.api;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.util.TreeMap;

public class TestCancelOrder {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            work();
        }
    }

    private static void work() {
        SignUtil util = new SignUtil();

        TreeMap<String, String> params = new TreeMap<>();
        params.put("api_key", "mxcVSRmGUlzZ4kTjko");
        params.put("market", "poly_eth");
        params.put("trade_no", "6d87c9cd-27a7-4413-8778-fe4cd322e48e");
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

        //HttpDelete delete = new HttpDelete("https://www.mxc.com/open/api/v1/private/order?" + sb.toString());
        HttpDelete delete = new HttpDelete("https://mexc.p2.work/open/api/v1/private/order?" + sb.toString());

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
            response = httpClient.execute(delete);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != 200) {
                System.out.println("Execute request with status " + statusCode);
            } else {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String content = EntityUtils.toString(entity, Consts.UTF_8);
                    System.out.println(content);
                }
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
