# ![MXC logo](https://www.mxc.com/assets/images/site-logo.png "MXC logo")Web Socket API for MXC (2019-06-25)

#	基本信息  

WSS接口Base url: wss://www.mxc.com/

##	公共接口：
----

###	K线

#### 获取全量的k线

**Stream**:  
request **get.kline**  
response **rs.kline**  

**Request Payload:**  

```javascript
{
    "symbol":"VDS_USDT",	//	交易对
    "interval":"Min30",		//	K线间隔时间	目前只支持 Min1、Min5、Min15、Min30、Min60、Day1、Month1
    "start":1561107500000,	//	K线起始时间
    "end":1561453160000		//	K线结束时间
}
```
**Response Payload:**
```javascript
{
    "symbol":"VDS_USDT",	// 交易对
    "data":{
        "q":[				// 这根K线期间成交额
            25710.17
        ],
        "s":"ok",			//状态
        "c":[				// 这根K线期间末一笔成交价
            1561449600
        ],
        "v":[				// 这根K线期间成交量
            295324.832092
        ],
        "h":[				// 这根K线期间最高成交价
            4.0439
        ],
        "l":[				// 这根K线期间最低成交价
            3.8564
        ],
        "o":[				// 这根K线期间第一笔成交价
            3.8817
        ]
    }
}
```
----

#### 获取增量的k线  
**Stream**:  
request **sub.kline**  
response **push.kline**  
**Request Payload:**  
```javascript
{
    "symbol":"VDS_USDT",
    "interval":"Min30"
}
```
**Response Payload:**
```javascript
{
    "symbol":"VDS_USDT",	// 交易对
    "data":{
        "q":[				// 这根K线期间成交额
            25710.17,		
            33157.01
        ],
        "s":"ok",
        "c":[				// 这根K线期间末一笔成交价
            1561449600,
            1561451400
        ],
        "v":[				// 这根K线期间成交量
            295324.832092,
            1414326.509656
        ],
        "h":[				// 这根K线期间最高成交价
            4.0439,			
            4.39
        ],
        "l":[				// 这根K线期间最低成交价
            3.8564,
            3.8568
        ],
        "o":[				// 这根K线期间第一笔成交价
            3.8817,
            4.0195
        ]
    }
}
```
----

#### 成交记录
获取最近时间的成交记录
**Stream**:  
request **get.deal**  
response **rs.deal**  
**Request Payload:** 
```javascript 
{
    "symbol":"VDS_USDT"	//	交易对
}
```
**Response Payload**
```javascript
[
    {
        "p":4.0806,			//成交价格
        "q":31.2,			//成交数量
        "T":2,				//交易类型
        "t":1561454717617	//成交时间
    }
]
```
----

#### 订阅交易信息
订阅交易信息

**Stream**:  
request **sub.symbol**  
response **push.symbol**  
**Request Payload:** 
```javascript 
{
    "symbol":"VDS_USDT"	//	交易对
}
```
**Response Payload**
```javascript
{
    "data":{					//数据
        "deals":				//成交信息
	        [
	            {
	                "t":1561465233455,	//成交时间
	                "p":"4.2003",		//交易价格
	                "q":"86.68",		//成交数量
	                "T":1			//成交类型:1买、2卖
	            }
	        ],
        "bids": 				//买单
	        [		
	            {
	                "p":"4.2000",		//买单价格
	                "q":"1488.43",		//买单数量
	                "a":"6251.40600"	//买单总量
	            }
	        ],
        "asks":					//卖单
	        [					
	            {		
	                "p":"4.2000",		//卖单价格
	                "q":"1488.43",		//卖单数量
	                "a":"6251.40600"	//卖单总量
	            }
	        ]
    },
    "symbol":"VDS_USDT"				//交易对
}
```

----
#### 获取交易对深度(全量)

**Stream**:  
request **get.depth**  
response **rs.depth**  
**Request Payload:** 
```javascript 
{
    "symbol":"AO_USDT"  //交易对
}
```
**Response Payload**
```javascript
{
    "asks":[                   //卖单
        {
            "p":1,             //价格
            "q":67990.27       //数量
        }
    ],
    "bids":[                //买单
        {
            "p":0.0004,        //价格
            "q":78414.35       //数量
        }
    ]
}
```

----

## 私有接口

#### 登录
Open Api接入

**Stream**:  
request **sub.personal**

**Request Payload:** 
```javascript 
{
    "api_key": "api_key",	//申请的API Key	
    "sign": "b8d2ff6432798ef858782d7fd109ab41",	//签名,签名规则  把api_key、req_time用私钥做一个签名,参考python或者java的Sample
    "req_time": "1561433613583"			//当前时间的时间搓
}


```


获取订阅私有接口数据的错误信息


response **rs.error** 

**Response Payload**  

```javascript
{
    "msg":"用户信息错误.",	//错误信息
    "type":"sub.personal"	//错误订阅stream
}
```

response **push.personal.order**  
如果数据里面全部为0那么代表订单撤单.请通过open api获取订单信息

**Response Payload**  

```javascript
{
    "symbol":"ETH_USDT",
    "data":{
        "price":1,
        "quantity":5,
        "amount":5.01,
        "remainAmount":5.01,
        "remainQuantity":5,
        "id":"069e29f4-8870-489f-aebf-d5aec5162bc2",
        "status":1,
        "tradeType":1,
        "createTime":1561518653000
    }
}
```

