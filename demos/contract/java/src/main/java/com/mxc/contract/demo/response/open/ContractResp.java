package com.mxc.contract.demo.response.open;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ContractResp implements Serializable {
    private String symbol;
    private String displayName;
    private String displayNameEn;
    // 开仓模式
    private int positionOpenType;

    // 标的货币 如 BTC
    private String baseCoin;
    // 标价货币 如 USDT
    private String quoteCoin;

    // 结算货币 如 USDT
    private String settleCoin;
    // 合约面值
    private BigDecimal contractSize;

    // 杠杆倍数上下限
    private Integer minLeverage;
    private Integer maxLeverage;

    // 价格小数位数
    private Integer priceScale;
    private Integer volScale;
    private Integer amountScale;

    // 价格的最小步进单位， 比如BTC 0.5
    private BigDecimal priceUnit;
    private BigDecimal volUnit;

    // 订单张数上下限
    private BigDecimal minVol;
    private BigDecimal maxVol;

    // 合约下单价格限制， 例如：
    // - 最高买价 = 合理价格*（1+3%）
    // - 最低卖价 = 合理价格*（1-3%）
    private BigDecimal bidLimitPriceRate;
    private BigDecimal askLimitPriceRate;

    // 费率, 约定takerFeeRate只能是正数， makerFeeRate可以为负数， 并且abs(takerFeeRate) >= abs(makerFeeRate)
    private BigDecimal takerFeeRate;
    private BigDecimal makerFeeRate;

    // 维持保证金率
    private BigDecimal maintenanceMarginRate;
    // 初始保证金率
    private BigDecimal initialMarginRate;
    // 基本张数
    private BigDecimal riskBaseVol;
    // 递增张数
    private BigDecimal riskIncrVol;
    // 维持保证金率递增量
    private BigDecimal riskIncrMmr;
    // 初始保证金率递增量
    private BigDecimal riskIncrImr;
    // 风险限额档位数
    private Integer riskLevelLimit;
    //合理价格偏离指数价格系数
    private BigDecimal priceCoefficientVariation;

    private List<String> indexOrigin;//指数来源

    private int state;

    private Boolean isNew;
    private Boolean isHot;
    private Boolean isHidden;

}
