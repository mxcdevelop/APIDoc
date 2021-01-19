package com.mxc.contract.demo.api;

import com.alibaba.fastjson.TypeReference;
import com.mxc.contract.demo.request.open.ContractHistoryReq;
import com.mxc.contract.demo.request.open.ContractKlineFairPriceReq;
import com.mxc.contract.demo.request.open.ContractKlineIndexPriceReq;
import com.mxc.contract.demo.request.open.ContractKlineReq;
import com.mxc.contract.demo.response.Result;
import com.mxc.contract.demo.response.open.*;
import com.mxc.contract.demo.utils.UrlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

public class PublicApi {
    private String url;

    public PublicApi(String url) {
        this.url = url;
    }

    /**
     * 获取服务器时间
     *
     * @return
     */
    public Result<Long> getSystemTime() {
        return ApiClient.get(url.concat("/api/v1/contract/ping"), new TypeReference<Result<Long>>() {
        });
    }

    /**
     * 获取合约信息
     *
     * @param symbol
     * @return
     */
    public Result<List<ContractResp>> getContractDetail(String symbol) {
        String uri = "/api/v1/contract/detail".concat(StringUtils.isBlank(symbol) ? "" : "?symbol=" + symbol);
        return ApiClient.get(url.concat(uri), new TypeReference<Result<List<ContractResp>>>() {
        });
    }

    /**
     * 获取可划转币种
     *
     * @return
     */
    public Result<List<String>> getSupportCurrencies() {
        return ApiClient.get(url.concat("/api/v1/contract/support_currencies"), new TypeReference<Result<List<String>>>() {
        });
    }

    /**
     * 获取合约深度信息
     *
     * @param symbol
     * @param limit
     * @return
     */
    public Result<ContractDepthResp> getContractDepth(String symbol, @Nullable Integer limit) {
        Assert.notNull(symbol, "symbol can't be null");
        String uri = url.concat("/api/v1/contract/depth/").concat(symbol);
        Optional.ofNullable(limit).map(l -> uri.concat("/").concat(l.toString()));
        return ApiClient.get(uri, new TypeReference<Result<ContractDepthResp>>() {
        });
    }

    /**
     * 获取合约最近N条深度信息快照
     *
     * @param symbol
     * @param limit
     * @return
     */
    public Result<List<ContractDepthCommitsRespDTO>> getContractDepthCommits(String symbol, int limit) {
        String uri = url.concat("/api/v1/contract/depth_commits/").concat(symbol).concat("/").concat(String.valueOf(limit));
        return ApiClient.get(uri, new TypeReference<Result<List<ContractDepthCommitsRespDTO>>>() {
        });
    }

    /**
     * 获取合约指数价格
     *
     * @param symbol
     * @return
     */
    public Result<ContractIndexPriceResp> getContractIndexPrice(String symbol) {
        String uri = url.concat("/api/v1/contract/index_price/").concat(symbol);
        return ApiClient.get(uri, new TypeReference<Result<ContractIndexPriceResp>>() {
        });
    }

    /**
     * 获取合约合理价格
     *
     * @param symbol
     * @return
     */
    public Result<ContractFairPriceResp> getContractFairPrice(String symbol) {
        String uri = url.concat("/api/v1/contract/fair_price/").concat(symbol);
        return ApiClient.get(uri, new TypeReference<Result<ContractFairPriceResp>>() {
        });
    }

    /**
     * 获取合约资金费率
     *
     * @param symbol
     * @return
     */
    public Result<ContractFundingRateResp> getContractFundingRate(String symbol) {
        String uri = url.concat("/api/v1/contract/funding_rate/").concat(symbol);
        return ApiClient.get(uri, new TypeReference<Result<ContractFundingRateResp>>() {
        });
    }

    /**
     * 获取蜡烛图数据
     *
     * @param req
     * @return
     */
    public Result<ContractKlineResp> getContractKline(ContractKlineReq req) {
        String uri = url + "/api/v1/contract/kline/" + req.getSymbol();
        req.setSymbol(null);
        String param = UrlUtils.requestParamOfGet(req);
        uri = uri + "?" + param;
        return ApiClient.get(uri, new TypeReference<Result<ContractKlineResp>>() {
        });

    }

    /**
     * 获取指数价格蜡烛图数据
     *
     * @param req
     * @return
     */
    public Result<ContractKlineIndexPriceResp> getContractKlineIndexPrice(ContractKlineIndexPriceReq req) {
        String uri = url + "/api/v1/contract/kline/index_price/" + req.getSymbol();
        req.setSymbol(null);
        String param = UrlUtils.requestParamOfGet(req);
        uri = uri + "?" + param;
        return ApiClient.get(uri, new TypeReference<Result<ContractKlineIndexPriceResp>>() {
        });

    }

    /**
     * 获取合理价格蜡烛图数据
     *
     * @param req
     * @return
     */
    public Result<ContractKlineFairPriceResp> getContractKlineFairPrice(ContractKlineFairPriceReq req) {
        String uri = url + "/api/v1/contract/kline/fair_price/" + req.getSymbol();
        req.setSymbol(null);
        String param = UrlUtils.requestParamOfGet(req);
        uri = uri + "?" + param;
        return ApiClient.get(uri, new TypeReference<Result<ContractKlineFairPriceResp>>() {
        });

    }

    /**
     * 获取成交数据
     *
     * @param symbol
     * @param limit
     * @return
     */
    public Result<List<ContractDealsResp>> getContractDeals(String symbol, @Nullable Integer limit) {
        String uri = url.concat("/api/v1/contract/deals/").concat(symbol);
        Optional.ofNullable(limit).map(l -> uri.concat("/").concat(l.toString()));
        return ApiClient.get(uri, new TypeReference<Result<List<ContractDealsResp>>>() {
        });
    }

    /**
     * 获取合约行情数据
     *
     * @param symbol
     * @return
     */
    public Result<List<ContractTickerResp>> getContractTicker(@Nullable String symbol) {
        String uri = url.concat("/api/v1/contract/ticker");
        Optional.ofNullable(symbol).map(uri::concat);
        return ApiClient.get(uri, new TypeReference<Result<List<ContractTickerResp>>>() {
        });
    }

    /**
     * 获取所有合约风险基金余额
     *
     * @return
     */
    public Result<List<ContractRiskReverseResp>> getContractRiskReserve() {
        String uri = url.concat("/api/v1/contract/risk_reverse");
        return ApiClient.get(uri, new TypeReference<Result<List<ContractRiskReverseResp>>>() {
        });
    }

    /**
     * 获取合约风险基金余额历史
     *
     * @param req
     * @return
     */
    public Result<ContractRiskReserveHistoryResp> getContractRiskReserveHistory(ContractHistoryReq req) {
        String uri = url.concat("/api/v1/contract/risk_reverse/history")
                .concat("?").concat(UrlUtils.requestParamOfGet(req));
        return ApiClient.get(uri, new TypeReference<Result<ContractRiskReserveHistoryResp>>() {
        });
    }

    /**
     * 获取合约资金费率历史
     *
     * @param req
     * @return
     */
    public Result<ContractFundingRateHistoryResp> getContractFundingRateHistory(ContractHistoryReq req) {
        String uri = url.concat("/api/v1/contract/funding_rate/history")
                .concat("?").concat(UrlUtils.requestParamOfGet(req));

        return ApiClient.get(uri, new TypeReference<Result<ContractFundingRateHistoryResp>>() {
        });
    }
}
