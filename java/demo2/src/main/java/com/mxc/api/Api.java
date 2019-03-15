package com.mxc.api;

import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class Api {
    public static final String ACCESS_KEY = "";
    public static final String SECRET_KEY = "";
    public static final String HOST = "https://www.mxc.com/open";

    public static final String MARKET_LIST = HOST + "/api/v1/data/markets";

    public static final String MARKET_DEPTH = HOST + "/api/v1/data/depth";
    public static final String BID_HISTORY = HOST + "/api/v1/data/history";
    public static final String MARKET_INFO = HOST + "/api/v1/data/markets_info";
    public static final String TIME = HOST + "/api/v1/data/time";
    public static final String ACCOUNT_INFO = HOST + "/api/v1/private/account/info";
    public static final String CURR_ORDER_LIST = HOST + "/api/v1/private/current/orders";
    public static final String ORDER_INFO = HOST + "/api/v1/private/order";
    public static final String CREATE_ORDER = HOST + "/api/v1/private/order";
    public static final String CANCEL_ORDER = HOST + "/api/v1/private/order";
    public static final String ORDER_LIST = HOST + "/api/v1/private/orders";
    public static final String TICKET = HOST + "/api/v1/data/ticker";

    /**
     * 获取市场列表
     *
     * @throws Exception
     */
    @Test
    public void getMarketList() throws Exception {
        URL url = new URL(MARKET_LIST);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.96 Safari/537.36");
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        bufferedReader.lines().forEach(e -> System.out.print(e));
    }

    /**
     * 获取市场深度
     * market = 市场名
     * depth = 深度
     *
     * @throws Exception
     */
    @Test
    public void getMarketDepth() throws Exception {
        URL url = new URL(MARKET_DEPTH + "?market=ETH_USDT&depth=5");
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.96 Safari/537.36");
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        bufferedReader.lines().forEach(e -> System.out.print(e));
    }

    /**
     * 最新成交记录
     * market = 市场名
     *
     * @throws Exception
     */
    @Test
    public void getHistory() throws Exception {
        URL url = new URL(BID_HISTORY + "?market=ETH_USDT");
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.96 Safari/537.36");
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        bufferedReader.lines().forEach(e -> System.out.print(e));
    }

    /**
     * 获取市场信息
     *
     * @throws Exception
     */
    @Test
    public void getMarketInfo() throws Exception {
        URL url = new URL(MARKET_INFO);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.96 Safari/537.36");
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        bufferedReader.lines().forEach(e -> System.out.print(e));
    }

    /**
     * 获取市场行情
     * market = 市场名
     *
     * @throws Exception
     */
    @Test
    public void getTicket() throws Exception {
        String market = "ETH_USDT";
        URL url = new URL(TICKET + "?market=" + market);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.96 Safari/537.36");
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        bufferedReader.lines().forEach(e -> System.out.print(e));
    }

    /**
     * 获取账户金额信息
     * <p>
     * api_key = 平台申请的accessKey
     * req_time = 请求时间
     * sign = 加密内容,使用SignUtil加密(需要平台申请的secret_key)
     *
     * @throws Exception
     */
    @Test
    public void getAccountInfo() throws Exception {
        long time = System.currentTimeMillis();
        SignUtil signUtil = new SignUtil();
        Map<String, String> signParam = new HashMap<>();
        signParam.put("api_key", ACCESS_KEY);
        signParam.put("req_time", time + "");
        String sign = signUtil.sign(signParam, SECRET_KEY);
        URL url = new URL(ACCOUNT_INFO + "?api_key=" + ACCESS_KEY + "&req_time=" + time + "&sign=" + sign);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.96 Safari/537.36");
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        bufferedReader.lines().forEach(e -> System.out.print(e));
    }

    /**
     * 获取当前委托列表
     * market = 委托订单所在的市场
     * page_num = 页数
     * page_size = 每页大小
     * req_time = 请求时间
     * trade_type = 交易类型，0/1/2 (所有/买/卖)
     * api_key = 平台申请的accessKey
     * sign = 加密内容,使用SignUtil加密(需要平台申请的secret_key)
     *
     * @throws Exception
     */
    @Test
    public void getCurrOrders() throws Exception {
        String market = "ETH_USDT";
        String pageNum = "1";
        String tradeType = "0";
        String pageSize = "10";
        long time = System.currentTimeMillis();
        SignUtil signUtil = new SignUtil();
        Map<String, String> signParam = new HashMap<>();
        signParam.put("api_key", ACCESS_KEY);
        signParam.put("market", market);
        signParam.put("page_num", pageNum);
        signParam.put("trade_type", tradeType);
        signParam.put("page_size", pageSize);
        signParam.put("req_time", time + "");
        String sign = signUtil.sign(signParam, SECRET_KEY);
        URL url = new URL(CURR_ORDER_LIST + "?market=" + market + "&page_num=" + pageNum + "&page_size=" + pageSize + "&trade_type=" + tradeType + "&api_key=" + ACCESS_KEY + "&req_time=" + time + "&sign=" + sign);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.96 Safari/537.36");
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        bufferedReader.lines().forEach(e -> System.out.print(e));
    }

    /**
     * 委托下单
     * api_key 平台申请的accessKey
     * market = 下单区的市场名
     * price = 下单价格
     * quantity = 下单数量
     * trade_type = 交易类型：1/2 (买/卖)
     * req_time = 请求时间
     * sign = 加密内容,使用SignUtil加密(需要平台申请的secret_key)
     *
     * @throws Exception
     */
    @Test
    public void createOrder() throws Exception {
        String market = "ETH_USDT";
        String price = "1";
        String quantity = "10";
        String tradeType = "1";
        long time = System.currentTimeMillis();
        SignUtil signUtil = new SignUtil();
        Map<String, String> signParam = new HashMap<>();
        signParam.put("api_key", ACCESS_KEY);
        signParam.put("market", market);
        signParam.put("price", price);
        signParam.put("quantity", quantity);
        signParam.put("trade_type", tradeType);
        signParam.put("req_time", time + "");
        String sign = signUtil.sign(signParam, SECRET_KEY);
        URL url = new URL(CREATE_ORDER);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setRequestProperty("content-type", " application/x-www-form-urlencoded");
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.96 Safari/537.36");
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
        writer.write("api_key=" + ACCESS_KEY + "&market=" + market + "&price=" + price + "&quantity=" + quantity + "&trade_type=" + tradeType + "&req_time=" + time + "&sign=" + sign);
        writer.flush();
        writer.close();
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        bufferedReader.lines().forEach(e -> System.out.print(e));
    }

    /**
     * 获取委托单信息
     * api_key 平台申请的accessKey
     * market = 下单区的市场名
     * req_time = 请求时间
     * trade_no = 委托单号
     * sign = 加密内容,使用SignUtil加密(需要平台申请的secret_key)
     *
     * @throws Exception
     */
    @Test
    public void getOrderInfo() throws Exception {
        String market = "ETH_USDT";
        String tradeNo = "";
        long time = System.currentTimeMillis();
        SignUtil signUtil = new SignUtil();
        Map<String, String> signParam = new HashMap<>();
        signParam.put("api_key", ACCESS_KEY);
        signParam.put("market", market);
        signParam.put("trade_no", tradeNo);
        signParam.put("req_time", time + "");
        String sign = signUtil.sign(signParam, SECRET_KEY);
        URL url = new URL(ORDER_INFO + "?market=" + market + "&trade_no=" + tradeNo + "&api_key=" + ACCESS_KEY + "&req_time=" + time + "&sign=" + sign);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.96 Safari/537.36");
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        bufferedReader.lines().forEach(e -> System.out.print(e));
    }

    /**
     * 取消委托订单
     * api_key 平台申请的accessKey
     * market = 下单区的市场名
     * req_time = 请求时间
     * trade_no = 委托单号
     * sign = 加密内容,使用SignUtil加密(需要平台申请的secret_key)
     *
     * @throws Exception
     */
    @Test
    public void cancelOrder() throws Exception {
        String market = "ETH_USDT";
        String tradeNo = "";
        long time = System.currentTimeMillis();
        SignUtil signUtil = new SignUtil();
        Map<String, String> signParam = new HashMap<>();
        signParam.put("market", market);
        signParam.put("trade_no", tradeNo);
        signParam.put("api_key", ACCESS_KEY);
        signParam.put("req_time", time + "");
        String sign = signUtil.sign(signParam, SECRET_KEY);
        URL url = new URL(CANCEL_ORDER + "?market=" + market + "&trade_no=" + tradeNo + "&api_key=" + ACCESS_KEY + "&req_time=" + time + "&sign=" + sign);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("DELETE");
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setRequestProperty("content-type", " application/x-www-form-urlencoded");
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.96 Safari/537.36");
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        bufferedReader.lines().forEach(e -> System.out.print(e));
    }

    /**
     * 获取委托单列表
     * api_key 平台申请的accessKey
     * market = 下单区的市场名
     * req_time = 请求时间
     * page_num = 页数
     * page_size = 每页显示数
     * trade_type = 交易类型，1/2 (买/卖)
     * sign = 加密内容,使用SignUtil加密(需要平台申请的secret_key)
     *
     * @throws Exception
     */
    @Test
    public void getOrders() throws Exception {
        String market = "ETH_USDT";
        String pageNum = "1";
        String tradeType = "1";
        String pageSize = "10";
        long time = System.currentTimeMillis();
        SignUtil signUtil = new SignUtil();
        Map<String, String> signParam = new HashMap<>();
        signParam.put("api_key", ACCESS_KEY);
        signParam.put("market", market);
        signParam.put("page_num", pageNum);
        signParam.put("trade_type", tradeType);
        signParam.put("page_size", pageSize);
        signParam.put("req_time", time + "");
        String sign = signUtil.sign(signParam, SECRET_KEY);
        URL url = new URL(ORDER_LIST + "?market=" + market + "&page_num=" + pageNum + "&page_size=" + pageSize + "&trade_type=" + tradeType + "&api_key=" + ACCESS_KEY + "&req_time=" + time + "&sign=" + sign);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.96 Safari/537.36");
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        bufferedReader.lines().forEach(e -> System.out.print(e));
    }
    
    /**
     * 获取K线数据
     * market 市场名
     * interval 时间间隔
     * startTime 起始时间(秒级) 当前时间毫秒数/1000
     * limit 返回数据条数
     * @throws Exception
     */
    @Test
    public void getKline() throws Exception{
        String market = "EOS_ETH";
        String interval = "1m";
        Long startTime = new Date().getTime()/1000;
        int limit = 1000;
        URL url = new URL(KLINE + "?market="+market+"&interval="+interval+"&startTime="+startTime+"&limit="+limit);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.96 Safari/537.36");
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        bufferedReader.lines().forEach(e -> System.out.print(e));
    }
}
