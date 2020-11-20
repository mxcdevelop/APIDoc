# OPEN-API Interface Module

##The basic information:

url: https://contract.mxc.com 、  https://contract.mxc.io 、 https://contract.mxc.ai

## Common Response Data Structure:
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
      "message": "System internal  error!"
    }
```
## Request Format:

The corresponding API accepts a request of Type GET, POST, or DELETE, the content-type of POST request: Application/JSON. Parameters are sent in JSON format (camel named should be required), and get requests are sent in requestParam format ( '_' delimited should be required)

Authentication  way:
    
    1.For public interfaces, signature is unnecessary
    2.For the private interface, parameters such as ApiKey, request-time,Signature and recv-Window (optional) need to be entered in the header. Signature is a string with the following rules:
        1)The request parameter string should be obtained first when signing,"" for cases there is no parameter;"&" for separating when face the concatenate request parameters in dictionary for get request . Finally get the signature target string (in the API of batch operation, if there are special symbols such as commas in the parameter value, these symbols need to do URL encode )For post requests, the signature parameter is a JSON string;
        2)Splice the  signature target string after get the parameter string . The rule follows :accessKey+ timestamp + obtained parameter string
        3)It is required to use HMAC SHA256 algorithm to sign the target string and carry the signature into the header as a parameter.
        4)If the request parameter participating in the signature is NULL, it does not participate in the signature, nor does it participate in the signature for the path parameter; the validation will be failed if there is "" as a parameter in the signature. , the null parameters will be encoding as " " when get the request.
        5) Put the  REq_time in the Signature into the request-time parameter of the header, put the Signature string obtained into the Signature parameter of the header, put the Access Key of the APIKEY into the APIKEY parameter of the header, the other request parameters will be passed normally.
        
Time security:

    The header parameter Request-Time required for all signature interfaces, which is a timestamp string in milliseconds, and the server will verify the Time range issued by the Request after receiving the Request.The request is considered invalid if the received REq_time is less than or more than the server time of 10 seconds (the default value) ,(the time window value can be defined by sending the optional header parameter recv-window with a maximum value of 60, and recv_window of 30 seconds or more is not recommended)

java example:

            /**
             * Get the get request parameter string
             *
             * @param param get/delete request parameters map
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
          
         /**Signature*/
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
              private String requestParam; //The request parameters are sorted in dictionary order, "&" should be used to concatenate strings, POST should be JSON string
          }

## Create API key

Users can build API key in the personal center of MXC site, which consists of two parts, the AccessKey of Access keyAPI  and AccessKey corresponding to secret key, used for signature calculation and verification.

***

##  Contract Interface (Public)
***
Get server time:GET api/v1/contract/ping

Get contract information:GET api/v1/contract/detail

Get the cryptocurrency in which the contract supports transfers:GET api/v1/contract/support_currencies

Get contract depth information:GET api/v1/contract/depth/{symbol}

Get contract  depth information:GET api/v1/contract/depth_commits/{symbol}/{limit}

Get contract  index price:GET api/v1/contract/index_price/{symbol}

Get contract fair price:GET api/v1/contract/fair_price/{symbol}

Get contract funding rate:GET api/v1/contract/funding_rate/{symbol}

Get K line data: GET api/v1/contract/kline/{symbol}

Get K lines data of the index price: GET api/v1/contract/kline/index_price/{symbol}

Get K line data of fair price: GET api/v1/contract/kline/fair_price/{symbol}

Get contract transaction data: GET api/v1/contract/deals/{symbol}

Get contract Ticker data: GET api/v1/contract/ticker

Get balance of contract risk fund: GET api/v1/contract/risk_reverse/{symbol}

Get contract risk fund balance history: GET api/v1/contract/risk_reverse/history

Get contract funding rate history: GET api/v1/contract/funding_rate/history

***
+ ### Get server time
+ ##### Rate limit: 20 times / 2 seconds 
+ ##### method: 
     + **GET api/v1/contract/ping**
- ##### Request parameter:None

   - ##### Response parameters :
example:
```json
    {
      "success": true,
      "data":111111111 
    }
```
***

+ ### Get contract information
+ ##### Rate limit:1/5 seconds
+ ##### method: 
     + **GET api/v1/contract/detail**
- ##### Request parameters:
    
| Parameter  | Data Type  | Required |  Description |
| ------------ | ------------ | ------------ | ------------ |
|  symbol | string  | false  | the name of the contract |


   - ##### Response parameters :
	
| Parameter  | Data Type   | Description  |
| ------------ | ------------ | ------------ |
| symbol  | string | the name of the contract |
| displayName  | string | display name |
| displayNameEn  | string | English display name |
| positionOpenType  | int  |  position open type,1：isolated，2：cross，3：both |
| baseCoin  | string  | base currency such as BTC |
| quoteCoin  | string  | quote currency such as USDT |
| settleCoin  | string  | liquidation currency such as USDT|
| contractSize  | decimal  | Contract value |
| minLeverage  | int  |  minimum leverage|
| maxLeverage  | int  | Maximum leverage|
| priceScale  | int  | price scale |
| volScale  | int  | quantity scale |
| amountScale  | int  | amount scale |
| priceUnit  | int  | price unit |
| volUnit  | int  | volume unit |
| minVol  | decimal  | minimum volume
| maxVol  | decimal  | maximum volume |
| bidLimitPriceRate  | decimal  | bid limitprice ratio |
| askLimitPriceRate  | decimal  | ask limit price ratio |
| takerFeeRate  | decimal  | taker rate |
| makerFeeRate  | decimal  | maker rate|
| maintenanceMarginRate  | decimal  | maintenance margin rate |
| initialMarginRate  | decimal  | initial margin rate |
| riskBaseVol  | decimal  | initial volume |
| riskIncrVol  | decimal  | risk incresing volume |
| riskIncrMmr  | decimal  | maintain increasing margin rate |
| riskIncrImr  | decimal  | initial increasing margin rate |
| riskLevelLimit  | int  |  risk level limit |
| priceCoefficientVariation  | decimal  | price coefficient variation |
| indexOrigin  | List<String>  | index origin |
| state  | int  | Status,0:enabled,1:delivery,2:completion,3:offline,4: pause|

***
+ ###  Get the cryptocurrency in which the contract supports transfers
+ ##### Rate limit:20 times/2 seconds
+ ##### method: 
     + **GET api/v1/contract/support_currencies**
- ##### Request parameter:None

   - ##### Response parameters  :
Example:
```json
    {
      "success": true,
      "data":["USDT","BTC","ETH"] 
    }
```
***
+ ### Get contract  depth information
+ ##### Rate limit:20 times/2 seconds
+ ##### method: 
     + **GET api/v1/contract/depth/{symbol}**
- ##### Request parameter:
	
| Parameter  | Data Type  | Required  |  Description |
| ------------ | ------------ | ------------ | ------------ |
| symbol | string  | true  | the name of the contract |

   - ##### Response parameters :
	
| Parameter | Data Type  | Description  |
| ------------ | ------------ | ------------ |
| asks  | List<Numeric[]> | the seller depth |
| bids  | List<Numeric[]> | the buyer depth |
| version  | long  | the version number |

note: [411.8, 10, 1] 411.8 is the price，10 is the volume of contracts for this price,1 is the order quantity
Example:
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
+ ### Gets N snapshot of the most recent depth information of the contract
+ ##### Rate limit:20 times/2 seconds 
+ ##### method: 
     + **GET api/v1/contract/depth_commits/{symbol}/{limit}**
- ##### Request parameters:
	
| Parameter  | Data Type  | Required  |  Description |
| ------------ | ------------ | ------------ | ------------ |
| symbol | string  | true  | the name of the contract  |
| limit | int  | true  | count |

   - ##### Response parameters:
	
| Parameter  | Data Type  | Description  |
| ------------ | ------------ | ------------ |
| asks  | List<Numeric[]> | the seller depth |
| bids  | List<Numeric[]> | the buyer depth |
| version  | long  | the version number |

***
+ ### Get contract  index price
+ ##### Rate limit:20 times/2 seconds 
+ ##### method: 
     + **GET api/v1/contract/index_price/{symbol}**
- ##### Request parameters:
	
| Parameter  | Data Type   | Required  |  Description |
| ------------ | ------------ | ------------ | ------------ |
| symbol | string  | true  | the name of the contract  |

   - ##### Response parameters:
	
| Parameter  | Data Type  | Description  |
| ------------ | ------------ | ------------ |
| symbol  | string | trading pair |
| indexPrice  | decimal  | index price |
| timestamp  | long   | system timestamp |

***

+ ### Get contract fair price
+ ##### Rate limit:20 times/2 seconds 
+ ##### method: 
     + **GET api/v1/contract/fair_price/{symbol}**
- ##### Request parameters:
	
| Parameter  | Data Tpye  | Required |  Description |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | true  | the name of the contract  |

   - ##### Response parameters:
	
| Parameter | Data Type   | Description  |
| ------------ | ------------ | ------------ |
| symbol | string | the name of the contract |
| fairPrice  | decimal  | fair price|
| timestamp  | long   | system timestamp|

***
+ ### Get contract capital rate
+ ##### Rate limit:20 times/2 seconds
+ ##### method: 
     + **GET api/v1/contract/funding_rate/{symbol}**
- ##### Request parameters:
	
| Parameter  | Data Type   | Required  |  Description |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | true  | the name of the contract |

   - ##### Response parameters:
	
| Parameter  | Data Type   | Description  |
| ------------ | ------------ | ------------ |
| symbol  | string | the name of the contract |
| fundingRate  | decimal  | funding rate |
| maxFundingRate  | decimal  | max fundingl rate |
| minFundingRate  | decimal  | min funding rate |
| collectCycle  | int  | charge cycle |
| nextSettleTime  | long  | next liquidate time |
| timestamp  | long   | system timestamp |

***

+ ### Get K line data
+ ##### Rate limit:20 times/2 seconds 
+ ##### method: 
     + **GET api/v1/contract/kline/{symbol}**
- ##### Request parameters:
	
| Parameter  | Data Type   | Required  |  Description |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | true  | the name of the contract  |
| interval   | string  | true  | interval: Min1、Min5、Min15、Min30、Min60、Hour4、Hour8、Day1、Week1、Month1  |
| start  | long  | true  | start timestamp  |
| end  | long  | true  | end timestamp |

   - ##### Response parameters:
	
| Parameter  | Data Type   | Description  |
| ------------ | ------------ | ------------ |
| open  | double[]  | the opening price |
| close  | double[]   | the closing price|
| high  | double[]   | the highest price |
| low  | double[]   | the lowest price |
| vol  | double[]   | volume |
| time  | long[]   | time window |

***

+ ### Get K lines data of the index price
+ ##### Rate limit:20 times/2 seconds 
+ ##### method: 
     + **GET api/v1/contract/kline/index_price/{symbol}**
- ##### Request parameters:
	
| Parameter | Data Type   | Required  |  Description |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | true  | The name of the contract  |
| interval   | string  | true  | interval: Min1、Min5、Min15、Min30、Min60、Hour4、Hour8、Day1、Week1、Month1  |
| start  | long  | true  | start timestamp  |
| end  | long  | true  | end timestamp |

   - ##### Response parameters:
	
| Parameter | Data Type   | Description  |
| ------------ | ------------ | ------------ |
| open  | double[]  | the opening price |
| close  | double[]   | the closing price |
| high  | double[]   | the highest price |
| low  | double[]   | the lowest price|
| vol  | double[]   | volume |
| time  | long[]   | time window |

***

+ ### Get K line data of fair price
+ ##### Rate limit:20 times/2 seconds 
+ ##### method: 
     + **GET api/v1/contract/kline/fair_price/{symbol}**
- ##### Request parameters:
	
| Parameter  | Data Type   | Required  |  Description |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | true  | the name of contract  |
| interval   | string  | true  | interval: Min1、Min5、Min15、Min30、Min60、Hour4、Hour8、Day1、Week1、Month1  |
| start  | long  | true  | start timestamp  |
| end  | long  | true  | end timestamp  |

   - ##### Response parameters:
	
| Parameter  | Data Type   | Description  |
| ------------ | ------------ | ------------ |
| open  | double[]  | the opening price |
| close  | double[]   | the closing price |
| high  | double[]   | the highest price |
| low  | double[]   | the lowest price |
| vol  | double[]   | volume |
| time  | long[]   | time window |

***

+ ### Get contract transaction data
+ ##### Rate limit:20 times/2 seconds 
+ ##### method: 
     + **GET api/v1/contract/deals/{symbol}**
- ##### Request parameters:
	
| Parameter  | Data Type   | Required  |  Description |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | true  | the name of the contract  |
| limit  | int  | false  | consequence set quantity ，maximum is 100, return 100 without default  |

   - ##### Response parameters:
	
| Parameter  | Data Type   | Description  |
| ------------ | ------------ | ------------ |
| p  | decimal  | deal price |
| v  | decimal  | quantity |
| T  | int  | deal type,1:purchase,2:sell |
| O  | int   | Open position, 1: Yes,2: No, when O is 1, vol is the newly added position |
| M  | int   |self-closing,1:yes,2:no |
| t  | long   | deal time|

***


+ ### Get contract Ticker data
+ ##### Rate limit:20 times/2 seconds 
+ ##### method: 
     + **GET api/v1/contract/ticker**
- ##### Request parameters:
	
| Parameter  | Data Type   | Required |  Description |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | false  | the name of the contract |


   - ##### Response parameters:
	
| Parameter  | Data Type  | Description |
| ------------ | ------------ | ------------ |
| symbol  | string | The name of the contract |
| lastPrice  | decimal  | the latest price |
| bid1  | decimal  | purchase一price |
| ask1  | decimal  | sell一price |
| volume24  | decimal  | 24 hours trading volume, according to the volume of statistics |
| amount24  | decimal  | 24 hours  transaction volume  |
| holdVol| decimal  | total holdings |
| lower24Price  | decimal  | lowest price within 24 hours |
| high24Price  | decimal  | highest price within 24 hours |
| riseFallRate  | decimal  | rise fall rate |
| riseFallValue  | decimal  | rise fall value |
| indexPrice  | decimal  | index price |
| fairPrice  | decimal  | fair price |
| fundingRate  | decimal  | funding rate |
| timestamp  | long   | deal time |

***
+ ### Get balance of contract risk fund
+ ##### Rate limit:20 times/2 seconds 
+ ##### method: 
     + **GET  api/v1/contract/risk_reverse/{symbol}**
- ##### Request parameters:
	
| Parameter  | Data Type  | Required |  Description |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | true  | the name of the contract |


   - ##### Response parameters:
	
| Parameter  | Data Type   | Description  |
| ------------ | ------------ | ------------ |
| symbol  | string | the name of the contract |
| currency  | string  | liquidation currency |
| available  | decimal  | balance |
***

+ ### Get contract risk fund balance history
+ ##### Rate limit:20 times/2 seconds
+ ##### method: 
     + **GET  api/v1/contract/risk_reverse/history**
- ##### Request parameters:
	
| Parameter  | Data Type   | Required  |  Description |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | true  | the name of the contract |
| page_num  | int  | true  | current page number, default is 1 |
| page_size  | int  | true  | the page size, default 20, maximum 100  |


   - ##### Response parameters:
	
| Parameter  | Data Type   | Description  |
| ------------ | ------------ | ------------ |
| pageSize  | int  |  page size |
| totalCount  | int  |  total count  |
| totalPage  | int  | total pages  |
| currentPage  | int  |  current page |
| resultList  | list  | data consequence set |
| symbol  | string | the name of the contract |
| currency  | string  | liquidation currency |
| available  | decimal  | balance |
| snapshotTime  | long  |  snapshot  time |
***

+ ### Get contract funding rate history
+ ##### Rate limit:20 times/2 seconds 
+ ##### method: 
     + **GET  api/v1/contract/funding_rate/history**
- ##### Request parameters:
	
| Parameter  | Data Type   | Required  |  Description |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | true  | The name of the contract |
| page_num  | int  | true  | current number of pages, default is 1  |
| page_size  | int  | true  | the page size, default 20, maximum 100  |


   - ##### Response parameters:
	
| Parameter  | Data Type   | Description  |
| ------------ | ------------ | ------------ |
| pageSize  | int  | the page size |
| totalCount  | int  | the total number |
| totalPage  | int  | the total pages |
| currentPage  | int  | the current page |
| resultList  | list  | data consequence set |
| symbol  | string | The name of the contract |
| fundingRate  | decimal  | funding rate |
| settleTime  | long  | liquidation time |
***


## Private Interface
***
Get all of the user's asset information:GET api/v1/private/account/assets

Get the user'sasset information:GET api/v1/private/account/asset/{currency}

Get the user's asset transfer record:GET api/v1/private/account/transfer_record

Get the user's history positions:GET api/v1/private/position/history_positions

Gets the user's current position:GET api/v1/private/position/open_positions

Get details of funding records:GET api/v1/private/position/funding_records

Get the user's current active order:GET api/v1/private/order/open_orders/{symbol}

Get all of the user's historical orders:GET api/v1/private/order/history_orders

Get order with an external order number:GET api/v1/private/order/external/{symbol}/{external_oid}

Get order by order number:GET api/v1/private/order/get/{order_id}

Get orders in bulk by order number:GET api/v1/private/order/batch_query

Get order transaction details:GET api/v1/private/order/deal_details/{order_id}


Get risk limits: GET api/v1/private/account/risk_limit


Modifiy margin on position:POST api/v1/private/position/change_margin

Modify position leverage :POST api/v1/private/position/change_leverage

Order:POST api/v1/private/order/submit

Bulk order:POST api/v1/private/order/submit_batch

Cancel the order:POST api/v1/private/order/cancel

Cancel all orders:POST api/v1/private/order/cancel_all

Modify the risk level: POST api/v1/private/account/change_risk_level


***

+ ### Get all of the user's asset information
+ ##### Rate limit:20 times/2 seconds 
+ ##### Required permissions: Account read permissions
+ ##### method: 
     + **GET api/v1/private/account/assets**
- ##### Request parameters: None

   - #####  Response parameters:
	
| Parameter | Data Type   | Description |
| ------------ | ------------ | ------------ |
| currency  | string  | currency |
| positionMargin  | decimal   | position margin |
| frozenBalance  | decimal   | frozen balance |
| availableBalance  | decimal   | available balance |
| cashBalance  | decimal   | drawable balance |
| equity  | decimal   | total equity |
| unrealized  | decimal   | unrealized profit and loss |

***

+ ### Get the user's single currency asset information
+ ##### Rate limit:20 times/2 seconds 
+ ##### Required permissions: Account read permissions
+ ##### method: 
     + **GET api/v1/private/account/asset/{currency}**
- ##### Request parameters:
	
| Parameter | Data Type   | Required |  Description |
| ------------ | ------------ | ------------ | ------------ |
| currency  | string  | true  | currency|

   - ##### Response parameters:
	
| Parameter | Data Type   | Description  |
| ------------ | ------------ | ------------ |
| currency  | string  | currency |
| positionMargin  | decimal   | position margin |
| frozenBalance  | decimal   | frozen balance |
| availableBalance  | decimal   | available balance |
| cashBalance  | decimal   | drawable balance |
| equity  | decimal   | total equity |
| unrealized  | decimal   | unrealized profit and loss |

***

+ ### Get the user's asset transfer records
+ ##### Rate limit:20 times/2 seconds 
+ ##### Required permissions: Account read permissions
+ ##### method: 
     + **GET api/v1/private/account/transfer_record**
- ##### Request parameters:
	
| Parameter | Data Type   | Required  |  Description |
| ------------ | ------------ | ------------ | ------------ |
| currency  | string  | false  | Description  |
| state  | string  | false  | state:WAIT 、SUCCESS 、FAILED  |
| type  | string  | false  | type:IN 、OUT  |
| page_num  | int  | true  | current page, default is 1 |
| page_size  | int  | true  |  page size, default 20, maximum 100  |

   - ##### Response parameters:
	
| Parameter | Data Type   | Description  |
| ------------ | ------------ | ------------ |
| pageSize  | int  | page size |
| totalCount  | int  | total count |
| totalPage  | int  | total page |
| currentPage  | int  | current page |
| resultList  | list  | data consequence set |
| id  | long  | id |
| txid  | string  | cash number |
| currency  | string  | currency|
| amount  | decimal  | transfer amount |
| type  | string   | type:IN 、OUT |
| state  | string   | state:WAIT 、SUCCESS 、FAILED  |
| createTime  | long   | create time |
| updateTime  | long   | update time |

***

+ ### Get the user's current position
+ ##### Rate limit:20 times/2 seconds
+ ##### Required permissions: Trade read permissions
+ ##### method: 
     + **GET api/v1/private/position/open_positions**
- ##### Request parameters:
	
| Parameter  | Data Type   | Required  |  Description |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | false  | The name of the contract|
   - ##### Response parameters:
	
| Parameter  | Required  | Description  |
| ------------ | ------------ | ------------ |
| positionId  | long  | position id |
| symbol  | string  | The name of the contract |
| holdVol  | decimal  | hold volume |
| positionType  | int  | position type， 1 long  2 short  |
| openType  | int   | open type， 1 isolated 2 cross |
| state  | int   | position state,1holding. 2 system holding 3 closed  |
| frozenVol  | decimal   | frozen volume |
| closeVol  | decimal   | close volume |
| holdAvgPrice  | decimal   | hold average price |
| closeAvgPrice  | decimal   | close average price |
| openAvgPrice  | decimal   | open average price |
| liquidatePrice  | decimal   | liquidate price |
| oim  | decimal   | original initial margin |
| adlLevel  | int   | adl the value of store-reduction level is 1-5. If it is empty, wait for the refresh |
| im  | decimal   | initial margin， add or subtract  items can be used to adjust the liquidate price |
| holdFee  | decimal   | hold fee,  positive  means  get it,  negative  means lost it |
| realised  | decimal   | profit and loss achieved |
| createTime  | date   | create time |
| updateTime  | date   | update time |
***

+ ### Get user history position information
+ ##### Rate limit:20 times/2 seconds 
+ ##### Required permissions: Trade read permissions
+ ##### method: 
     + **GET api/v1/private/position/history_positions**
- ##### Request parameters:
	
| Parameter | Data Type  | Required  |  Description |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | false  | The name of the contract |
| type  | int  | false  | position type， 1long 2short|
| page_num  | int  | true  | current number of pages, default is 1  |
| page_size  | int  | true  | page size , default 20, maximum 100  |

   - ##### Response parameters:
	
| Parameter  | Data Type   | Description  |
| ------------ | ------------ | ------------ |
| pageSize  | int  | page size |
| totalCount  | int  | total count |
| totalPage  | int  | total pages |
| currentPage  | int  | current page |
| resultList  | list  | data consequence set |
| positionId  | long  | position id |
| symbol  | string  | The name of the contract |
| holdVol  | decimal  | hold volume |
| positionType  | int  | position type， 1 long  2 short |
| openType  | int   | open type， 1isolated 2cross |
| state  | int   | position state,1holding 2 system holding 3closed  |
| frozenVol  | decimal   | frozen volume |
| closeVol  | decimal   | close volume |
| holdAvgPrice  | decimal   | hold average price |
| closeAvgPrice  | decimal   | close average price |
| openAvgPrice  | decimal   | open average price |
| liquidatePrice  | decimal   | liquidate price |
| oim  | decimal   | original initial margin |
| im  | decimal   | initial margin， add or subtract  items can be used to adjust the liquidate price |
| holdFee  | decimal   | hold fee,  positive  means  get it,  negative  means lost it |
| realised  | decimal   |  profit and loss achieved |
| createTime  | date   | create time |
| updateTime  | date   | update time |

***
+ ### Get details of funding records
+ ##### Rate limit:20 times/2 seconds 
+ ##### Required permissions: Trade read permissions
+ ##### method: 
     + **GET api/v1/private/position/funding_records**
- ##### Request parameters:
	
| Parameter  | Data Type  | Required  |  Description |
| ------------ | ------------ | ------------ | ------------ |
| symbol | string  | false  | the name of the contract|
| position_id  | int  | false  | position id|
| page_num  | int  | true  | current page number, default is 1  |
| page_size  | int  | true  | page size, default 20, maximum 100  |

   - ##### Response parameters:
	
| Parameter | Data Type   | Description  |
| ------------ | ------------ | ------------ |
| pageSize  | int  | page size |
| totalCount  | int  | total count |
| totalPage  | int  | total page|
| currentPage  | int  | current page |
| resultList  | list  | data consequence list |
| id  | long  | id |
| symbol  | string  | the name of the contract |
| positionId  | long  | position id |
| positionType  | int  |  1 long  2 short |
| positionValue  | decimal  | position volume |
| funding  | decimal   | funding |
| rate  | decimal   | funding rate  |
| settleTime  | date   | liquidate time |

***

+ ### Query the order based on the external ID
+ ##### Rate limit:20 times/2 seconds 
+ ##### Required permissions: Trade read permissions
+ ##### method: 
     + **GET api/v1/private/order/external/{symbol}/{external_oid}**
- ##### Request parameters:
	
| Parameter  | Data Type  | Required |  Description |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | true  | the name of the contract|
| external_oid  | string  | true  | external order ID|

   - ##### Response parameters:
	
| Parameter  | Data Type  | Description  |
| ------------ | ------------ | ------------ |
| orderId  | long  | page size |
| symbol  | string  | the name of the contract |
| positionId  | long  | position id |
| price  | decimal  | order price |
| vol  | decimal  | order price |
| leverage  | long  | leverage |
| side  | int  | order side 1open long,2close short,3open short,close long |
| category  | int  |order category:1limit order,2close instead order,4ADL reduction|
| orderType  | int  | 1:price limited order,2:Post Only Maker,3:transact  or cancel immediately,4:all transactions or all cancellations，5:market orders,6 convert market price to current price |
| dealAvgPrice  | decimal  | deal average price |
| dealVol  | decimal   | deal volume |
| orderMargin  | decimal   | order margin |
| takerFee  | decimal   | taker fee |
| makerFee  | decimal   | maker fee |
| profit  | decimal   | close profit |
| feeCurrency  | string   | fee currency |
| openType  | int   | open type,1isolated,2cross |
| state  | int   | order state,1:uninformed,2uncompleted,3completed,4canceled,5invalid|
| externalOid  | string   | external order ID |
| createTime  | date   | create time|
| updateTime  | date   | update time |

***


+ ### Query the order based on the order ID
+ ##### Rate limit:20 times/2 seconds 
+ ##### Required permissions: Trade read permissions
+ ##### method: 
     + **GET api/v1/private/order/get/{order_id}**
- ##### Request parameters:
	
| Parameter  | Data Type   | Required  |  Description |
| ------------ | ------------ | ------------ | ------------ |
| order_id  | long  | true  | order number|

   - ##### Response parameters:
	
| Parameter  | Data Type   | Description |
| ------------ | ------------ | ------------ |
| orderId  | long  | page size |
| symbol  | string  | the name of contract |
| positionId  | long  | position id |
| price  | decimal  | order price |
| vol  | decimal  | order volume|
| leverage  | long  | leverage |
| side  | int  | order side 1open long,2close short,3open short,close long |
| category  | int  | order category:1limit order,2close instead order,4ADL reduction |
| orderType  | int  | 1:price limited order,2:Post Only Maker,3:transact  or cancel immediately,4:all transactions or all cancellations，5:market orders,6 convert market price to current price |
| dealAvgPrice  | decimal  | deal average price |
| dealVol  | decimal   | deal volume |
| orderMargin  | decimal   | order margin  |
| takerFee  | decimal   | taker fee |
| makerFee  | decimal   | maker fee |
| profit  | decimal   | close profit |
| feeCurrency  | string   | fee currency |
| openType  | int   | open type,1isolated,2cross |
| state  | int   | order state,1:uninformed,2uncompleted,3completed,4cancelled,5invalid |
| externalOid  | string   |External order ID|
| createTime  | date   | create time  |
| updateTime  | date   | update time |

***

+ ### Query the order in bulk based on the order ID
+ ##### Rate limit:5 times/2 seconds
+ ##### Required permissions: Trade read permissions
+ ##### method: 
     + **GET /api/v1/private/order/batch_query**
- ##### Request parameters:
	
| Parameter  | Data Type   | Required  |  Description |
| ------------ | ------------ | ------------ | ------------ |
| order_ids  | long[]  | true  | order number array，Can be separated by "," for example :order_ids = 1,2,3(maximum 50 orders):|

   - ##### Response parameters:
	
| Parameter  | Data Type   | Description  |
| ------------ | ------------ | ------------ |
| orderId  | long  | page size |
| symbol  | string  | The name of the contract |
| positionId  | long  | position id |
| price  | decimal  | order price |
| vol  | decimal  | volume |
| leverage  | long  | leverage |
| side  | int  | order side 1open long,2close short,3open short,close long |
| category  | int  | order category:1limit order,2close instead order,4ADL reduction |
| orderType  | int  | 1:price limited order,2:Post Only Maker,3:transact  or cancel immediately,4:all transactions or all cancellations，5:market orders,6 convert market price to current price |
| dealAvgPrice  | decimal  | deal |
| dealVol  | decimal   | deal volume |
| orderMargin  | decimal   | order margin  |
| takerFee  | decimal   | taker fee |
| makerFee  | decimal   | maker fee |
| profit  | decimal   | close profit |
| feeCurrency  | string   | fee currency |
| openType  | int   | open type ,1isolated,2cross |
| state  | int   | order state,1:uninformed,2uncompleted,3completed,4cancelled,5invalid |
| externalOid  | string   | external order ID |
| createTime  | date   | create time |
| updateTime  | date   | update time 

***

+ ### Get the user's current pending order
+ ##### Rate limit:20 times/2 seconds 
+ ##### Required permissions: Trade read permissions
+ ##### method: 
     + **GET api/v1/private/order/open_orders/{symbol}**
- ##### Request parameters:
	
| Parameter  | Data Type   | Required  |  Description |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | false  | the name of the contract,不传返回所有|
| page_num  | int  | true  | current page number, default is 1 |
| page_size  | int  | true  |  page size default 20, maximum 100 |

   - ##### Response parameters:
	
| Parameter  |  Data Type   | Description  |
| ------------ | ------------ | ------------ |
| pageSize  | int  | page size|
| totalCount  | int  | total count |
| totalPage  | int  | total pages|
| currentPage  | int  | current page |
| resultList  | list  | data consequence list |
| orderId  | long  | page size |
| symbol  | string  | the name of the contract |
| positionId  | long  | position id |
| price  | decimal  | order price|
| vol  | decimal  | order volume |
| leverage  | long  | leverage |
| side  | int  | order side 1open long,2close short,3open short,close long |
| category  | int  | order category:1limit order,2close instead order,4ADL reduction |
| orderType  | int  |  1:price limited order,2:Post Only Maker,3:transact  or cancel immediately,4:all transactions or all cancellations，5:market orders,6 convert market price to current price |
| dealAvgPrice  | decimal  | deal average price |
| dealVol  | decimal   | deal volume  |
| orderMargin  | decimal   | order margin  |
| usedMargin  | decimal   | used margin  |
| takerFee  | decimal   | taker fee |
| makerFee  | decimal   | maker fee |
| profit  | decimal   | close profit|
| feeCurrency  | string   | fee currency|
| openType  | int   | open type,1 isolated,2 cross |
| state  | int   | order state,1:uninformed,2uncompleted,3completed,4cancelled,5invalid |
| errorCode  | int   | error code,0:normal，1：parameter errors，2：account balance is insufficient，3：the position does not exist，4：  position insufficient，5：For long positions, the order price is less than the close price, while for short positions, the order price is more than the close rice.，6：When opening long, the close price is more than the fair price, while when opening short, the close price is less than the fair price  ,7:exceed risk quota restrictions, 8: system canceled |
| externalOid  | string   | external order ID |
| createTime  | date   | create time  |
| updateTime  | date   | update time |

***

+ ### Get all of the user's historical orders
+ ##### Rate limit:20 times/2 seconds 
+ ##### Required permissions: Trade read permissions
+ ##### method: 
     + **GET api/v1/private/order/history_orders**
- ##### Request parameters:
	
| Parameter | Data Type   | Required  |  Description |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | false  | The name of the contract |
| states  | string  | false  | order state,1:uninformed,2uncompleted,3completed,4cancelled,5invalid; Multiple  separate by ','|
| category  | int  | false  | order category:1limit order,2close instead order,4ADL reduction|
| page_num  | int  | true  | current page number, default is 1  |
| page_size  | int  | true  | page size, default 20, maximum 100 |

   - ##### Response parameters:
	
| Parameter  | Data Type   | Description  |
| ------------ | ------------ | ------------ |
| pageSize  | int  | page size |
| totalCount  | int  | total count |
| totalPage  | int  | total pages |
| currentPage  | int  | current page |
| resultList  | list  | data consequence list  |
| orderId  | long  | page size |
| symbol  | string  | the name of the contract |
| positionId  | long  | position id |
| price  | decimal  | order price|
| vol  | decimal  | order volume |
| leverage  | long  | leverage |
| side  | int  | order side 1open long,2close short,3open short,close long |
| category  | int  | order category:1limit order,2close instead order,4ADL reduction |
| orderType  | int  | 1:price limited order,2:Post Only Maker,3:transact  or cancel immediately,4:all transactions or all cancellations，5:market orders,6 convert market price to current price|
| dealAvgPrice  | decimal  | deal average price |
| dealVol  | decimal   | deal volume |
| orderMargin  | decimal   | order margin  |
| usedMargin  | decimal   | used margin  |
| takerFee  | decimal   | taker fee |
| makerFee  | decimal   | maker fee
| profit  | decimal   | close profit |
| feeCurrency  | string   | fee currency |
| openType  | int   | open type,1 isolated,2 cross |
| state  | int   | order state,1:uninformed,2uncompleted,3completed,4cancelled,5invalid |
| errorCode  | int   | error code,0:normal，1：parameter errors，2：account balance is insufficient，3：the position does not exist，4： position insufficient，5：For long positions, the order price is less than the close price, while for short positions, the order price is more than the close rice.，6：When opening long, the close price is more than the fair price, while when opening short, the close price is less than the fair price .
| externalOid  | string   | external order ID |
| createTime  | date   | create time |
| updateTime  | date   | update tine |

***

+ ### Get order transaction details
+ ##### Rate limit:20 times/2 seconds
+ ##### Required permissions: Trade read permissions
+ ##### method: 
     + **GET api/v1/private/order/deal_details/{order_id}**
- ##### Request parameters :
	
| Parameter | Data Type   | Required  |  Description |
| ------------ | ------------ | ------------ | ------------ |
| order_id  | long  | true  | order id|

   - ##### Response parameters:
	
| Parameter  | Data Type   | Description  |
| ------------ | ------------ | ------------ |
| symbol  | string  | The name of the contract |
| side  | int  |order side 1open long,2close short,3open short,close long |
| vol  | decimal  | deal volume |
| price  | decimal   | deal price |
| fee  | decimal   | fee |
| feeCurrency  | string   | fee currency |
| timestamp  | long   | update time |
***

+ ### Get risk limits
+ ##### Rate limit:20 times/2 seconds 
+ #####Required permissions: Trade read permissions
+ ##### method: 
     + **GET api/v1/private/account/risk_limit**
- ##### Request parameters:
	
| Parameter | Data Type  | Required  |  Description |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | false  | the name of contrcat , not uploaded will return all|

   - ##### Response parameters:
	
| Parameter | Data Type   | Description |
| ------------ | ------------ | ------------ |
| symbol  | string  | the name of the contract |
| positionType  | int  | position type 1:long，2:short |
| level  | int   | current risk level |
| maxVol  | decimal   | maximum position volume |
| maxLeverage  | int   | maximum leverage ratio |
| mmr  | decimal   | maintenance margin rate |
| imr  | decimal   | initial margin rate |
***

+ ### Increase or decrease margin
+ ##### Rate limit:20 times/2 seconds 
+ ##### Required permissions: trading permissions
+ ##### method: 
     + **POST api/v1/private/position/change_margin**
- ##### Request parameters:
	
| Parameter  | Data Type   | Required |  Description |
| ------------ | ------------ | ------------ | ------------ |
| positionId  | long  | true  | position id|
| amount  | decimal  | true  | amount |
| type  | string  | true  | type ,ADD:increase,SUB:decrese|

   - ##### Response parameters: public,success: true,success,false,failure

***

+ ### Modified leverage ratio
+ #####Rate limit:20 times/2 seconds 
+ ##### Required permissions: trading permissions
+ ##### method: 
     + **POST api/v1/private/position/change_leverage**
- ##### Request parameters:
	
| Parameter  | Data Type   | Required|  Description |
| ------------ | ------------ | ------------ | ------------ |
| positionId  | long  | true  | position id|
| leverage  | int  | true  | leverage|

   - ##### Response parameters: public,success: true,false

***

+ ### Order
+ ##### Rate limit:20 times/2 seconds 
+ ##### Required permissions: trading permissions
+ ##### method: 
     + **POST api/v1/private/order/submit**
- ##### Request parameters:
	
| Parameter  | Data Type   | Required  |  Description|
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | true  | the name of the contract|
| price  | decimal  | true  | price |
| vol  | decimal  | true  | volume|
| leverage  | int  | false  | levarage ,the leverage ratio must be passed in when opening the position|
| side  | int  | true  | order side 1 open long ,2close short,3open short ,4 close l|
| type  | int  | true  | order type,1:price limited order,2:Post Only Maker,3:transact  or cancel immediately,4:all transactions or all cancellations，5:market orders |
| openType  | int  | true  | open type,1:isolated,2:cross|
| externalOid  | string  | false  | external order ID|

   - ##### Response parameters: success,success =true,data is the order ID,success =false,failure data=null
***
+ ### Bulk order
+ ##### Rate limit:1/2 seconds 
+ ##### Required permissions: trading permissions
+ ##### method: 
     + **POST api/v1/private/order/submit_batch**
- ##### Request parameters(maximum 50 ):
	
| Parameter  | Data Type   | Required  |  Description |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | true  | the name of the contract |
| price  | decimal  | true  | price|
| vol  | decimal  | true  | volume|
| leverage  | int  | false  | leverage ,the leverage ratio must be passed in when opening the position|
| side  | int  | true  | order side 1open long,2close short,3open short,close long|
| type  | int  | true  | order type,1:price limited order,2:Post Only Maker,3:transact  or cancel immediately,4:all transactions or all cancellations，5:market orders |
| openType  | int  | true  | open type,1:isolated,2:cross||
| externalOid  | string  | false  | external order ID,returns the existing order ID if it already exists|


Example:
```json
    [{
      "symbol": "BTC_USD",
      "price": 8800,
      "vol": 100,
      "leverage": 20,
      "side": 1,
      "type": 1,
      "openType": 1,
      "externalOid":System to cancel "order1"
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
   - ##### Response parameters:
	
| Parameter   | Data Type   | Description  |
| ------------ | ------------ | ------------ |
| externalOid  | string  | external order ID |
| orderId  | long  | order ID, null on failure  |
| errorMsg  | string  | error message, not null when failed|
| errorCode  | int  | error code, default is 0|

***

	
+ ### Oreder canceled
+ ##### Rate limit:20 times/2 seconds 
+ ##### Required permissions: trading permissions
+ ##### method: 
     + **POST api/v1/private/order/cancel**
- ##### Request parameters:
	
| Parameter | Data Type   | Required  |  Description |
| ------------ | ------------ | ------------ | ------------ |
| None  | List<Long>  | true  | order id list,maximum 50|

 Example :[1,2,3]

   - ##### Response parameters: - ##### Response parameters: public parameters, Success: true success,false failure

***
+ ### Cancel all orders under a contract
+ ##### Rate limit:20 times/2 seconds 
+ ##### Required permissions: trading permissions
+ ##### method: 
     + **POST api/v1/private/order/cancel_all**
- ##### Request parameters:
	
| Parameter   | Data Type   | Required |  Description |
| ------------ | ------------ | ------------ | ------------ |
| symbol  | string  | false | the name of the contract, fill in The Symbol cancels only orders placed under this contract, not all orders placed under this contract|

   - ##### Response parameters: - ##### Response parameters: public,success: true success,false failure
   
 ***
 + ### Modify the risk level
 + ##### Rate limit:20 times/2 seconds 
 + ##### Required permissions: trading permissions
 + ##### method: 
      + **POST api/v1/private/account/change_risk_level**
 - ##### Request parameters:
 	
 | Parameter   | Data Type   | Required  |  Description |
 | ------------ | ------------ | ------------ | ------------ |
 | symbol  | string  | true  | the name of the contract |
 | level  | int  | true  | level|
 | positionType  | int  | true  | 1:long，2:short |
 
    - ##### Response parameters: - ##### Response parameters: public parameters,success: true success ,false failure
    
 ***

 - #### Error code:
  
  | code  | Description  |
  | ------------ | ------------ |
  | 0  | succeed  |
  | 9999  | public abnormal  |
  | 500  | internal error  |
  | 501  | system busyness  |
  | 401  | unauthorized  | 
  | 402  | api_key overdue  |
  | 406  | access ip is not in the list  |
  | 506  | unknow request  |
  | 510  | excessive frequency of requests  |
  | 511  | interface inaccessible  |
  | 513  | invalid request(For access API request time more than server time 2 seconds or less than server time 10 seconds or more  |
  | 600  | parameter error  |
  | 601  | data decode error  |
  | 602  | verify failed  |
  | 603  | repeated requests  |
  | 1000  | account does not exist  |
  | 1001  | contract does not exist |
  | 1002  | Contract not activated  |
  | 1003  | wrong risk limit level  |
  | 1004  | amount error  |
  | 2001  | wrong order side  |
  | 2002  | wrong opening type  |
  | 2003  | overpriced to buy  |
  | 2004  | lowpriced to sell  |
  | 2005  | lack of balance  |
  | 2006  | leverage ratio error |
  | 2007  | order price error  |
  | 2008  | closing quantity is insufficient  |
  | 2009  | position does not exist or have been closed  |
  | 2011  | order quantity error  |
  | 2013  | cancel orders excess maximum the limit  |
  | 2014  |  batch order quantity exceeds the limit  |
  | 2015  | price or quantity scale error |
  | 2016  | order planned over the maximum  |
  | 2018  | exceed the maximum order margin  |
  | 2019  | there is an active open position order``  |
  | 2021  |  the order leverage  is not consistent with the existing position leverage  |
  | 2022  | error position type  |
  | 2023  | the new position over the maximum leverage position  |
  | 2024  | the new order over the maximum leverage order  |
  | 2025  | the new holding positions over the maximum position  |
  | 2026  | leverage modified unsupported for long positon.  |
  | 2027  | only one option for cross or isolated position in the same side. |
  | 3001  | order planned price error  |
  | 3002  | order planned trigger  error |
  | 3003  | xxecution cycle error  |
  | 3004  | trigger price error |
  | 4001  | unsupported currency |
***


# WEBSOCKET Module

## Essential Information:
	original ws link : wss://contract.mxc.ai/ws 、  wss://contract.mxc.com/ws 、 wss://contract.mxc.io/ws

## Details of data interaction commands:
    List of subscribe/unsubscribe data commands（ws identification is not required except the list of personal related commands）
    send ping  message:
    {"method":"ping"}
    Servers return:
    {"channel":"pong","data":1587453241453},If no ping is received within 1 minute, the connection will be disconnected. It is recommended to send a ping for 10-20 seconds.
### Public model:
***
####tickers: 

+ ##### Subscribe:
```json
 {"method":"sub.tickers","param":{}}
```
If you want to return content (the same with following subscription ):
```json
 {"method":"sub.tickers","param":{},"gzip": false}
```
  - ##### Response parameters:
	
| Parameter   | Data Type  | Description  |
| ------------ | ------------ | ------------ |
| symbol  | string | the name of the contract |
| lastPrice  | decimal  | last price |
| volume24  | decimal  | 24 hours trading volume, according to the  statistics count |
| riseFallRate  | decimal  | rise fall rate |
| fairPrice  | decimal  | fair price |
Example:
```json
 {"channel":"push.tickers",
 "data":[
    {"fairPrice":183.01,"lastPrice":183,"riseFallRate":-0.0708,"symbol":"BSV_USDT","volume24":200},
    {"fairPrice":220.22,"lastPrice":220.4,"riseFallRate":-0.0686,"symbol":"BCH_USDT","volume24":200}
    ],
 "ts":1587442022003}
```
+ ##### Unsubscribe:
```json
 {"method":"unsub.tickers","param":{}}
```
***
#### Single ticker: 

+ ##### Subscribe:
```json
{"method":"sub.ticker","param":{"symbol":"BTC_USDT"}}
```
  - ##### Response parameters:
	
| Parameter  | Data Type   | Description  |
| ------------ | ------------ | ------------ |
| symbol  | string | The name of the contract |
| lastPrice  | decimal  | last price |
| bid1  | decimal  | bid/1 |
| ask1  | decimal  | ask/1 |
| volume24  | decimal  | 24 hours trading volume, according to the number of statistics |
| holdVol| decimal  | hold volume |
| lower24Price  | decimal  | lowest price within 24 hours |
| high24Price  | decimal  | highest price in 24 hours |
| riseFallRate  | decimal  | rise fall rate |
| riseFallValue  | decimal  | rise fall value|
| indexPrice  | decimal  | index price |
| fairPrice  | decimal  | fair price |
| fundingRate  | decimal  | funding fee |
| timestamp  | long   | system timestamp|
Example:
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
+ ##### Unsubscribe:
```json
{"method":"unsub.ticker","param":{"symbol":"BTC_USDT"}}
```
***

#### Deal: 

+ ##### Subscribe:
```json
{"method":"sub.deal","param":{"symbol":"BTC_USDT"}}
```
   - ##### Response parameters:
	
| Parameter   | Data Type   | Description  |
| ------------ | ------------ | ------------ |
| p  | decimal  | deal price |
| v  | decimal  | volume |
| T  | int  | transaction direction,1:purchase,2:sell |
| O  | int   | Open position?, 1: Yes,2: No, vol is the newly added position when O is 1 |
| M  | int   | Is it automatic? 1: Yes,2: No |
| t  | long   | deal time |
Example:
```json
{"channel":"push.deal",
"data":{"M":1,"O":1,"T":1,"p":6866.5,"t":1587442049632,"v":2096},
"symbol":"BTC_USDT",
"ts":1587442022003
}
```
+ ##### Unsubscribe:
```json
{"method":"unsub.deal","param":{"symbol":"BTC_USDT"}}
```
***

#### Depth: 

+ #####Subscribe:
```json
{"method":"sub.depth","param":{"symbol":"BTC_USDT"}} //Incremental, all
{"method":"sub.depth","param":{"symbol":"BTC_USDT","compress":true}}//Incremental, push after compression
{"method":"sub.depth.full","param":{"symbol":"BTC_USDT","limit":5}}//Limit :5, 10, 20, default is 20, can only subscribe to the full amount of one gear
```
   - ##### Response Parameter:
	
| Parameter  | Data Type  | Description |
| ------------ | ------------ | ------------ |
| asks  | List<Numeric[]> |seller depth |
| bids  | List<Numeric[]> | buyerdepth |
| version  | long  | the version number |
Tip: [411.8, 10, 1] 411.8 is price，10 is the order numbers of the contract ,1 is the order quantity

example:
```json
{"channel":"push.depth",
"data":{"asks":[[6859.5,3251,1]],"bids":[],"version":96801927},
"symbol":"BTC_USDT",
"ts":1587442022003

}
```
+ ##### Unsubscribe:
```json
{"method":"unsub.depth","param":{"symbol":"BTC_USDT"}}//Increments are unsubscribed with this event
{"method":"usub.depth.full","param":{"symbol":"BTC_USDT"}}//unsubscribe to the full amount, limit or not
```
***

#### K line: 

+ ##### Subscribe:
```json
{"method":"sub.kline","param":{"symbol":"BTC_USDT","interval":"Min60"}}
```
interval optional parameters:  Min1、Min5、Min15、Min30、Min60、Hour4、Hour8、Day1、Week1、Month1 
   - ##### Response parameters:
	
| Parameter   | Data Type   | Description  |
| ------------ | ------------ | ------------ |
| symbol  | string | The name of the contract |
| a  | decimal  | total transaction amount |
| c  | decimal  | the closing price |
| interval  | string  | interval: Min1、Min5、Min15、Min30、Min60、Hour4、Hour8、Day1、Week1、Month1 |
| l  | decimal  | the lowest price |
| o  | decimal  | the opening price |
| q  | decimal  | total quantity of transactions |
| h  | decimal  | the highest price |
| t  | long  | trading time，unit：second（s）， the start time of the window（windowStart） |
example:
```json
{"channel":"push.kline",
"data":{"a":233.740269343644737245,"c":6885,"h":6910.5,
"interval":"Min60","l":6885,"o":6894.5,
"q":1611754,"symbol":"BTC_USDT","t":1587448800},
"symbol":"BTC_USDT",
"ts":1587442022003
}
```
+ ##### Unsubscribe:
```json
{"method":"unsub.kline","param":{"symbol":"BTC_USDT"}}
```
***

#### capital rate: 

+ ##### Subscribe:
```json
{"method":"sub.funding.rate","param":{"symbol":"BTC_USDT"}}
```
   - ##### Response parameters:
	
| Parameter   | Data Type  | Description |
| ------------ | ------------ | ------------ |
| symbol  | string | the name of the contract |
| fundingRate  | decimal  | funding rate |
| nextSettleTime  | long  | next liquidate time |

Example:
```json
{"channel":"push.funding.rate",
"data":{"rate":0.001,"symbol":"BTC_USDT"},
"symbol":"BTC_USDT",
"ts":1587442022003
}
```
+ ##### Unsubscribe:
```json
{"method":"unsub.funding.rate","param":{"symbol":"BTC_USDT"}}
```
***

#### index price: 

+ ##### Subscribe:
```json
{"method":"sub.index.price","param":{"symbol":"BTC_USDT"}}
```
  - ##### Response parameters:
	
| Parameter   | Data Type   | Description  |
| ------------ | ------------ | ------------ |
| symbol  | string | the name of the contract |
| price  | decimal  | price |

example:
```json
{"channel":"push.index.price",
"data":{"price":0.001,"symbol":"BTC_USDT"},
"symbol":"BTC_USDT",
"ts":1587442022003
}
```
+ ##### Unsubscribe:
```json
{"method":"unsub.index.price","param":{"symbol":"BTC_USDT"}}
```
***

#### Fair price: 

+ ##### Subscribe:
```json
{"method":"sub.fair.price","param":{"symbol":"BTC_USDT"}}
```
  - ##### Response parameters:
	
| Parameter   | Data Type   | Description  |
| ------------ | ------------ | ------------ |
| symbol  | string | the name of the contract |
| price  | decimal  | price |

examp:
```json
{"channel":"push.fair.price",
"data":{"price":0.001,"symbol":"BTC_USDT"},
"symbol":"BTC_USDT",
"ts":1587442022003
}
```
+ ##### Unsubscribe:
```json
{"method":"unsub.fair.price","param":{"symbol":"BTC_USDT"}}
```
***

### Private Module:

- #### Login validation
```json
{
	"method":"login",
	"param":{
		"apiKey":"apiKey", // Openapi needs to pass this parameter, which is constructed in accordance with the OpenAPI documentation
		"reqTime":"reqTime", // Openapi needs to pass this parameter, which is constructed in accordance with the OpenAPI documentation
		"signature":"signature" // Openapi needs to pass this parameter, which is constructed in accordance with the OpenAPI documentation
	}
}
```
#### Response parameters: success: None，fail:Returns the corresponding error message,channel = rs.error

- ##### Login succeed(channel = rs.login)
Example:
```json
{"channel":"rs.login",
"data":"success",
"ts":"11111111111"
}
```


- ##### Order (channel = push.personal.order)
| Parameter   | Data Type   | Description  |
| ------------ | ------------ | ------------ |
| orderId  | long  | page size |
| symbol  | string  | the name of the contract |
| positionId  | long  | position id |
| price  | decimal  | order price |
| vol  | decimal  | order volume |
| leverage  | long  | leverage |
| side  | int  | order side 1open long,2close short,3open short,close long |
| category  | int  | order category:1limit order,2close instead order,4ADL reduction仓 |
| orderType  | int  | 1:price limited order,2:Post Only Maker,3:transact  or cancel immediately,4:all transactions or all cancellations |
| dealAvgPrice  | decimal  | deal avarage price |
| dealVol  | decimal   | deal volume |
| orderMargin  | decimal   | order margin  |
| usedMargin  | decimal   | used margin  |
| takerFee  | decimal   | taker fee |
| makerFee  | decimal   | maker fee |
| profit  | decimal   | close profit |
| feeCurrency  | string   | fee currency |
| openType  | int   | open type,1:isolated,2:cross| |
| state  | int   | order state,1:uninformed,2uncompleted,3completed,4cancelled,5invalid |
| errorCode  | int   |error code,0:normal，1：parameter errors，2：account balance is insufficient，3：the position does not exist，4： position insufficient，5：For long positions, the order price is less than the close price, while for short positions, the order price is more than the close rice.，6：When opening long, the close price is more than the fair price, while when opening short, the close price is less than the fair price ,7:exceed the risk limit，8:system  cancelled |
| externalOid  | string   | extranal order id |
| createTime  | date   | create time |
| updateTime  | date   | update time |
 
 - ##### Assert (channel = push.personal.asset)
 
 | Parameter   | Data Type   | Description  |
 | ------------ | ------------ | ------------ |
 | currency  | string  | currency |
 | positionMargin  | decimal   | position margin |
 | frozenBalance  | decimal   | frozen balance |
 | availableBalance  | decimal   | available balance |
 | cashBalance  | decimal   | drawable balance |
 
 - ##### position (channel = push.personal.position)
 	
| Parameter  | Data Type   | Description  |
| ------------ | ------------ | ------------ |
| positionId  | long  | position id |
| symbol  | string  | the name of the contract |
| holdVol  | decimal  | hold volume |
| positionType  | int  | position type， 1long 2short |
| openType  | int   | open type， 1isolated 2cross |
| state  | int   | position state,1holding2system holding 3closed  |
| frozenVol  | decimal   | frozen volume |
| closeVol  | decimal   | close volume|
| holdAvgPrice  | decimal   | hold average price |
| closeAvgPrice  | decimal   | close average price |
| openAvgPrice  | decimal   | open average price |
| liquidatePrice  | decimal   | liquidate price |
| oim  | decimal   | original initial margin |
| adlLevel  | int   | The value of ADL is 1-5. If it is empty,  wait for the refresh |
| im  | decimal   | initial margin， add or subtract this item can be used to adjust the liquidate price |
| holdFee  | decimal   | hold fee,  positive  means u get it,  negative means lose it |
| realised  | decimal   | profit and loss achieved |
| createTime  | date   | create time|
| updateTime  | date   | update time |


 - ##### Risk limitation(channel = push.personal.risk.limit)
 	
| Parameter   | Data Type   | Description  |
| ------------ | ------------ | ------------ |
| symbol  | string  | the name of the contract |
| positionType  | int  | position typt 1:long，2:short|
| level  | int   | current risk level |
| maxVol  | decimal   | maximum position volume |
| maxLeverage  | int   | maximum leverage ratio |
| mmr  | decimal   | maintenance margin rate |
| imr  | decimal   | initial margin rate|

 - ##### adl automatic reduction of position level(channel = push.personal.adl.level)
 	
| Parameter   | Data Type  | Description |
| ------------ | ------------ | ------------ |
| adlLevel| int   | the current adl level ：1-5|
| positionId  | long   | position id |

 - ##### How is depth information maintained
  
#### How is incremental depth information maintained:
        1:Though https://contract.mxc.com/api/v1/contract/depth/BTC_USDT to get full amount of depth information, save the current version.
        2. Subscribe to ws depth information, if the received data version more than the current version after update,  the later received update overwrites the previous one at the same price.
        3.Through https://contract.mxc.com/api/v1/contract/depth_commits/BTC_USDT/1000 get  the latest 1000 depth snapshots.
        4.Discard version data from the snapshot obtained by Version (less than step 3 )for the same price in the current cached depth information
        5.Update the contents of the deep snapshot to the local cache and keep updating from the event received by the WS
       6. The version of each new event should be exactly equal to version+1 of the previous event, otherwise packet loss may occur. In case of packet loss or discontinuous version of the event retrieved, please re-initialize from Step 3.
       7. The amount of hanging orders in each event represents the absolute value of the current hanging orders of the price, rather than the relative change.
        8. If the amount of a hanging order corresponding to a certain price is 0, it means that the hanging order at that price has been cancelled, the price should be removed.

#### Subscriptions，subscribe succeed response:     
   
      channel is rs. + Subscribed method，
      data为 "success"
Submit subscription information：
```json
{"method":"sub.deal","param":{"symbol":"BTC_USDT"}}
```
subscribe succeed response:
```json
{"channel":"rs.sub.deal",
"data":"success",
"ts":"11111111111"
}
```

subscribe failed response:
```json
{"channel":"rs.error",
"data":"Contract doesn't exist!",
"ts":"11111111111"
}
```



