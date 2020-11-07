# OPEN-API接口模块

##基本信息:

url: https://contract.mxc.com 、  https://contract.mxc.io 、 https://contract.mxc.ai

## 通用响应数据结构:
```json
    {
      "success": true,
      "code": 0,
      "data":
       {
          "symbol":"BTC_USD",
          "fairPrice":8000,
          "timestamp":1111111111
         }
    }
```
or
```json
    {
      "success": false,
       "code":500,
      "message": "系统内部错误!"
    }
```
## 请求格式:

相应API接受GET，POST或DELETE类型的请求,post请求的Content-Type为:application/json.参数以json格式发送(参数命名规则为驼峰命名),get请求以requestParam形式发送(参数命名规则为'_'隔开)
鉴权方式:
    
    1.对于公共接口,不需要签名
    2.对于私有接口,需要在header中传入ApiKey、Request-Time、Signature、Recv-Window(可选)参数,Signature为签名字符串,签名规则如下:
        1)签名时需要先获得请求参数字符串,无参时为"";对于get请求,按字典序拼接业务参数以&间隔，
          并最终获得签名目标串（在批量操作的API中，若参数值中有逗号等特殊符号，这些符号在签名时需要做URL encode）,
          对于post请求,签名参数为json字符串;
        2)获得参数字符串后,再拼接签名目标串,规则为:accessKey+时间戳+获取到的参数字符串
        3)要求使用HMAC SHA256算法对目标串进行签名，并最终将签名作为参数携带到header中
        4)参与签名的业务参数为null时,不参与签名,对于path参数,也不参与签名;注意get请求将参数拼接至url上时,如果参数为null,
          后台解析时,会解析成"",固当get请求,某参数为null时,不要传该参数,或者签名时,将该参数的值设置为"",否则会出现验签失败!
        5)请求时将签名时用到的req_time的值放入header的Request-Time参数中,获得的签名字符串放入header的Signature参数中,将APIKEY的Access Key放在header的ApiKey参数中,其余业务参数按正常传递即可
时间安全:

    所有签名接口均需要传入header参数Request-Time，即以毫秒表示的时间戳字符串，服务端接收到请求后会验证请求发出的时间
    范围。若接受请求时，收到的req_time小于或大于服务端时间10秒（默认值）以上（该时间窗口值可以通过发送可选header参数
    Recv-Window来自定义，其最大值为60，不推荐使用30秒以上的recv_window），则认为该请求无效

java示例:

            /**
             * 获取get请求参数字符串
             *
             * @param param get/delete请求参数map
             * @return
             */
           public static String getRequestParamString(Map<String, String> param) {
               if (MapUtils.isEmpty(param)) {
                   return "";
               }
               StringBuilder sb = new StringBuilder(1024);
               SortedMap<String, String> map = new TreeMap<>(param);
               for (Map.Entry<String, String> entry : map.entrySet()) {
                   String key = entry.getKey();
                   String value = StringUtils.isBlank(entry.getValue()) ? "" : entry.getValue();
                   sb.append(key).append('=').append(urlEncode(value)).append('&');
               }
               sb.deleteCharAt(sb.length() - 1);
               return sb.toString();
           }
          public static String urlEncode(String s) {
              try {
                  return URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20");
              } catch (UnsupportedEncodingException e) {
                  throw new IllegalArgumentException("UTF-8 encoding not supported!");
              }
          } 
          
         /**签名*/
          public static String sign(SignVo signVo) {
                  if (signVo.getRequestParam() == null) {
                      signVo.setRequestParam("");
                  }
                  String str = signVo.getAccessKey() + signVo.getReqTime() + signVo.getRequestParam();
                  return actualSignature(str, signVo.getSecretKey());
              }
              
              
          public static String actualSignature(String inputStr, String key) {
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
              private String requestParam; //get请求参数根据字典顺序排序,使用&拼接字符串,post为json字符串
          }

## 创建API key

用户可以在MXC站点个人中心，创建API key，其包括两个部分，Access keyAPI的访问秘钥，Secret key对应的秘钥，用于签名计算及验证

***

##  合约行情接口(公共)
***
获取服务器时间:GET api/v1/contract/ping

获取合约信息:GET api/v1/contract/detail

获取合约支持划转的币种:GET api/v1/contract/support_currencies

获取合约深度信息:GET api/v1/contract/depth/{symbol}

获取合约深度信息:GET api/v1/contract/depth_commits/{symbol}/{limit}

获取合约指数价格:GET api/v1/contract/index_price/{symbol}

获取合约合理价格:GET api/v1/contract/fair_price/{symbol}

获取合约资金费率:GET api/v1/contract/funding_rate/{symbol}

获取合约K线数据: GET api/v1/contract/kline/{symbol}

获取指数价格K线数据: GET api/v1/contract/kline/index_price/{symbol}

获取合理价格K线数据: GET api/v1/contract/kline/fair_price/{symbol}

获取合约成交数据: GET api/v1/contract/deals/{symbol}

获取合约ticker数据: GET api/v1/contract/ticker

获取合约风险基金余额: GET api/v1/contract/risk_reverse/{symbol}

获取合约风险基金余额历史: GET api/v1/contract/risk_reverse/history

获取合约资金费率历史: GET api/v1/contract/funding_rate/history

***
+ ### 获取服务器时间
+ ##### 限速规则: 20次/2秒 
+ ##### method: 
     + **GET api/v1/contract/ping**
- ##### 请求参数: 无

   - ##### 响应参数 :
示例:
```json
    {
      "success": true,
      "data":111111111 
    }
```
***

+ ### 获取合约信息
+ ##### 限速规则: 1次/5秒 
+ ##### method: 
     + **GET api/v1/contract/detail**
- ##### 请求参数:
    
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
|  symbol | string  | false  | 合约名  |


   - ##### 响应参数 :
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| symbol  | string | 合约名 |
| displayName  | string | 展示名 |
| displayNameEn  | string | 英文展示名 |
| positionOpenType  | int  |  开仓类型,1：逐仓，2：全仓，3：全仓，逐仓都支持 |
| baseCoin  | string  | 标的货币 如 BTC |
| quoteCoin  | string  | 标价货币 如 USDT |
| settleCoin  | string  | 结算货币 如 USDT|
| contractSize  | decimal  | 合约面值 |
| minLeverage  | int  | 杠杆倍数下限 |
| maxLeverage  | int  | 杠杆倍数上限 |
| priceScale  | int  | 价格精度 |
| volScale  | int  | 数量精度 |
| amountScale  | int  | 金额精度 |
| priceUnit  | int  | 价格的最小步进单位 |
| volUnit  | int  | 数量的最小步进单位 |
| minVol  | decimal  | 订单张数下限|
| maxVol  | decimal  | 订单张数上限 |
| bidLimitPriceRate  | decimal  | 卖单价格限制比率 |
| askLimitPriceRate  | decimal  | 买单价格限制比率 |
| takerFeeRate  | decimal  | 买单费率 |
| makerFeeRate  | decimal  | 卖单费率 |
| maintenanceMarginRate  | decimal  | 维持保证金率 |
| initialMarginRate  | decimal  | 初始保证金率 |
| riskBaseVol  | decimal  | 基本张数 |
| riskIncrVol  | decimal  | 递增张数 |
| riskIncrMmr  | decimal  | 维持保证金率递增量 |
| riskIncrImr  | decimal  | 初始保证金率递增量 |
| riskLevelLimit  | int  | 风险限额档位数 |
| priceCoefficientVariation  | decimal  | 合理价格偏离指数价格系数 |
| indexOrigin  | List<String>  | 指数来源 |
| state  | int  | 状态,0:启用,1:交割,2:交割完成,3:下线,4: 暂停|

***
+ ### 获取可划转币种
+ ##### 限速规则: 20次/2秒 
+ ##### method: 
     + **GET api/v1/contract/support_currencies**
- ##### 请求参数: 无

   - ##### 响应参数 :
示例:
```json
    {
      "success": true,
      "data":["USDT","BTC","ETH"] 
    }
```
***
+ ### 获取合约深度信息
+ ##### 限速规则: 20次/2秒 
+ ##### method: 
     + **GET api/v1/contract/depth/{symbol}**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| symbol | string  | true  | 合约名  |

   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| asks  | List<Numeric[]> |卖方深度 |
| bids  | List<Numeric[]> | 买方深度 |
| version  | long  | 版本号 |

备注: [411.8, 10, 1] 411.8为价格，10为此价格的合约张数,1为订单数量
返回示例:
```json
{
    "asks":[
        [
            3968.5,
            121
        ],
        [
            3968.6,
            160,
            4
        ]
    ],
    "bids":[
        [
            3968.4,
            179,
            4
        ],
        [
            3968,
            914,
            3
        ]
    ],
    "version":1
}
```
***
+ ### 获取合约最近N条深度信息快照
+ ##### 限速规则: 20次/2秒 
+ ##### method: 
     + **GET api/v1/contract/depth_commits/{symbol}/{limit}**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| symbol | string  | true  | 合约名  |
| limit | int  | true  | 条数  |

   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| asks  | List<Numeric[]> |卖方深度 |
| bids  | List<Numeric[]> | 买方深度 |
| version  | long  | 版本号 |

***
+ ### 获取合约指数价格
+ ##### 限速规则: 20次/2秒 
+ ##### method: 
     + **GET api/v1/contract/index_price/{symbol}**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| symbol | string  | true  | 合约名  |

   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| symbol  | string | 交易对 |
| indexPrice  | decimal  | 指数价格 |
| timestamp  | long   | 系统时间戳 |

***

+ ### 获取合约合理价格
+ ##### 限速规则: 20次/2秒 
+ ##### method: 
     + **GET api/v1/contract/fair_price/{symbol}**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | true  | 合约名  |

   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| symbol | string| 合约名 |
| fairPrice  | decimal  | 合理价格 |
| timestamp  | long   | 系统时间戳 |

***
+ ### 获取合约资金费率
+ ##### 限速规则: 20次/2秒 
+ ##### method: 
     + **GET api/v1/contract/funding_rate/{symbol}**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | true  | 合约名  |

   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| symbol  | string | 合约名 |
| fundingRate  | decimal  | 资金费率 |
| maxFundingRate  | decimal  | 资金费率上限 |
| minFundingRate  | decimal  | 资金费率下限 |
| collectCycle  | int  | 收取周期 |
| nextSettleTime  | long  | 下次收取时间 |
| timestamp  | long   | 系统时间戳 |

***

+ ### 获取k线数据
+ ##### 限速规则: 20次/2秒 
+ ##### method: 
     + **GET api/v1/contract/kline/{symbol}**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | true  | 合约名  |
| interval   | string  | true  | 间隔: Min1、Min5、Min15、Min30、Min60、Hour4、Hour8、Day1、Week1、Month1  |
| start  | long  | true  | 开始时间戳  |
| end  | long  | true  | 结束时间戳  |

   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| open  | double[]  | 开盘价 |
| close  | double[]   | 收盘价 |
| high  | double[]   | 最高价 |
| low  | double[]   | 最低价 |
| vol  | double[]   | 成交量 |
| time  | long[]   | 时间窗口 |

***

+ ### 获取指数价格k线数据
+ ##### 限速规则: 20次/2秒 
+ ##### method: 
     + **GET api/v1/contract/kline/index_price/{symbol}**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | true  | 合约名  |
| interval   | string  | true  | 间隔: Min1、Min5、Min15、Min30、Min60、Hour4、Hour8、Day1、Week1、Month1  |
| start  | long  | true  | 开始时间戳  |
| end  | long  | true  | 结束时间戳  |

   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| open  | double[]  | 开盘价 |
| close  | double[]   | 收盘价 |
| high  | double[]   | 最高价 |
| low  | double[]   | 最低价 |
| vol  | double[]   | 成交量 |
| time  | long[]   | 时间窗口 |

***

+ ### 获取合理价格k线数据
+ ##### 限速规则: 20次/2秒 
+ ##### method: 
     + **GET api/v1/contract/kline/fair_price/{symbol}**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | true  | 合约名  |
| interval   | string  | true  | 间隔: Min1、Min5、Min15、Min30、Min60、Hour4、Hour8、Day1、Week1、Month1  |
| start  | long  | true  | 开始时间戳  |
| end  | long  | true  | 结束时间戳  |

   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| open  | double[]  | 开盘价 |
| close  | double[]   | 收盘价 |
| high  | double[]   | 最高价 |
| low  | double[]   | 最低价 |
| vol  | double[]   | 成交量 |
| time  | long[]   | 时间窗口 |

***

+ ### 获取成交数据
+ ##### 限速规则: 20次/2秒 
+ ##### method: 
     + **GET api/v1/contract/deals/{symbol}**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | true  | 合约名  |
| limit  | int  | false  | 结果集数量，最大为100，不填默认返回100条  |

   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| p  | decimal  | 成交价 |
| v  | decimal  | 数量 |
| T  | int  | 成交方向,1:买,2:卖 |
| O  | int   | 是否是开仓，1:是,2:否,当O为1的时候, vol是新增的持仓量 |
| M  | int   | 是否为自成交,1:是,2:否 |
| t  | long   | 成交时间|

***


+ ### 获取合约ticker数据
+ ##### 限速规则: 20次/2秒 
+ ##### method: 
     + **GET api/v1/contract/ticker**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | false  | 合约名 |


   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| symbol  | string | 合约名 |
| lastPrice  | decimal  | 最新价 |
| bid1  | decimal  | 买一价 |
| ask1  | decimal  | 卖一价 |
| volume24  | decimal  | 24小时成交量，按张数统计 |
| amount24  | decimal  | 24小时成交额|
| holdVol| decimal  | 总持仓量 |
| lower24Price  | decimal  | 24小时最低价 |
| high24Price  | decimal  | 24小时内最高价 |
| riseFallRate  | decimal  | 涨跌幅 |
| riseFallValue  | decimal  | 涨跌额 |
| indexPrice  | decimal  | 指数价格 |
| fairPrice  | decimal  | 合理价 |
| fundingRate  | decimal  | 资金费率 |
| timestamp  | long   | 成交时间 |

***
+ ### 获取合约风险基金余额
+ ##### 限速规则: 20次/2秒 
+ ##### method: 
     + **GET  api/v1/contract/risk_reverse/{symbol}**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | true  | 合约名 |


   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| symbol  | string | 合约名 |
| currency  | string  | 结算货币 |
| available  | decimal  | 余额 |
***

+ ### 获取合约风险基金余额历史
+ ##### 限速规则: 20次/2秒 
+ ##### method: 
     + **GET  api/v1/contract/risk_reverse/history**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | true  | 合约名 |
| page_num  | int  | true  | 当前页数,默认为1  |
| page_size  | int  | true  | 每页大小,默认20,最大100  |


   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| pageSize  | int  | 页面大小 |
| totalCount  | int  | 总条数 |
| totalPage  | int  | 总页数 |
| currentPage  | int  | 当前页 |
| resultList  | list  | 数据结果集 |
| symbol  | string | 合约名 |
| currency  | string  | 结算货币 |
| available  | decimal  | 余额 |
| snapshotTime  | long  | 快照时间 |
***

+ ### 获取合约资金费率历史
+ ##### 限速规则: 20次/2秒 
+ ##### method: 
     + **GET  api/v1/contract/funding_rate/history**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | true  | 合约名 |
| page_num  | int  | true  | 当前页数,默认为1  |
| page_size  | int  | true  | 每页大小,默认20,最大100  |


   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| pageSize  | int  | 页面大小 |
| totalCount  | int  | 总条数 |
| totalPage  | int  | 总页数 |
| currentPage  | int  | 当前页 |
| resultList  | list  | 数据结果集 |
| symbol  | string | 合约名 |
| fundingRate  | decimal  | 资金费率 |
| settleTime  | long  | 结算时间 |
***


## 私有接口
***
获取用户所有资产信息:GET api/v1/private/account/assets

获取用户资产信息:GET api/v1/private/account/asset/{currency}

获取用户资产划转记录:GET api/v1/private/account/transfer_record

获取用户历史持仓:GET api/v1/private/position/history_positions

获取用户当前持仓:GET api/v1/private/position/open_positions

获取资金费用明细:GET api/v1/private/position/funding_records

获取用户当前活跃订单:GET api/v1/private/order/open_orders/{symbol}

获取用户所有历史订单:GET api/v1/private/order/history_orders

通过外部订单号获取单个订单:GET api/v1/private/order/external/{symbol}/{external_oid}

通过订单号获取单个订单:GET api/v1/private/order/get/{order_id}

通过订单号批量获取订单:GET api/v1/private/order/batch_query

获取订单成交明细:GET api/v1/private/order/deal_details/{order_id}


获取风险限额: GET api/v1/private/account/risk_limit


修改持仓保证金:POST api/v1/private/position/change_margin

修改持仓杠杆倍数:POST api/v1/private/position/change_leverage

下单:POST api/v1/private/order/submit

批量下单:POST api/v1/private/order/submit_batch

取消订单:POST api/v1/private/order/cancel

取消所有订单:POST api/v1/private/order/cancel_all

修改风险等级: POST api/v1/private/account/change_risk_level


***

+ ### 获取用户所有资产信息
+ ##### 限速规则: 20次/2秒 
+ ##### 需要权限: 账户读取权限
+ ##### method: 
     + **GET api/v1/private/account/assets**
- ##### 请求参数: 无

   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| currency  | string  | 币种 |
| positionMargin  | decimal   | 仓位保证金 |
| frozenBalance  | decimal   | 冻结余额 |
| availableBalance  | decimal   | 当前可用余额 |
| cashBalance  | decimal   | 可提现余额 |
| equity  | decimal   | 总权益 |
| unrealized  | decimal   | 未实现盈亏 |

***

+ ### 获取用户单个币种资产信息
+ ##### 限速规则: 20次/2秒 
+ ##### 需要权限: 账户读取权限
+ ##### method: 
     + **GET api/v1/private/account/asset/{currency}**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| currency  | string  | true  | 币种  |

   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| currency  | string  | 币种 |
| positionMargin  | decimal   | 仓位保证金 |
| frozenBalance  | decimal   | 冻结余额 |
| availableBalance  | decimal   | 当前可用余额 |
| cashBalance  | decimal   | 可提现余额 |
| equity  | decimal   | 总权益 |
| unrealized  | decimal   | 未实现盈亏 |

***

+ ### 获取用户资产划转记录
+ ##### 限速规则: 20次/2秒 
+ ##### 需要权限: 账户读取权限
+ ##### method: 
     + **GET api/v1/private/account/transfer_record**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| currency  | string  | false  | 币种  |
| state  | string  | false  | 状态:WAIT 、SUCCESS 、FAILED  |
| type  | string  | false  | 类型:IN 、OUT  |
| page_num  | int  | true  | 当前页数,默认为1  |
| page_size  | int  | true  | 每页大小,默认20,最大100  |

   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| pageSize  | int  | 页面大小 |
| totalCount  | int  | 总条数 |
| totalPage  | int  | 总页数 |
| currentPage  | int  | 当前页 |
| resultList  | list  | 数据结果集 |
| id  | long  | id |
| txid  | string  | 流水号 |
| currency  | string  | 币种 |
| amount  | decimal  | 转账金额 |
| type  | string   | 类型:IN 、OUT |
| state  | string   | 状态:WAIT 、SUCCESS 、FAILED  |
| createTime  | long   | 创建时间 |
| updateTime  | long   | 修改时间 |

***

+ ### 获取用户当前持仓
+ ##### 限速规则: 20次/2秒 
+ ##### 需要权限: 交易读取权限
+ ##### method: 
     + **GET api/v1/private/position/open_positions**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | false  | 合约名|
   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| positionId  | long  | 持仓id |
| symbol  | string  | 合约名 |
| holdVol  | decimal  | 持仓数量 |
| positionType  | int  | 仓位类型， 1多 2空 |
| openType  | int   | 开仓类型， 1逐仓 2全仓 |
| state  | int   | 仓位状态,1持仓中2系统代持3已平仓  |
| frozenVol  | decimal   | 冻结量 |
| closeVol  | decimal   | 平仓量 |
| holdAvgPrice  | decimal   | 持仓均价 |
| closeAvgPrice  | decimal   | 平仓均价 |
| openAvgPrice  | decimal   | 开仓均价 |
| liquidatePrice  | decimal   | 逐仓时的爆仓价 |
| oim  | decimal   | 原始初始保证金 |
| adlLevel  | int   | adl减仓等级,取值为 1-5，为空时需等待刷新 |
| im  | decimal   | 初始保证金， 逐仓时可以加减此项以调节爆仓价 |
| holdFee  | decimal   | 资金费, 正数表示得到，负数表示支出 |
| realised  | decimal   | 已实现盈亏 |
| createTime  | date   | 创建时间 |
| updateTime  | date   | 修改时间 |
***

+ ### 获取用户历史持仓信息
+ ##### 限速规则: 20次/2秒 
+ ##### 需要权限: 交易读取权限
+ ##### method: 
     + **GET api/v1/private/position/history_positions**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | false  | 合约名|
| type  | int  | false  | 仓位类型， 1多 2空|
| page_num  | int  | true  | 当前页数,默认为1  |
| page_size  | int  | true  | 每页大小,默认20,最大100  |

   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| pageSize  | int  | 页面大小 |
| totalCount  | int  | 总条数 |
| totalPage  | int  | 总页数 |
| currentPage  | int  | 当前页 |
| resultList  | list  | 数据结果集 |
| positionId  | long  | 持仓id |
| symbol  | string  | 合约名 |
| holdVol  | decimal  | 持仓数量 |
| positionType  | int  | 仓位类型， 1多 2空 |
| openType  | int   | 开仓类型， 1逐仓 2全仓 |
| state  | int   | 仓位状态,1持仓中2系统代持3已平仓  |
| frozenVol  | decimal   | 冻结量 |
| closeVol  | decimal   | 平仓量 |
| holdAvgPrice  | decimal   | 持仓均价 |
| closeAvgPrice  | decimal   | 平仓均价 |
| openAvgPrice  | decimal   | 开仓均价 |
| liquidatePrice  | decimal   | 逐仓时的爆仓价 |
| oim  | decimal   | 原始初始保证金 |
| im  | decimal   | 初始保证金， 逐仓时可以加减此项以调节爆仓价 |
| holdFee  | decimal   | 资金费, 正数表示得到，负数表示支出 |
| realised  | decimal   | 已实现盈亏 |
| createTime  | date   | 创建时间 |
| updateTime  | date   | 修改时间 |

***
+ ### 获取用户资金费用明细
+ ##### 限速规则: 20次/2秒 
+ ##### 需要权限: 交易读取权限
+ ##### method: 
     + **GET api/v1/private/position/funding_records**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| symbol | string  | false  | 合约名|
| position_id  | int  | false  | 仓位id|
| page_num  | int  | true  | 当前页数,默认为1  |
| page_size  | int  | true  | 每页大小,默认20,最大100  |

   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| pageSize  | int  | 页面大小 |
| totalCount  | int  | 总条数 |
| totalPage  | int  | 总页数 |
| currentPage  | int  | 当前页 |
| resultList  | list  | 数据结果集 |
| id  | long  | id |
| symbol  | string  | 合约名 |
| positionId  | long  | 持仓id |
| positionType  | int  | 1:多仓,2:空仓 |
| positionValue  | decimal  | 仓位价值 |
| funding  | decimal   | 费用 |
| rate  | decimal   | 资金费率  |
| settleTime  | date   | 清算时间 |

***

+ ### 根据外部id查询订单
+ ##### 限速规则: 20次/2秒 
+ ##### 需要权限: 交易读取权限
+ ##### method: 
     + **GET api/v1/private/order/external/{symbol}/{external_oid}**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | true  | 合约名|
| external_oid  | string  | true  | 外部订单号|

   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| orderId  | long  | 页面大小 |
| symbol  | string  | 合约名 |
| positionId  | long  | 持仓id |
| price  | decimal  | 委托价格 |
| vol  | decimal  | 委托数量 |
| leverage  | long  | 杠杆倍数 |
| side  | int  | 订单方向 1开多,2平空,3开空,4平多 |
| category  | int  | 订单类别:1限价委托,2强平代管委托,3代管平仓委托,4ADL减仓 |
| orderType  | int  | 1:限价单,2:Post Only只做Maker,3:立即成交或立即取消,4:全部成交或者全部取消，5:市价单,6:市价转现价|
| dealAvgPrice  | decimal  | 成交均价 |
| dealVol  | decimal   | 成交数量 |
| orderMargin  | decimal   | 委托保证金  |
| takerFee  | decimal   | 买单手续费 |
| makerFee  | decimal   | 卖单手续费 |
| profit  | decimal   | 平仓盈亏 |
| feeCurrency  | string   | 收费币种 |
| openType  | int   | 开仓类型,1逐仓,2全仓 |
| state  | int   | 订单状态,1:待报,2未完成,3已完成,4已撤销,5无效 |
| externalOid  | string   | 外部订单号 |
| createTime  | date   | 创建时间 |
| updateTime  | date   | 修改时间 |

***


+ ### 根据订单id查询订单
+ ##### 限速规则: 20次/2秒 
+ ##### 需要权限: 交易读取权限
+ ##### method: 
     + **GET api/v1/private/order/get/{order_id}**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| order_id  | long  | true  | 订单号|

   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| orderId  | long  | 页面大小 |
| symbol  | string  | 合约名 |
| positionId  | long  | 持仓id |
| price  | decimal  | 委托价格 |
| vol  | decimal  | 委托数量 |
| leverage  | long  | 杠杆倍数 |
| side  | int  | 订单方向 1开多,2平空,3开空,4平多 |
| category  | int  | 订单类别:1限价委托,2强平代管委托,3代管平仓委托,4ADL减仓 |
| orderType  | int  | 1:限价单,2:Post Only只做Maker,3:立即成交或立即取消,4:全部成交或者全部取消，5:市价单,6:市价转现价|
| dealAvgPrice  | decimal  | 成交均价 |
| dealVol  | decimal   | 成交数量 |
| orderMargin  | decimal   | 委托保证金  |
| takerFee  | decimal   | 买单手续费 |
| makerFee  | decimal   | 卖单手续费 |
| profit  | decimal   | 平仓盈亏 |
| feeCurrency  | string   | 收费币种 |
| openType  | int   | 开仓类型,1逐仓,2全仓 |
| state  | int   | 订单状态,1:待报,2未完成,3已完成,4已撤销,5无效 |
| externalOid  | string   | 外部订单号 |
| createTime  | date   | 创建时间 |
| updateTime  | date   | 修改时间 |

***

+ ### 根据订单id批量查询订单
+ ##### 限速规则: 5次/2秒 
+ ##### 需要权限: 交易读取权限
+ ##### method: 
     + **GET /api/v1/private/order/batch_query**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| order_ids  | long[]  | true  | 订单号数组，可使用逗号隔开例如:order_ids = 1,2,3(最大50个订单):|

   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| orderId  | long  | 页面大小 |
| symbol  | string  | 合约名 |
| positionId  | long  | 持仓id |
| price  | decimal  | 委托价格 |
| vol  | decimal  | 委托数量 |
| leverage  | long  | 杠杆倍数 |
| side  | int  | 订单方向 1开多,2平空,3开空,4平多 |
| category  | int  | 订单类别:1限价委托,2强平代管委托,3代管平仓委托,4ADL减仓 |
| orderType  | int  | 1:限价单,2:Post Only只做Maker,3:立即成交或立即取消,4:全部成交或者全部取消，5:市价单,6:市价转现价|
| dealAvgPrice  | decimal  | 成交均价 |
| dealVol  | decimal   | 成交数量 |
| orderMargin  | decimal   | 委托保证金  |
| takerFee  | decimal   | 买单手续费 |
| makerFee  | decimal   | 卖单手续费 |
| profit  | decimal   | 平仓盈亏 |
| feeCurrency  | string   | 收费币种 |
| openType  | int   | 开仓类型,1逐仓,2全仓 |
| state  | int   | 订单状态,1:待报,2未完成,3已完成,4已撤销,5无效 |
| externalOid  | string   | 外部订单号 |
| createTime  | date   | 创建时间 |
| updateTime  | date   | 修改时间 |

***

+ ### 获取用户当前未结束订单
+ ##### 限速规则: 20次/2秒 
+ ##### 需要权限: 交易读取权限
+ ##### method: 
     + **GET api/v1/private/order/open_orders/{symbol}**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | false  | 合约名,不传返回所有|
| page_num  | int  | true  | 当前页数,默认为1  |
| page_size  | int  | true  | 每页大小,默认20,最大100  |

   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| pageSize  | int  | 页面大小 |
| totalCount  | int  | 总条数 |
| totalPage  | int  | 总页数 |
| currentPage  | int  | 当前页 |
| resultList  | list  | 数据结果集 |
| orderId  | long  | 页面大小 |
| symbol  | string  | 合约名 |
| positionId  | long  | 持仓id |
| price  | decimal  | 委托价格 |
| vol  | decimal  | 委托数量 |
| leverage  | long  | 杠杆倍数 |
| side  | int  | 订单方向 1开多,2平空,3开空,4平多 |
| category  | int  | 订单类别:1限价委托,2强平代管委托,4ADL减仓 |
| orderType  | int  | 1:限价单,2:Post Only只做Maker,3:立即成交或立即取消,4:全部成交或者全部取消，5:市价单,6:市价转现价|| dealAvgPrice  | decimal  | 成交均价 |
| dealVol  | decimal   | 成交数量 |
| orderMargin  | decimal   | 委托保证金  |
| usedMargin  | decimal   | 已经使用的保证金  |
| takerFee  | decimal   | 买单手续费 |
| makerFee  | decimal   | 卖单手续费 |
| profit  | decimal   | 平仓盈亏 |
| feeCurrency  | string   | 收费币种 |
| openType  | int   | 开仓类型,1逐仓,2全仓 |
| state  | int   | 订单状态,1:待报,2未完成,3已完成,4已撤销,5无效 |
| errorCode  | int   | 错误code,0:正常，1：参数错误，2：账户余额不足，3：仓位不存在，4：仓位可用持仓不足，5：多仓时， 委托价小于了强平价，空仓时， 委托价大于了强平价，6：开多时， 强平价大于了合理价，开空时， 强平价小于了合理价 ,7:超过风险限额限制，8：系统撤销|
| externalOid  | string   | 外部订单号 |
| createTime  | date   | 创建时间 |
| updateTime  | date   | 修改时间 |

***

+ ### 获取用户所有历史订单
+ ##### 限速规则: 20次/2秒 
+ ##### 需要权限: 交易读取权限
+ ##### method: 
     + **GET api/v1/private/order/history_orders**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | false  | 合约名|
| states  | string  | false  | 订单状态,1:待报,2未完成,3已完成,4已撤销,5无效;多个用 ',' 隔开|
| category  | int  | false  | 订单类别,1:限价委托,2:强平代管委托,4:ADL减仓|
| page_num  | int  | true  | 当前页数,默认为1  |
| page_size  | int  | true  | 每页大小,默认20,最大100  |

   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| pageSize  | int  | 页面大小 |
| totalCount  | int  | 总条数 |
| totalPage  | int  | 总页数 |
| currentPage  | int  | 当前页 |
| resultList  | list  | 数据结果集 |
| orderId  | long  | 页面大小 |
| symbol  | string  | 合约名 |
| positionId  | long  | 持仓id |
| price  | decimal  | 委托价格 |
| vol  | decimal  | 委托数量 |
| leverage  | long  | 杠杆倍数 |
| side  | int  | 订单方向 1开多,2平空,3开空,4平多 |
| category  | int  | 订单类别:1限价委托,2强平代管委托,4ADL减仓 |
| orderType  | int  | 1:限价单,2:Post Only只做Maker,3:立即成交或立即取消,4:全部成交或者全部取消，5:市价单,6:市价转现价|| dealAvgPrice  | decimal  | 成交均价 |
| dealVol  | decimal   | 成交数量 |
| orderMargin  | decimal   | 委托保证金  |
| usedMargin  | decimal   | 已经使用的保证金  |
| takerFee  | decimal   | 买单手续费 |
| makerFee  | decimal   | 卖单手续费 |
| profit  | decimal   | 平仓盈亏 |
| feeCurrency  | string   | 收费币种 |
| openType  | int   | 开仓类型,1逐仓,2全仓 |
| state  | int   | 订单状态,1:待报,2未完成,3已完成,4已撤销,5无效 |
| errorCode  | int   | 错误code,0:正常，1：参数错误，2：账户余额不足，3：仓位不存在，4：仓位可用持仓不足，5：多仓时， 委托价小于了强平价，空仓时， 委托价大于了强平价，6：开多时， 强平价大于了合理价，开空时， 强平价小于了合理价 |
| externalOid  | string   | 外部订单号 |
| createTime  | date   | 创建时间 |
| updateTime  | date   | 修改时间 |

***

+ ### 获取订单成交明细
+ ##### 限速规则: 20次/2秒 
+ ##### 需要权限: 交易读取权限
+ ##### method: 
     + **GET api/v1/private/order/deal_details/{order_id}**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| order_id  | long  | true  | 订单id|

   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| symbol  | string  | 合约名 |
| side  | int  | 订单方向 1开多,2平空,3开空,4平多 |
| vol  | decimal  | 成交数量|
| price  | decimal   | 成交价格 |
| fee  | decimal   | 手续费 |
| feeCurrency  | string   | 收费币种 |
| timestamp  | long   | 修改时间 |
***

+ ### 获取风险限额
+ ##### 限速规则: 20次/2秒 
+ ##### 需要权限: 交易读取权限
+ ##### method: 
     + **GET api/v1/private/account/risk_limit**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | false  | 合约名,不传返回所有|

   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| symbol  | string  | 合约名 |
| positionType  | int  | 持仓类型 1:多仓，2:空仓|
| level  | int   | 当前风险等级 |
| maxVol  | decimal   | 最大可持仓数量 |
| maxLeverage  | int   | 最大杠杆倍数 |
| mmr  | decimal   | 维持保证金率 |
| imr  | decimal   | 初始保证金率 |
***

+ ### 增加或减少仓位保证金
+ ##### 限速规则: 20次/2秒 
+ ##### 需要权限: 交易权限
+ ##### method: 
     + **POST api/v1/private/position/change_margin**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| positionId  | long  | true  | 仓位id|
| amount  | decimal  | true  | 金额|
| type  | string  | true  | 类型,ADD:增加,SUB:减少|

   - ##### 响应参数: 公共参数,success: true成功,false失败

***

+ ### 修改杠杆倍数
+ ##### 限速规则: 20次/2秒 
+ ##### 需要权限: 交易权限
+ ##### method: 
     + **POST api/v1/private/position/change_leverage**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| positionId  | long  | true  | 仓位id|
| leverage  | int  | true  | 杠杆倍数|

   - ##### 响应参数: 公共参数,success: true成功,false失败

***

+ ### 下单
+ ##### 限速规则: 20次/2秒 
+ ##### 需要权限: 交易权限
+ ##### method: 
     + **POST api/v1/private/order/submit**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | true  | 合约名|
| price  | decimal  | true  | 价格|
| vol  | decimal  | true  | 数量|
| leverage  | int  | false  | 杠杆倍数,开仓的时候杠杆倍数必须传入|
| side  | int  | true  | 订单方向 1开多,2平空,3开空,4平多|
| type  | int  | true  | 订单类型,1:限价单,2:Post Only只做Maker,3:立即成交或立即取消,4:全部成交或者全部取消,5:市价单|
| openType  | int  | true  | 开仓类型,1:逐仓,2:全仓|
| externalOid  | string  | false  | 外部订单号|

   - ##### 响应参数: 成功时,success =true,data值为订单id,success =false,失败data=null
***
+ ### 批量下单
+ ##### 限速规则: 1次/2秒 
+ ##### 需要权限: 交易权限
+ ##### method: 
     + **POST api/v1/private/order/submit_batch**
- ##### 请求参数(最大50条):
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | true  | 合约名|
| price  | decimal  | true  | 价格|
| vol  | decimal  | true  | 数量|
| leverage  | int  | false  | 杠杆倍数,开仓的时候杠杆倍数必须传入|
| side  | int  | true  | 订单方向 1开多,2平空,3开空,4平多|
| type  | int  | true  | 订单类型,1:限价单,2:Post Only只做Maker,3:立即成交或立即取消,4:全部成交或者全部取消,5:市价单|
| openType  | int  | true  | 开仓类型,1:逐仓,2:全仓|
| externalOid  | string  | false  | 外部订单号,如果已存在，返回已存在的订单id|


参数示例:
```json
    [{
      "symbol": "BTC_USD",
      "price": 8800,
      "vol": 100,
      "leverage": 20,
      "side": 1,
      "type": 1,
      "openType": 1,
      "externalOid": "order1"
    },
    {
       "symbol": "BTC_USD",
       "price": 500,
       "vol": 100,
        "leverage": 50,
        "side": 3,
        "type": 1,
        "openType": 1,
        "externalOid": "order2"
          }]
```
   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| externalOid  | string  | 外部订单号 |
| orderId  | long  | 订单id,失败时为null  |
| errorMsg  | string  | 错误信息，失败时不为空|
| errorCode  | int  | 错误code，默认为0|

***

	
+ ### 取消订单
+ ##### 限速规则: 20次/2秒 
+ ##### 需要权限: 交易权限
+ ##### method: 
     + **POST api/v1/private/order/cancel**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| 无  | List<Long>  | true  | 订单id列表,最大50条|

 参数示例:[1,2,3]

   - ##### 响应参数: - ##### 响应参数: 公共参数,success: true成功,false失败

***
+ ### 取消某合约下所有订单
+ ##### 限速规则: 20次/2秒 
+ ##### 需要权限: 交易权限
+ ##### method: 
     + **POST api/v1/private/order/cancel_all**
- ##### 请求参数:
	
| 参数名  | 类型  | 是否必填  |  说明 |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | false| 合约名,传入symbol只取消该合约下的订单，不传取消所有合约下的订单|

   - ##### 响应参数: - ##### 响应参数: 公共参数,success: true成功,false失败
   
 ***
 + ### 修改风险等级
 + ##### 限速规则: 20次/2秒 
 + ##### 需要权限: 交易权限
 + ##### method: 
      + **POST api/v1/private/account/change_risk_level**
 - ##### 请求参数:
 	
 | 参数名  | 类型  | 是否必填  |  说明 |
 | ------------ | ------------ | ------------ | ------------ |
 | symbol  | string  | true  | 合约名|
 | level  | int  | true  | 等级|
 | positionType  | int  | true  | 1:多仓，2:空仓|
 
    - ##### 响应参数: - ##### 响应参数: 公共参数,success: true成功,false失败
    
 ***

 - #### 错误码:
  
  | code  | 描述  |
  | ------------ | ------------ |
  | 0  | 操作成功  |
  | 9999  | 公共异常  |
  | 500  | 内部错误  |
  | 501  | 系统繁忙  |
  | 401  | 未授权  | 
  | 402  | api_key过期  |
  | 406  | 访问ip不在白名单  |
  | 506  | 未知的请求来源  |
  | 510  | 请求过于频繁  |
  | 511  | 接口禁止访问  |
  | 513  | 请求无效(针对open api 请求时间>服务器时间2秒或者<服务器时间10秒以上)  |
  | 600  | 参数错误  |
  | 601  | 数据解析错误  |
  | 602  | 验签失败  |
  | 603  | 重复请求  |
  | 1000  | 账户不存在  |
  | 1001  | 合约不存在  |
  | 1002  | 合约未启用  |
  | 1003  | 风险限额等级错误  |
  | 1004  | 金额错误  |
  | 2001  | 订单方向错误  |
  | 2002  | 开仓类型错误  |
  | 2003  | 买单价格过高  |
  | 2004  | 卖单价格过低  |
  | 2005  | 余额不足  |
  | 2006  | 杠杆倍数错误  |
  | 2007  | 订单价格错误  |
  | 2008  | 可平数量不足  |
  | 2009  | 仓位不存在或已平仓  |
  | 2011  | 订单数量错误  |
  | 2013  | 取消订单数量超过最大限制  |
  | 2014  | 批量下单数量超限  |
  | 2015  | 价格或者数量精度错误  |
  | 2016  | 计划委托超过最大委托数量  |
  | 2018  | 超过最大可减保证金  |
  | 2019  | 存在开仓活跃委托``  |
  | 2021  | 下单杠杆倍数和已有仓位杠杆倍数不一致  |
  | 2022  | 持仓类型错误  |
  | 2023  | 存在大于新档位最大杠杆倍数的持仓  |
  | 2024  | 存在大于新档位最大杠杆倍数的订单  |
  | 2025  | 当前持仓数量大于新档位最大允许数量  |
  | 2026  | 全仓不支持修改杠杆  |
  | 2027  | 同一方向全仓和逐仓只能存在一个  |
  | 3001  | 计划委托价格类型错误  |
  | 3002  | 计划委托触发类型错误  |
  | 3003  | 执行周期错误  |
  | 3004  | 触发价格错误  |
  | 4001  | 不支持的币种  |
***


# WEBSOCKET模块

## 基本信息:
	原生ws连接地址: wss://contract.mxc.ai/ws 、  wss://contract.mxc.com/ws 、 wss://contract.mxc.io/ws

## 数据交互命令详解:
    订阅/取消订阅数据命令列表（除个人相关命令列表之外，其余都不需要做ws认证）
    发送ping消息:
    {"method":"ping"}
    服务端返回:
    {"channel":"pong","data":1587453241453},1分钟以内未收到客户端ping，将断开该客户端连接，建议10~20秒发送一次ping
### 公共模块:
***
####tickers: 

+ ##### 订阅:
```json
 {"method":"sub.tickers","param":{}}
```
如需返回明文(后面订阅一样):
```json
 {"method":"sub.tickers","param":{},"gzip": false}
```
  - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| symbol  | string | 合约名 |
| lastPrice  | decimal  | 最新价 |
| volume24  | decimal  | 24小时成交量，按张数统计 |
| riseFallRate  | decimal  | 涨跌幅 |
| fairPrice  | decimal  | 合理价 |
示例:
```json
 {"channel":"push.tickers",
 "data":[
    {"fairPrice":183.01,"lastPrice":183,"riseFallRate":-0.0708,"symbol":"BSV_USDT","volume24":200},
    {"fairPrice":220.22,"lastPrice":220.4,"riseFallRate":-0.0686,"symbol":"BCH_USDT","volume24":200}
    ],
 "ts":1587442022003}
```
+ ##### 取消订阅:
```json
 {"method":"unsub.tickers","param":{}}
```
***
#### 单个ticker: 

+ ##### 订阅:
```json
{"method":"sub.ticker","param":{"symbol":"BTC_USDT"}}
```
  - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| symbol  | string | 合约名 |
| lastPrice  | decimal  | 最新价 |
| bid1  | decimal  | 买一价 |
| ask1  | decimal  | 卖一价 |
| volume24  | decimal  | 24小时成交量，按张数统计 |
| holdVol| decimal  | 总持仓量 |
| lower24Price  | decimal  | 24小时最低价 |
| high24Price  | decimal  | 24小时内最高价 |
| riseFallRate  | decimal  | 涨跌幅 |
| riseFallValue  | decimal  | 涨跌额 |
| indexPrice  | decimal  | 指数价格 |
| fairPrice  | decimal  | 合理价 |
| fundingRate  | decimal  | 资金费率 |
| timestamp  | long   | 系统时间戳 |
示例:
```json
{"channel":"push.ticker",
"data":{"ask1":6866.5,"bid1":6865,"contractId":1,"fairPrice":6867.4,
"fundingRate":0.0008,"high24Price":7223.5,"indexPrice":6861.6,
"lastPrice":6865.5,"lower24Price":6756,"maxBidPrice":7073.42,
"minAskPrice":6661.37,"riseFallRate":-0.0424,
"riseFallValue":-304.5,"symbol":"BTC_USDT",
"timestamp":1587442022003,"holdVol":2284742,"volume24":164586129},
"symbol":"BTC_USDT",
"ts":1587442022003
}
```
+ ##### 取消订阅:
```json
{"method":"unsub.ticker","param":{"symbol":"BTC_USDT"}}
```
***

#### 成交: 

+ ##### 订阅:
```json
{"method":"sub.deal","param":{"symbol":"BTC_USDT"}}
```
   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| p  | decimal  | 成交价 |
| v  | decimal  | 数量 |
| T  | int  | 成交方向,1:买,2:卖 |
| O  | int   | 是否是开仓，1:是,2:否,当O为1的时候, vol是新增的持仓量 |
| M  | int   | 是否为自成交,1:是,2:否 |
| t  | long   | 成交时间|
示例:
```json
{"channel":"push.deal",
"data":{"M":1,"O":1,"T":1,"p":6866.5,"t":1587442049632,"v":2096},
"symbol":"BTC_USDT",
"ts":1587442022003
}
```
+ ##### 取消订阅:
```json
{"method":"unsub.deal","param":{"symbol":"BTC_USDT"}}
```
***

#### 深度: 

+ ##### 订阅:
```json
{"method":"sub.depth","param":{"symbol":"BTC_USDT"}} //增量，全部
{"method":"sub.depth","param":{"symbol":"BTC_USDT","compress":true}}//增量，压缩后推送
{"method":"sub.depth.full","param":{"symbol":"BTC_USDT","limit":5}}//全量,limit可取值:5、10、20，不传默认为20档，只能订阅一个档位的全量
```
   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| asks  | List<Numeric[]> |卖方深度 |
| bids  | List<Numeric[]> | 买方深度 |
| version  | long  | 版本号 |
备注: [411.8, 10, 1] 411.8为价格，10为此价格的合约张数,1为订单数量

示例:
```json
{"channel":"push.depth",
"data":{"asks":[[6859.5,3251,1]],"bids":[],"version":96801927},
"symbol":"BTC_USDT",
"ts":1587442022003

}
```
+ ##### 取消订阅:
```json
{"method":"unsub.depth","param":{"symbol":"BTC_USDT"}}//增量的都以该事件取消订阅
{"method":"usub.depth.full","param":{"symbol":"BTC_USDT"}}//取消订阅全量，limit可传可不传
```
***

#### K线: 

+ ##### 订阅:
```json
{"method":"sub.kline","param":{"symbol":"BTC_USDT","interval":"Min60"}}
```
interval可选参数:  Min1、Min5、Min15、Min30、Min60、Hour4、Hour8、Day1、Week1、Month1 
   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| symbol  | string | 合约名 |
| a  | decimal  | 总成交金额 |
| c  | decimal  | 收盘价 |
| interval  | string  | 间隔: Min1、Min5、Min15、Min30、Min60、Hour4、Hour8、Day1、Week1、Month1 |
| l  | decimal  | 最低价 |
| o  | decimal  | 开盘价 |
| q  | decimal  | 总成交量 |
| h  | decimal  | 最高价 |
| t  | long  | 交易时间，单位：秒（s），为窗口的开始时间（windowStart） |

示例:
```json
{"channel":"push.kline",
"data":{"a":233.740269343644737245,"c":6885,"h":6910.5,
"interval":"Min60","l":6885,"o":6894.5,
"q":1611754,"symbol":"BTC_USDT","t":1587448800},
"symbol":"BTC_USDT",
"ts":1587442022003
}
```
+ ##### 取消订阅:
```json
{"method":"unsub.kline","param":{"symbol":"BTC_USDT"}}
```
***

#### 资金费率: 

+ ##### 订阅:
```json
{"method":"sub.funding.rate","param":{"symbol":"BTC_USDT"}}
```
   - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| symbol  | string | 合约名 |
| fundingRate  | decimal  | 资金费率 |
| nextSettleTime  | long  | 下一次结算时间 |

示例:
```json
{"channel":"push.funding.rate",
"data":{"rate":0.001,"symbol":"BTC_USDT"},
"symbol":"BTC_USDT",
"ts":1587442022003
}
```
+ ##### 取消订阅:
```json
{"method":"unsub.funding.rate","param":{"symbol":"BTC_USDT"}}
```
***

#### 指数价格: 

+ ##### 订阅:
```json
{"method":"sub.index.price","param":{"symbol":"BTC_USDT"}}
```
  - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| symbol  | string | 合约名 |
| price  | decimal  | 价格 |

示例:
```json
{"channel":"push.index.price",
"data":{"price":0.001,"symbol":"BTC_USDT"},
"symbol":"BTC_USDT",
"ts":1587442022003
}
```
+ ##### 取消订阅:
```json
{"method":"unsub.index.price","param":{"symbol":"BTC_USDT"}}
```
***

#### 合理价格: 

+ ##### 订阅:
```json
{"method":"sub.fair.price","param":{"symbol":"BTC_USDT"}}
```
  - ##### 响应参数:
	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| symbol  | string | 合约名 |
| price  | decimal  | 价格 |

示例:
```json
{"channel":"push.fair.price",
"data":{"price":0.001,"symbol":"BTC_USDT"},
"symbol":"BTC_USDT",
"ts":1587442022003
}
```
+ ##### 取消订阅:
```json
{"method":"unsub.fair.price","param":{"symbol":"BTC_USDT"}}
```
***

### 私有模块:

- #### 登录认证
```json
{
	"method":"login",
	"param":{
		"apiKey":"apiKey", // openapi需要传此参数，参数构造方式参照openapi文档
		"reqTime":"reqTime", // openapi需要传此参数，参数构造方式参照openapi文档
		"signature":"signature" // openapi需要传此参数，参数构造方式参照openapi文档
	}
}
```
#### 响应参数: 成功: 无，失败:返回对应错误信息,channel = rs.error

- ##### 登录成功(channel = rs.login)
示例:
```json
{"channel":"rs.login",
"data":"success",
"ts":"11111111111"
}
```


- ##### 订单(channel = push.personal.order)
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| orderId  | long  | 页面大小 |
| symbol  | string  | 合约名 |
| positionId  | long  | 持仓id |
| price  | decimal  | 委托价格 |
| vol  | decimal  | 委托数量 |
| leverage  | long  | 杠杆倍数 |
| side  | int  | 订单方向 1开多,2平空,3开空,4平多 |
| category  | int  | 订单类别:1限价委托,2强平代管委托,4ADL减仓 |
| orderType  | int  | 1:限价单,2:Post Only只做Maker,3:立即成交或立即取消,4:全部成交或者全部取消| |
| dealAvgPrice  | decimal  | 成交均价 |
| dealVol  | decimal   | 成交数量 |
| orderMargin  | decimal   | 委托保证金  |
| usedMargin  | decimal   | 已经使用的保证金  |
| takerFee  | decimal   | 买单手续费 |
| makerFee  | decimal   | 卖单手续费 |
| profit  | decimal   | 平仓盈亏 |
| feeCurrency  | string   | 收费币种 |
| openType  | int   | 开仓类型,1逐仓,2全仓 |
| state  | int   | 订单状态,1:待报,2未完成,3已完成,4已撤销,5无效 |
| errorCode  | int   | 错误code,0:正常，1：参数错误，2：账户余额不足，3：仓位不存在，4：仓位可用持仓不足，5：多仓时， 委托价小于了强平价，空仓时， 委托价大于了强平价，6：开多时， 强平价大于了合理价，开空时， 强平价小于了合理价 ,7:超过风险限额限制，8:系统撤销|
| externalOid  | string   | 外部订单号 |
| createTime  | date   | 创建时间 |
| updateTime  | date   | 修改时间 |
 
 - ##### 资产(channel = push.personal.asset)
 
 | 参数名  | 类型  | 说明  |
 | ------------ | ------------ | ------------ |
 | currency  | string  | 币种 |
 | positionMargin  | decimal   | 仓位保证金 |
 | frozenBalance  | decimal   | 冻结余额 |
 | availableBalance  | decimal   | 当前可用余额 |
 | cashBalance  | decimal   | 可提现余额 |
 
 - ##### 仓位(channel = push.personal.position)
 	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| positionId  | long  | 持仓id |
| symbol  | string  | 合约名 |
| holdVol  | decimal  | 持仓数量 |
| positionType  | int  | 仓位类型， 1多 2空 |
| openType  | int   | 开仓类型， 1逐仓 2全仓 |
| state  | int   | 仓位状态,1持仓中2系统代持3已平仓  |
| frozenVol  | decimal   | 冻结量 |
| closeVol  | decimal   | 平仓量 |
| holdAvgPrice  | decimal   | 持仓均价 |
| closeAvgPrice  | decimal   | 平仓均价 |
| openAvgPrice  | decimal   | 开仓均价 |
| liquidatePrice  | decimal   | 逐仓时的爆仓价 |
| oim  | decimal   | 原始初始保证金 |
| adlLevel  | int   | adl减仓等级,取值为 1-5，为空时需等待刷新 |
| im  | decimal   | 初始保证金， 逐仓时可以加减此项以调节爆仓价 |
| holdFee  | decimal   | 资金费, 正数表示得到，负数表示支出 |
| realised  | decimal   | 已实现盈亏 |
| createTime  | date   | 创建时间 |
| updateTime  | date   | 修改时间 |


 - ##### 风险限额(channel = push.personal.risk.limit)
 	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| symbol  | string  | 合约名 |
| positionType  | int  | 持仓类型 1:多仓，2:空仓|
| level  | int   | 当前风险等级 |
| maxVol  | decimal   | 最大可持仓数量 |
| maxLeverage  | int   | 最大杠杆倍数 |
| mmr  | decimal   | 维持保证金率 |
| imr  | decimal   | 初始保证金率 |

 - ##### adl自动减仓等级(channel = push.personal.adl.level)
 	
| 参数名  | 类型  | 说明  |
| ------------ | ------------ | ------------ |
| adlLevel| int   | 当前adl等级 ：1-5|
| positionId  | long   | 仓位id |

 - ##### 如何维护深度信息
  
#### 如何维护增量深度信息:
        1.通过接口 https://contract.mxc.com/api/v1/contract/depth/BTC_USDT获取全量深度信息，保存当前version
        2.订阅ws深度信息，收到更新后，如果收到的数据version>当前version,同一个价位，后收到的更新覆盖前面的。
        3.通过接口 https://contract.mxc.com/api/v1/contract/depth_commits/BTC_USDT/1000获取最新1000条深度快照
        4.将目前缓存的深度信息中同一价格，version<步骤3获取到的快照中的version的数据丢弃
        5.将深度快照中的内容更新至本地缓存，并从ws接收到的event开始继续更新
        5.每一个新event的version应该恰好等于上一个event的version+1，否则可能出现了丢包，如出现丢包或者获取到的
        event的version不连续,请从步骤3重新进行初始化。
        6.每一个event中的挂单量代表这个价格目前的挂单量绝对值，而不是相对变化。
        7.如果某个价格对应的挂单量为0，表示该价位的挂单已经撤单或者被吃，应该移除这个价位。

#### 订阅类事件，订阅成功响应:     
   
      channel 为 rs. + 订阅的method，
      data为 "success"
例如订阅成交信息：
```json
{"method":"sub.deal","param":{"symbol":"BTC_USDT"}}
```
订阅成功响应:
```json
{"channel":"rs.sub.deal",
"data":"success",
"ts":"11111111111"
}
```

订阅失败响应:
```json
{"channel":"rs.error",
"data":"合约不存在!",
"ts":"11111111111"
}
```


