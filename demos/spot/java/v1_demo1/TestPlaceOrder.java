package com.mexc.open.api.controller.api;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
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

public class TestPlaceOrder {

    public static void main(String[] args) {
        SignUtil util = new SignUtil();

        Map<String, String> params = new HashMap<>();
        params.put("api_key", "mxcVSRmGUlzZ4kTjko");
        //params.put("market", "omg_btc");
        params.put("market", "poly_eth");
        params.put("price", "0.0000001");
        params.put("quantity", "50000");
        params.put("trade_type", "1");
        params.put("req_time", "1529049965959");
        params.put("sign", util.sign(params, ""));

        List<NameValuePair> httpParams = new ArrayList<>();
        params.forEach((key, value) -> httpParams.add(new BasicNameValuePair(key, value)));

        //HttpPost post = new HttpPost("https://www.mxc.com/open/api/v1/private/order");
        HttpPost post = new HttpPost("https://mexc.p2.work/open/api/v1/private/order");
        try {
            post.setEntity(new UrlEncodedFormEntity(httpParams));
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            response = httpClient.execute(post);
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
