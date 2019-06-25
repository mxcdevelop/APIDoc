# ![MXC logo](https://www.mxc.com/assets/images/site-logo.png "MXC logo")Web Socket API for MXC (2019-06-25)

#	基本信息
----
WSS接口Base url: wss://www.mxc.com/

##	公共接口：
----
### 币种信息

获取币种信息

**Stream**  
Request:	**get.symbol**  
Response:	**rs.symbol**  
**Request Payload:**
```javascript
{
    "symbol":"VDS_USDT"// 交易对
}
```
**Response Payload:**  
```javascript
{
    "market":"USDT",
    "currency":"ETH",
    "fullName":"Ethereum",
    "priceScale":2,
    "quantityScale":5,
    "sort":3,
    "suggest":1,
    "icon":"F201904021431026806xaLWK30i3vnWd",
    "type":"MAIN",
    "beforeOrderSide":"none",
    "presaleEnable":0,
    "presalePrice":0,
    "presaleMemberId":"",
    "fly":true,
    "rate":0.0183,
    "url":"https://coinmarketcap.com/zh/currencies/ethereum/",
    "note":"Ethereum（以太坊）是一个平台和一种编程语言，使开发人员能够建立和发布下一代分布式应用。 Ethereum可以用来编程，分散，担保和交易任何事物：投票，域名，金融交易所，众筹，公司管理， 合同和大部分的协议，知识产权，还有得益于硬件集成的智能资产。",
    "noteEn":"Ethereum (Ethernet Square) is a platform and a programming language that enables developers to build and deploy the next generation of distributed applications. & Nbsp; Ethereum can be used to program, distributed, secured transactions and any thing: voting, domain names, financial & nbsp Exchange, crowdfunding, corporate governance,; most of the contracts and agreements, intellectual property rights, as well as benefit from the integration of hardware intelligence assets.",
    "c":314.05,
    "o":308.38,
    "h":316.5,
    "l":307.28,
    "q":188999.65972,
    "a":59123779.63
}
```
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