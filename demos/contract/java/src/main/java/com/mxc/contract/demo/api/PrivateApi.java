package com.mxc.contract.demo.api;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import com.mxc.contract.demo.request.identified.*;
import com.mxc.contract.demo.response.Result;
import com.mxc.contract.demo.response.identified.*;
import com.mxc.contract.demo.utils.SignatureUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

import java.util.*;

import static com.mxc.contract.demo.utils.SignatureUtils.getRequestParamString;
import static com.mxc.contract.demo.utils.SignatureUtils.requestParamOfGet;


public class PrivateApi {
    private String url;
    private String accessKey;
    private String privateKey;

    public PrivateApi(String url, String accessKey, String privateKey) {
        this.url = url;
        this.accessKey = accessKey;
        this.privateKey = privateKey;
    }


    /**
     * 获取用户所有资产信息
     *
     * @return
     */
    public Result<List<AccountAssetsResp>> getAccountAssets() {
        SignatureUtils.SignVo signVo = signVoOfGet(null);
        String uri = url.concat("/api/v1/private/account/assets");
        return ApiClient.get(uri, signVo, new TypeReference<Result<List<AccountAssetsResp>>>() {
        });
    }

    /**
     * 获取用户单个币种资产信息
     *
     * @param currency
     * @return
     */
    public Result<AccountAssetResp> getAccountAsset(String currency) {
        SignatureUtils.SignVo signVo = signVoOfGet(null);
        String uri = url.concat("/api/v1/private/account/asset/").concat(currency);
        return ApiClient.get(uri, signVo, new TypeReference<Result<AccountAssetResp>>() {
        });
    }

    /**
     * 获取用户资产划转记录
     *
     * @param req
     * @return
     */
    public Result<AccountTransferRecordResp> getAccountTransferRecord(AccountTransferRecordReq req) {
        String param = requestParamOfGet(req);
        SignatureUtils.SignVo signVo = signVoOfGet(param);
        String uri = url.concat("/api/v1/private/account/transfer_record");
        uri = uri.concat("?").concat(param);
        return ApiClient.get(uri, signVo, new TypeReference<Result<AccountTransferRecordResp>>() {
        });
    }

    /**
     * 获取用户历史持仓信息
     *
     * @param req
     * @return
     */
    public Result<HistoryPositionsResp> getHistoryPositions(HistoryPositionsReq req) {
        String param = requestParamOfGet(req);
        SignatureUtils.SignVo signVo = signVoOfGet(param);
        String uri = url.concat("/api/v1/private/position/history_positions");
        uri = uri.concat("?").concat(param);
        return ApiClient.get(uri, signVo, new TypeReference<Result<HistoryPositionsResp>>() {
        });

    }

    /**
     * 获取用户当前持仓
     *
     * @param symbol
     * @return
     */
    public Result<List<OpenPositionsResp>> getOpenPositions(@Nullable String symbol) {
        Map<String, Object> params = new HashMap<>(4);
        Optional.ofNullable(symbol).map(s -> params.put("symbol", symbol));
        String param = requestParamOfGet(params);
        SignatureUtils.SignVo signVo = signVoOfGet(param);
        String uri = url.concat("/api/v1/private/position/open_positions");
        uri = StringUtils.isEmpty(param) ? uri : uri.concat("?").concat(param);
        return ApiClient.get(uri, signVo, new TypeReference<Result<List<OpenPositionsResp>>>() {
        });
    }

    /**
     * 获取用户资金费用明细
     *
     * @param req
     * @return
     */
    public Result<FundingRecordsResp> getFundingRecords(FundingRecordReq req) {
        String param = requestParamOfGet(req);
        SignatureUtils.SignVo signVo = signVoOfGet(param);
        String uri = url.concat("/api/v1/private/position/funding_records");
        uri = uri.concat("?").concat(param);
        return ApiClient.get(uri, signVo, new TypeReference<Result<FundingRecordsResp>>() {
        });
    }

    /**
     * 获取用户当前未结束订单
     *
     * @param req
     * @return
     */
    public Result<OpenOrdersResp> getOpenOrders(OpenOrdersReq req) {
        String symbol = req.getSymbol();
        req.setSymbol(null);
        String param = requestParamOfGet(req);
        SignatureUtils.SignVo signVo = signVoOfGet(param);
        String uri = url.concat("/api/v1/private/order/open_orders");
        if (!StringUtils.isEmpty(symbol)) {
            uri = uri.concat("/").concat(symbol);
        }
        uri = uri.concat("?").concat(param);
        return ApiClient.get(uri, signVo, new TypeReference<Result<OpenOrdersResp>>() {
        });
    }

    /**
     * 获取用户所有历史订单
     *
     * @param req
     * @return
     */
    public Result<HistoryOrdersResp> getHistoryOrders(HistoryOrdersReq req) {
        String param = requestParamOfGet(req);
        SignatureUtils.SignVo signVo = signVoOfGet(param);
        String uri = url.concat("/api/v1/private/order/history_orders");
        uri = uri.concat("?").concat(param);
        return ApiClient.get(uri, signVo, new TypeReference<Result<HistoryOrdersResp>>() {
        });
    }

    /**
     * 根据外部号查询订单
     *
     * @param symbol
     * @param externalOid
     * @return
     */
    public Result<OrderRespDTO> getOderByExternalId(String symbol, String externalOid) {
        SignatureUtils.SignVo signVo = signVoOfGet(null);
        String uri = url.concat("/api/v1/private/order/external/").concat(symbol).concat("/").concat(externalOid);
        return ApiClient.get(uri, signVo, new TypeReference<Result<OrderRespDTO>>() {
        });
    }

    /**
     * 根据订单号查询订单
     *
     * @param orderId
     * @return
     */
    public Result<OrderRespDTO> getOderByOrderId(Long orderId) {
        SignatureUtils.SignVo signVo = signVoOfGet(null);
        String uri = url.concat("/api/v1/private/order/get/" + orderId);
        return ApiClient.get(uri, signVo, new TypeReference<Result<OrderRespDTO>>() {
        });
    }

    /**
     * 根据订单号批量查询订单
     *
     * @param orderIds
     * @return
     */
    public Result<List<OrderRespDTO>> batchQueryByOrderIds(List<Long> orderIds) {
        StringBuilder sb = new StringBuilder();
        orderIds.forEach(id -> sb.append(id).append(","));
        String ids = sb.substring(0, sb.length() - 1);
        Map<String, String> map = new HashMap<>();
        map.put("order_ids", ids);
        String param = getRequestParamString(map);
        SignatureUtils.SignVo signVo = signVoOfGet(param);
        String uri = url.concat("/api/v1/private/order/batch_query?order_ids=" + ids);
        return ApiClient.get(uri, signVo, new TypeReference<Result<List<OrderRespDTO>>>() {
        });
    }

    /**
     * 根据订单号获取订单成交明细
     *
     * @param orderId
     * @return
     */
    public Result<List<DealDetailDTO>> getDealDetailById(Long orderId) {
        SignatureUtils.SignVo signVo = signVoOfGet(null);
        String uri = url.concat("/api/v1/private/order/deal_details/" + orderId);
        return ApiClient.get(uri, signVo, new TypeReference<Result<List<DealDetailDTO>>>() {
        });
    }

    /**
     * 获取用户所有订单成交明细
     *
     * @param req
     * @return
     */
    public Result<OrderDealsResp> getOrderDeals(OrderDealsReq req) {
        String param = requestParamOfGet(req);
        SignatureUtils.SignVo signVo = signVoOfGet(param);
        String uri = url.concat("/api/v1/private/order/order_deals");
        uri = uri.concat("?").concat(param);
        return ApiClient.get(uri, signVo, new TypeReference<Result<OrderDealsResp>>() {
        });
    }

    /**
     * 获取计划委托订单列表
     *
     * @param req
     * @return
     */
    public Result<PlanOrdersResp> getPlanOrders(PlanOrdersReq req) {
        String param = requestParamOfGet(req);
        SignatureUtils.SignVo signVo = signVoOfGet(param);
        String uri = url.concat("/api/v1/private/planorder/orders");
        uri = uri.concat("?").concat(param);
        return ApiClient.get(uri, signVo, new TypeReference<Result<PlanOrdersResp>>() {
        });
    }

    /**
     * 获取止盈止损订单列表
     *
     * @param req
     * @return
     */
    public Result<StopOrdersResp> getStopOrders(StopOrdersReq req) {
        String param = requestParamOfGet(req);
        SignatureUtils.SignVo signVo = signVoOfGet(param);
        String uri = url.concat("/api/v1/private/stoporder/orders");
        uri = uri.concat("?").concat(param);
        return ApiClient.get(uri, signVo, new TypeReference<Result<StopOrdersResp>>() {
        });
    }

    /**
     * 获取风险限额
     *
     * @param symbol
     * @return
     */
    public Result<Map<String, List<RiskLimitDTO>>> getAccountRiskLimit(@Nullable String symbol) {
        String uri = url.concat("/api/v1/private/account/risk_limit");
        String param = null;
        if (!StringUtils.isEmpty(symbol)) {
            param = "symbol=" + symbol;
            uri = uri.concat("?").concat(param);
        }
        SignatureUtils.SignVo signVo = signVoOfGet(param);
        return ApiClient.get(uri, signVo, new TypeReference<Result<Map<String, List<RiskLimitDTO>>>>() {
        });
    }

    /**
     * 获取用户当前手续费率
     *
     * @param symbol
     * @return
     */
    public Result<TieredFeeRateResp> getTieredFeeRate(String symbol) {
        String uri = url.concat("/api/v1/private/account/tiered_fee_rate");
        String param = "symbol=" + symbol;
        uri = uri.concat("?").concat(param);
        SignatureUtils.SignVo signVo = signVoOfGet(param);
        return ApiClient.get(uri, signVo, new TypeReference<Result<TieredFeeRateResp>>() {
        });
    }

    /**
     * 增加或减少仓位保证金
     *
     * @param req
     * @return
     */
    public Result<Object> changeMargin(ChangeMarginReq req) {
        String uri = url.concat("/api/v1/private/position/change_margin");
        SignatureUtils.SignVo signVo = signVoOfPost(req);
        return ApiClient.post(uri, signVo, new TypeReference<Result<Object>>() {
        });
    }

    /**
     * 修改杠杆倍数
     *
     * @param req
     * @return
     */
    public Result<Object> changeLeverage(ChangeLeverageReq req) {
        String uri = url.concat("/api/v1/private/position/change_leverage");
        SignatureUtils.SignVo signVo = signVoOfPost(req);
        return ApiClient.post(uri, signVo, new TypeReference<Result<Object>>() {
        });
    }

    /**
     * 下单
     *
     * @param req
     * @return orderId
     */
    public Result<Long> submitOrder(SubmitOrderReq req) {
        String uri = url.concat("/api/v1/private/order/submit");
        SignatureUtils.SignVo signVo = signVoOfPost(req);
        return ApiClient.post(uri, signVo, new TypeReference<Result<Long>>() {
        });
    }

    /**
     * 批量下单
     *
     * @param orderReqList 最大50条
     * @return
     */
    public Result<List<SubmitBatchRespDTO>> submitBatchOrder(List<OrderReqDTO> orderReqList) {
        String uri = url.concat("/api/v1/private/order/submit_batch");
        SignatureUtils.SignVo signVo = signVoOfPost(orderReqList);
        return ApiClient.post(uri, signVo, new TypeReference<Result<List<SubmitBatchRespDTO>>>() {
        });

    }

    /**
     * 取消订单
     *
     * @param orderIds 订单id列表,最大50条
     * @return
     */
    public Result<Object> cancelOrders(List<Long> orderIds) {
        String uri = url.concat("/api/v1/private/order/cancel");
        SignatureUtils.SignVo signVo = signVoOfPost(orderIds);
        return ApiClient.post(uri, signVo, new TypeReference<Result<Object>>() {
        });
    }

    /**
     * 根据外部订单号取消订单
     *
     * @param req
     * @return
     */
    public Result<Object> cancelWithExternal(CancelOrderWithExternalReq req) {
        String uri = url.concat("/api/v1/private/order/cancel_with_external");
        SignatureUtils.SignVo signVo = signVoOfPost(req);
        return ApiClient.post(uri, signVo, new TypeReference<Result<Object>>() {
        });
    }

    /**
     * 取消某合约下所有订单
     *
     * @param symbol 合约名,传入symbol只取消该合约下的订单，不传取消所有合约下的订单
     * @return
     */
    public Result<Object> cancelAllOrders(@Nullable String symbol) {
        String uri = url.concat("/api/v1/private/order/cancel_all");
        Map<String, Object> param = null;
        if (!StringUtils.isEmpty(symbol)) {
            param = new HashMap<>();
            param.put("symbol", symbol);
        }
        SignatureUtils.SignVo signVo = signVoOfPost(param);
        return ApiClient.post(uri, signVo, new TypeReference<Result<Object>>() {
        });
    }

    /**
     * 修改风险等级
     *
     * @param req
     * @return
     */
    public Result<Object> changeRiskLevel(ChangeRiskLevelReq req) {
        String uri = url.concat("/api/v1/private/account/change_risk_level");
        SignatureUtils.SignVo signVo = signVoOfPost(req);
        return ApiClient.post(uri, signVo, new TypeReference<Result<Object>>() {
        });
    }

    /**
     * 计划委托下单
     *
     * @param req
     * @return orderId
     */
    public Result<String> submitPlanOrder(PlanOrderReq req) {
        String uri = url.concat("/api/v1/private/planorder/place");
        SignatureUtils.SignVo signVo = signVoOfPost(req);
        return ApiClient.post(uri, signVo, new TypeReference<Result<String>>() {
        });
    }

    /**
     * 取消计划委托订单
     *
     * @param reqDTOList
     * @return
     */
    public Result<Object> cancelPlanOrder(List<CancelOrderReqDTO> reqDTOList) {
        String uri = url.concat("/api/v1/private/planorder/cancel");
        SignatureUtils.SignVo signVo = signVoOfPost(reqDTOList);
        return ApiClient.post(uri, signVo, new TypeReference<Result<Object>>() {
        });
    }

    /**
     * 取消所有计划委托订单
     *
     * @param symbol
     * @return
     */
    public Result<Object> cancelAllPlanOrder(String symbol) {
        String uri = url.concat("/api/v1/private/planorder/cancel_all");
        Map<String, Object> param = null;
        if (!StringUtils.isEmpty(symbol)) {
            param = Maps.newHashMap();
            param.put("symbol", symbol);
        }
        SignatureUtils.SignVo signVo = signVoOfPost(param);
        return ApiClient.post(uri, signVo, new TypeReference<Result<Object>>() {
        });
    }

    /**
     * 取消止盈止损委托单
     *
     * @param reqDTOList 取消订单列表,最大50条
     * @return
     */
    public Result<Object> cancelStopOrders(List<StopOrderCancelDTO> reqDTOList) {
        String uri = url.concat("/api/v1/private/stoporder/cancel");
        SignatureUtils.SignVo signVo = signVoOfPost(reqDTOList);
        return ApiClient.post(uri, signVo, new TypeReference<Result<Object>>() {
        });
    }

    /**
     * 取消所有止盈止损委托单
     *
     * @param req
     * @return
     */
    public Result<Object> cancelAllStopOrders(StopOrderCancelAllReq req) {
        String uri = url.concat("/api/v1/private/stoporder/cancel_all");
        SignatureUtils.SignVo signVo = signVoOfPost(req);
        return ApiClient.post(uri, signVo, new TypeReference<Result<Object>>() {
        });
    }

    /**
     * 修改限价单止盈止损价格
     *
     * @param req
     * @return
     */
    public Result<Object> stopOrderChangePrice(StoporderChangePriceReq req) {
        String uri = url.concat("/api/v1/private/stoporder/change_price");
        SignatureUtils.SignVo signVo = signVoOfPost(req);
        return ApiClient.post(uri, signVo, new TypeReference<Result<Object>>() {
        });
    }

    /**
     * 修改止盈止损委托单止盈止损价格
     *
     * @param req
     * @return
     */
    public Result<Object> stopOrderChangePlanPriceReq(StopOrderChangePlanPriceReq req) {
        String uri = url.concat("/api/v1/private/stoporder/change_plan_price");
        SignatureUtils.SignVo signVo = signVoOfPost(req);
        return ApiClient.post(uri, signVo, new TypeReference<Result<Object>>() {
        });
    }


    private SignatureUtils.SignVo signVoOfGet(String param) {
        return signVoOf(param);
    }

    private SignatureUtils.SignVo signVoOfPost(Object param) {
        String content = Objects.isNull(param) ? null : JSONObject.toJSONString(param);
        return signVoOf(content);
    }

    private SignatureUtils.SignVo signVoOf(String param) {
        SignatureUtils.SignVo signVo = new SignatureUtils.SignVo();
        long epochSecond = System.currentTimeMillis();
        signVo.setReqTime(epochSecond + "");
        signVo.setSecretKey(privateKey);
        signVo.setAccessKey(accessKey);
        signVo.setRequestParam(param);
        return signVo;
    }
}
