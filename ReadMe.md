# MXC API

- - - - - -

## API简介

欢迎使用MXC API，API分为[公共接口](#公共接口)和[私有接口](#私有接口)，你可以使用公共接口查询行情数据，并使用私有接口进行交易。

对于私有接口，为了防止请求在网络传输过程中被篡改，您需要使用您的API Key做签名认证，以保证我们收到的请求为您本人发出。一个合法的MXC签名是将所有参数名按字母顺序排列，用&连接各参数，再加上您的api_secret，做MD5生成签名。需将签名放入参数sign中。
>以`获取账户资产信息`接口为例，需要传的参数包括`api_key`，`req_time`和`sign`共三个，除去`sign`之外的参数都需要参与签名。
>
>如果您的`api_key`为`mmmyyyaaapppiiikkkeeeyyy`，您的`api_secret`为`ssseeecccrrreeettt`，当前的十位的时间戳为`1234567890`。
>
>首先将需要签名的参数按照参数名进行排序(首先比较所有参数名的第一个字母，按英文字母顺序排列，若遇到相同首字母，则看第二个字母，以此类推)。所以第一个参数为`api_key`，第二个参数为`req_time`。
>
>将参数和参数值用`=`进行连接：得到`api_key=mmmyyyaaapppiiikkkeeeyyy`和`req_time=1234567890`。
>
>将得到的值用`&`进行连接，得到`api_key=mmmyyyaaapppiiikkkeeeyyy&req_time=1234567890`
>
>在最后连接`&api_secret=ssseeecccrrreeettt`，得到`api_key=mmmyyyaaapppiiikkkeeeyyy&req_time=1234567890&api_secret=ssseeecccrrreeettt`。
>
>将得到的字符串用MD5摘要算法进行签名，得到`c90172772df116dd658141e853185517`，加入到`sign`参数中
>
>将`api_key`，`req_time`和`sign`三个参数放入请求中并发送请求


----

API HOST: **https://www.mxc.com**

公共接口：

* GET [/open/api/v1/data/markets](#获取市场列表信息) 获取市场列表信息
* GET [/open/api/v1/data/markets_info](#获取交易对信息) 获取交易对信息
* GET [/open/api/v1/data/depth](#获取深度信息) 获取深度信息
* GET [/open/api/v1/data/history](#获取单个币种成交记录信息) 获取单个币种成交记录信息
* GET [/open/api/v1/data/ticker](#获取市场行情信息) 获取市场行情信息
* GET [/open/api/v1/data/kline](#获取市场k线信息) 获取市场k线信息

私有接口：

* GET [/open/api/v1/private/account/info](#获取账户资产信息) 获取账户资产信息
* GET [/open/api/v1/private/current/orders](#获取当前委托信息) 获取当前委托信息
* POST [/open/api/v1/private/order](#下单) 下单
* DELETE [/open/api/v1/private/order](#取消订单) 取消订单
* GET [/open/api/v1/private/orders](#查询账号历史成交) 查询账号历史成交

---

## 接口说明

代码库中的python目录为Python的示例代码，java目录为java的示例代码。


## **获取市场列表信息**

* GET `/open/api/v1/data/markets`

**请求参数**

| 参数        | 类型   |  是否必须   |  说明   |
| :--------:   | :-----:  |  :-----:  |  :-----:  |

**返回值**

{'code': 200, 'data': ['btc_usdt', ... ,'btc_eos'], 'msg': 'OK'}

**返回值说明**

| 返回值        |  说明   |
| :--------:   | :-----:  |
| data        |  包含所有交易对的数组   |

**示例**

[python](#获取市场列表信息-python-demo)

----

## **获取交易对信息**

* GET `/open/api/v1/data/markets_info`

**请求参数**

| 参数        | 类型   |  是否必须   |  说明   |
| :--------:   | :-----:  |  :-----:  |  :-----:  |

**返回值**

{'code': 200, 'data': {'EOS_BTC': {'priceScale': 8, 'quantityScale': 8, 'minAmount': 0.0, 'buyFeeRate': 0.001, 'sellFeeRate': 0.001}, ...}, 'msg': 'OK'}

**返回值说明**

| 返回值        |  说明   |
| :--------:   | :-----:  |
| priceScale        |  价格精度   |
| quantityScale        |  数量精度   |
| minAmount        |  最小量   |
| buyFeeRate        |  买单费率   |
| sellFeeRate        |  卖单费率   |

**示例**

[python](#获取交易对信息-python-demo)

----

## **获取深度信息**

* GET `/open/api/v1/data/depth`

**请求参数**

| 参数        | 类型   |  是否必须   |  说明   |
| :--------:   | :-----:  |  :-----:  |  :-----:  |
| depth        | integer   |  √   |  返回的深度个数   |
| market        | string   |  √   |  交易对名称   |

**返回值**

{'code': 200, 'data': {'asks': [{'price': '3802.8', 'quantity': '0.999'}, ...], 'bids': [{'price': '3802.35', 'quantity': '0.487573'}, ...]}, 'msg': 'OK'}

**返回值说明**

| 返回值        |  说明   |
| :--------:   | :-----:  |
| price        |  价格   |
| quantity        |  数量   |

**示例**

[python](#获取深度信息-python-demo)

----

## **获取单个币种成交记录信息**

* GET `/open/api/v1/data/history`

**请求参数**

| 参数        | 类型   |  是否必须   |  说明   |
| :--------:   | :-----:  |  :-----:  |  :-----:  |
| market        | string   |  √   |  交易对名称   |

**返回值**

{'code': 200, 'data': [{'tradeTime': '2019-02-26 17:38:45.993', 'tradePrice': '3806.42', 'tradeQuantity': '0.011905', 'tradeType': '2'}, ...], 'msg': 'OK'}

**返回值说明**

| 返回值        |  说明   |
| :--------:   | :-----:  |
| tradeTime        |  成交时间   |
| tradePrice        |  成交价格   |
| tradeQuantity        |  成交量   |
| tradeType        |  成交类型1/2 (买/卖)   |

**示例**

[python](#获取单个币种成交记录信息-python-demo)

----

## **获取市场行情信息**

* GET `/open/api/v1/data/ticker`

**请求参数**

| 参数        | 类型   |  是否必须   |  说明   |
| :--------:   | :-----:  |  :-----:  |  :-----:  |
| market        | string   |  √   |  交易对   |

**返回值**

{'code': 200, 'data': {'volume': '6643.760303', 'high': '3867.25', 'low': '3764.95', 'buy': '3808.85', 'sell': '3809.44', 'open': '3788.47', 'last': '3809.3'}, 'msg': 'OK'}

**返回值说明**

| 返回值        |  说明   |
| :--------:   | :-----:  |
| volume        |  24小时成交量   |
| high        |  24小时最高价   |
| low        |  24小时最低价   |
| buy        |  买一价   |
| sell        |  卖一价   |
| open        |  开盘价   |
| last        |  最后成交价   |

**示例**

[python](#获取市场行情信息-python-demo)

----

## **获取市场k线信息**

* GET `/open/api/v1/data/kline`

**请求参数**

| 参数        | 类型   |  是否必须   |  说明   |
| :--------:   | :-----:  |  :-----:  |  :-----:  |
| market       | string   |  √   |  交易对   |
| interval     | string   |  √   |  时间间隔(分钟制:1m，5m，15m，30m，60m。小时制:1h，天制:1d，月制:1M)|
| startTime    | long     |  √   |  起始时间(单位秒,毫秒数/1000 ) |
| limit        | long     |  √   |  返回条数 |

**返回值说明**

```text
[
  [
    
    1552637940,       # 开始时间 (单位秒,毫秒数/1000 )
    "0.02700818",        # 开盘价
    "0.02701826",        # 收盘价
    "0.02703995",        # 最高价  
    "0.02699879",        # 最低价
    "4147.5086",         # 成交量
    "112.05043000089"    # 计价货币成交量
  ],
]
```

| 返回值       |  说明   |
| :--------:  | :-----:  |
| time        |  开始时间 (单位秒,毫秒数/1000 )  |
| open        |  开盘价   |
| close       |  收盘价   |
| high        |  最高价   |
| low         |  最低价   |
| vol         |  成交量   |
| amount      |  计价货币成交量   |



## **获取账户资产信息**

* GET `/open/api/v1/private/account/info`

**请求参数**

| 参数        | 类型   |  是否必须   |  说明   |
| :--------:   | :-----:  |  :-----:  |  :-----:  |
| api_key         | string   |  √   |  您的api key   |
| req_time          | string   |  √   |  请求时间戳   |
| sign          | string   |  √   |  请求签名   |

**返回值**

{'ETH': {'frozen': '5.12501497', 'available': '12.65392852'}, 'EOS': {'frozen': '0', 'available': '288.2285'}}


**返回值说明**

| 返回值        |  说明   |
| :--------:   | :-----:  |
| frozen        |  冻结量   |
| available        |  可用量   |

**示例**

[python](#获取账户资产信息-python-demo)

----

## **获取当前委托信息**

* GET `/open/api/v1/private/current/orders`

**请求参数**

| 参数        | 类型   |  是否必须   |  说明   |
| :--------:   | :-----:  |  :-----:  |  :-----:  |
| api_key         | string   |  √   |  您的api key   |
| market          | string   |  √   |  交易对   |
| page_num           | integer   |  √   |  页数   |
| page_size           | integer   |  √   |  每页大小   |
| req_time            | string   |  √   |  请求时间戳   |
| trade_type            | integer   |  √   |  交易类型，0/1/2 (所有/买/卖)   |
| sign          | string   |  √   |  请求签名   |

**返回值**

{'code': 200, 'data': [{'id': '5feb364f-xxxx-xxxx-xxxx-f34c93f4ddfa', 'market': 'EOS_ETH', 'price': '0.0251', 'status': '1', 'totalQuantity': '99.9999', 'tradedQuantity': '0.0000', 'tradedAmount': '0', 'createTime': '2019-02-26 14:50:22', 'type': 1}, ...], 'msg': 'OK'}

**返回值说明**

| 返回值        |  说明   |
| :--------:   | :-----:  |
| id        |  订单id   |
| market        |  交易对   |
| price        |  挂单价   |
| status        |  订单状态，1:未成交 2:已成交 3:部分成交 4:已撤单 5:部分撤单   |
| totalQuantity        |  挂单总量   |
| tradedQuantity        |  挂单成交量   |
| tradedAmount        |  挂单成交量(计价币)   |
| createTime        |  订单创建时间   |
| type        |  订单类型1/2 (买/卖)   |

**示例**

[python](#获取当前委托信息-python-demo)

----

## **下单**

* POST `/open/api/v1/private/order`

**请求参数**

| 参数        | 类型   |  是否必须   |  说明   |
| :--------:   | :-----:  |  :-----:  |  :-----:  |
| api_key         | string   |  √   |  您的api key   |
| market          | string   |  √   |  交易对   |
| price            | string   |  √   |  交易价格   |
| quantity            | string   |  √   |  交易数量   |
| req_time            | string   |  √   |  请求时间戳   |
| trade_type            | integer   |  √   |  交易类型：1/2 (买/卖)   |
| sign          | string   |  √   |  请求签名   |

**返回值**

{'code': 200, 'data': 'd66cce2e-xxxx-xxxx-xxxx-cf080e0d8aae', 'msg': 'OK'}

**返回值说明**

| 返回值        |  说明   |
| :--------:   | :-----:  |
| data        |  订单id   |

**示例**

[python](#下单-python-demo)

----

## **取消订单**

* DELETE `/open/api/v1/private/order`

**请求参数**

| 参数        | 类型   |  是否必须   |  说明   |
| :--------:   | :-----:  |  :-----:  |  :-----:  |
| api_key         | string   |  √   |  您的api key   |
| market          | string   |  √   |  交易对   |
| req_time            | string   |  √   |  请求时间戳   |
| trade_no             | string   |  √   |  委托单号   |
| sign          | string   |  √   |  请求签名   |

**返回值**

{'code': 200, 'data': None, 'msg': 'OK'}

**返回值说明**

| 返回值        |  说明   |
| :--------:   | :-----:  |

**示例**

[python](#取消订单-python-demo)

----

## **查询账号历史成交**

* GET `/open/api/v1/private/orders`

**请求参数**

| 参数        | 类型   |  是否必须   |  说明   |
| :--------:   | :-----:  |  :-----:  |  :-----:  |
| api_key         | string   |  √   |  您的api key   |
| req_time          | string   |  √   |  请求时间戳   |
| market          | string   |  √   |  交易对   |
| trade_type            | string   |  √   |  交易类型，1/2 (买/卖)   |
| page_num          | integer   |  √   |  页数   |
| page_size             | integer   |  √   |  每页大小   |

**返回值**

{'code': 200, 'data': [{'id': 'd66cce2e-017e-4066-a379-cf080e0d8aae', 'market': 'EOS_ETH', 'price': '0.025100000000000000', 'status': '1', 'totalQuantity': '99.999900000000000000', 'tradedQuantity': '0.000000000000000000', 'tradedAmount': '0.000000000000000000', 'createTime': '2019-02-26 18:21:13.0', 'type': 1}, ...], 'msg': 'OK'}


**返回值说明**

| 返回值        |  说明   |
| :--------:   | :-----:  |
| id        |  订单id   |
| market        |  交易对   |
| price        |  成交价格   |
| status        |  订单状态，1:未成交 2:已成交 3:部分成交 4:已撤单 5:部分撤单   |
| totalQuantity        |  订单总量   |
| createTime        |  订单时间   |
| type        |  交易类型：1/2 (买/卖)   |

**示例**


[python](#查询账号历史成交-python-demo)

----

> #### 接口示例

* 公共

```python
import requests

ROOT_URL = 'https://www.mxc.com'

headers = {
    'Content-Type': 'application/json',
    'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36',
    "Accept": "application/json",
}
```

> ###### 获取市场列表信息 python demo

```python
url = ROOT_URL + '/open/api/v1/data/markets'
response = requests.request('GET', url, headers=headers)
print(response.json())
```

> ###### 获取交易对信息 python demo

```python
url = ROOT_URL + '/open/api/v1/data/markets_info'
response = requests.request('GET', url, headers=headers)
print(response.json())
```

> ###### 获取深度信息 python demo

```python
symbol = 'BTC_USDT'
depth = 30
params = {'market': symbol,
          'depth': depth}
url = ROOT_URL + '/open/api/v1/data/depth'
response = requests.request('GET', url, params=params, headers=headers)
print(response.json())
```

> ###### 获取单个币种成交记录信息 python demo

```python
symbol = 'BTC_USDT'
params = {'market': symbol}
url = ROOT_URL + '/open/api/v1/data/history'
response = requests.request('GET', url, params=params, headers=headers)
print(response.json())
```

> ###### 获取市场行情信息 python demo

```python
symbol = 'BTC_USDT'
params = {'market': symbol}
url = ROOT_URL + '/open/api/v1/data/ticker'
response = requests.request('GET', url, params=params, headers=headers)
print(response.json())
```

* 私有

```python
import time
import hashlib

API_KEY = 'your api key'
SECRET_KEY = 'your secret key'

def sign(params):
    sign = ''
    for key in sorted(params.keys()):
        sign += key + '=' + str(params[key]) + '&'
    response_data = sign + 'api_secret=' + SECRET_KEY
    return hashlib.md5(response_data.encode("utf8")).hexdigest()
```

> ###### 获取账户资产信息 python demo

```python
url = ROOT_URL + '/open/api/v1/private/account/info'
params = {'api_key': API_KEY,
          'req_time': time.time()}
params.update({'sign': sign(params)})
response = requests.request('GET', url, params=params, headers=headers)
print(response.json())
```

> ###### 获取当前委托信息 python demo

```python
symbol = 'BTC_USDT'
trade_type = 0
params = {'api_key': API_KEY,
          'req_time': time.time(),
          'market': symbol,
          'trade_type': trade_type,  # 交易类型，0/1/2 (所有/买/卖)
          'page_num': 1,
          'page_size': 50}
params.update({'sign': sign(params)})
url = ROOT_URL + '/open/api/v1/private/current/orders'
response = requests.request('GET', url, params=params, headers=headers)
print(response.json())
```

> ###### 下单 python demo

```python
symbol = 'BTC_USDT'
price = 9999
quantity = 66
trade_type = 1  # 1/2 (买/卖)
params = {'api_key': API_KEY,
          'req_time': time.time(),
          'market': symbol,
          'price': price,
          'quantity': quantity,
          'trade_type': trade_type}
params.update({'sign': sign(params)})
url = ROOT_URL + '/open/api/v1/private/order'
response = requests.request('POST', url, params=params, headers=headers)
print(response.json())
```

> ###### 取消订单 python demo

```python
symbol = 'BTC_USDT'
order_id = '3cd4bd41-****-****-****-d593f8eea202'
params = {'api_key': API_KEY,
          'req_time': time.time(),
          'market': symbol,
          'trade_no': order_id}
params.update({'sign': sign(params)})
url = ROOT_URL + '/open/api/v1/private/order'
response = requests.request('DELETE', url, params=params, headers=headers)
print(response.json())
```

> ###### 查询账号历史成交 python demo

```python
symbol = 'EOS_ETH'
deal_type = 1
params = {'api_key': API_KEY,
          'req_time': time.time(),
          'market': symbol,
          'trade_type': deal_type,
          'page_num': 1,
          'page_size': 70}
params.update({'sign': sign(params)})
url = ROOT_URL + '/open/api/v1/private/orders'
response = requests.request('GET', url, params=params)
print(response.json())
```
