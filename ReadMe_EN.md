# MXC API

- - - - - -

## API Introduction

Welcome to the MXC API, which includes [public] and [non-public] by category. You can use public interface to access all market data, and trading via private interface.

To prevent the request(s) from being tempered in the process of network transmission, signature authentication is required for your API Key for the private interface, which guarantees that you are the source of the request(s). A legal MXC signature consists of parameters connected by “&” in alphabetical order, and your api_secret, through MD5 method. 
The signature needed to be placed in the parameter sign.

>To take `/open/api/v1/private/account/info` as an example, three parameters are required to be uploaded, including `api_key`,`req_time` and `sign`, all are involved in signature expect for`sign`.
>
>If your `api_key` is `mmmyyyaaapppiiikkkeeeyyy`, and `api_secret` is `ssseeecccrrreeettt`, the current ten-digit timestamp is `1234567890`.
>
>First sort the parameters that need to be signed based on the parameter name (Compare the first letter of all parameter names. Alphabetically, if the first letters are same, then the second letter, and so on.) Therefore, the first parameter is `api_key`，the second is `req_time`.
>
>Connecting the parameter and parameter value with `=` results: `api_key=mmmyyyaaapppiiikkkeeeyyy` and `req_time=1234567890`
>
>Connecting the results with `&` and get: `api_key=mmmyyyaaapppiiikkkeeeyyy&req_time=1234567890`
>
>Finally connecting `&api_secret=ssseeecccrrreeettt` results: `api_key=mmmyyyaaapppiiikkkeeeyyy&req_time=1234567890&api_secret=ssseeecccrrreeettt`
>
>Signing the obtained string with the MD5 digest algorithm results: `c90172772df116dd658141e853185517`.Place this result into “sign” parameter.
>
>Put `api_key`，`req_time` and `sign` into request and submit the request.

Notice: 

* Please add the request header to the public interface, otherwise it will return 403

* POST request please put the parameters into params, put the body will return 401

----

API HOST: **https://www.mxc.com**

Public Interface：

* GET [/open/api/v1/data/markets](#obtain-market-list-information) Obtain market list information
* GET [/open/api/v1/data/markets_info](#obtain-trading-pair-information) Obtain Trading Pair Information
* GET [/open/api/v1/data/depth](#obtain-depth-information) Obtain depth information
* GET [/open/api/v1/data/history](#obtain-transaction-history-of-a-type-of-token) Obtain transaction history of a type of token
* GET [/open/api/v1/data/ticker](#obtain-market-information) Obtain market information
* GET [/open/api/v1/data/kline](#obtain-marketing-k-line-information) Obtain marketing K-line information

私有接口：

* GET [/open/api/v1/private/account/info](#obtain-account-assets-information) Obtain account assets information
* GET [/open/api/v1/private/current/orders](#obtain-current-orders-information) Obtain current orders information
* POST [/open/api/v1/private/order](#place-order) Place order
* DELETE [/open/api/v1/private/order](#cancel-order) Cancel order
* GET [/open/api/v1/private/orders](#query-history-transactions) Query history transactions
* GET [/open/api/v1/private/order](#check-the-status-of-order) Check the status of order

---

## Interface Description

The python directory in the codebase is sample codes for Python, Jave directory is sample codes for Java.


## **Obtain market list information**

* GET `/open/api/v1/data/markets`

**Request Parameters**

| Parameter    | Type   | Required or Not|  Description |
| :--------:   | :-----:  |  :-----:  |  :-----:  |

**Returned value**

```json
{
    "code": 200,
    "data": [
        "btc_usdt",
        "eth_usdt"
    ],
    "msg": "OK"
}
```

**Returned value explanation**

| Returned value    |  Description|
| :--------:   | :-----:  |
| data        |  An array containing all the transaction pairs  |

----

## **Obtain Trading Pair Information**

* GET `/open/api/v1/data/markets_info`

**Request parameter**

| Parameter    | Type   | Required or Not|  Description |
| :--------:   | :-----:  |  :-----:  |  :-----:  |

**Returned value**

```json
{
    "code": 200,
    "data": {
        "ETC_BTC": {
            "priceScale": 6,
            "quantityScale": 2,
            "minAmount": 0.0001,
            "buyFeeRate": 0.002,
            "sellFeeRate": 0.002
        },
        "BTC_USDT": {
            "priceScale": 2,
            "quantityScale": 6,
            "minAmount": 0.1,
            "buyFeeRate": 0.002,
            "sellFeeRate": 0.002
        }
    },
    "msg": "OK"
}
```

**Returned value explanation**

| Returned value    |  Description|
| :--------:   | :-----:  |
| priceScale        |  price precision   |
| quantityScale        |  Quantity precision   |
| minAmount        |  minimum   |
| buyFeeRate        |  buy fee rate   |
| sellFeeRate        |  Sell fee rate   |

----

## **Obtain Depth Information**

* GET `/open/api/v1/data/depth`

**Request Parameters**

| Parameter    | Type   | Required or Not|  Description |
| :--------:   | :-----:  |  :-----:  |  :-----:  |
| depth        | integer   |  √   |  the number of returned depths   |
| market        | string   |  √   |  trading pair   |

**Returned value**

```json
{
    "code": 200,
    "data": {
        "asks": [
            {
                "price": "7061.82",
                "quantity": "2.759119"
            },
            {
                "price": "7062.4",
                "quantity": "0.01764"
            }
        ],
        "bids": [
            {
                "price": "7061.8",
                "quantity": "0.160269"
            },
            {
                "price": "7059.68",
                "quantity": "0.26862"
            }
        ]
    },
    "msg": "OK"
}
```

**Returned value explanation**

| Returned value    |  Description|
| :--------:   | :-----:  |
| price        |  price   |
| quantity        |  quantity   |

----

## **Obtain transaction history of a type of token**

* GET `/open/api/v1/data/history`

**Request Parameters**

| Parameter    | Type   | Required or Not|  Description |
| :--------:   | :-----:  |  :-----:  |  :-----:  |
| market        | string   |  √   |  trading pair   |

**Returned value**

```json
{
    "code": 200,
    "data": [
        {
            "tradeTime": "2019-05-13 14:12:58.787",
            "tradePrice": "7051.04",
            "tradeQuantity": "0.0189",
            "tradeType": "1"
        },
        {
            "tradeTime": "2019-05-13 14:12:58.494",
            "tradePrice": "7051.04",
            "tradeQuantity": "0.023551",
            "tradeType": "1"
        }
    ],
    "msg": "OK"
}
```

**Returned value explanation**

| Returned value    |  Description|
| :--------:   | :-----:  |
| tradeTime        |  Trading time   |
| tradePrice        |  Price   |
| tradeQuantity        |  volume   |
| tradeType        |  type1/2 (Buy/Sell)   |

----

## **Obtain market information**

* GET `/open/api/v1/data/ticker`

**Request Parameters**

| Parameter    | Type   | Required or Not|  Description |
| :--------:   | :-----:  |  :-----:  |  :-----:  |
| market        | string   |  ×   |  Trading pair   |

**Return Value**

```json
{
    "code": 200,
    "data": {
        "volume": "29821.449121",
        "high": "7512.22",
        "low": "6791.23",
        "buy": "7054.5",
        "sell": "7054.95",
        "open": "7304.1",
        "last": "7054.46"
    },
    "msg": "OK"
}
```

**Returned value explanation**

| Returned value    |  Description|
| :--------:   | :-----:  |
| volume        |  24h Volume   |
| high        |  24H highest   |
| low        |  24H lowest   |
| buy        |  bid price   |
| sell        |  ask price   |
| open        |  open price   |
| last        |  last price   |

----

## **Obtain marketing K-line information**

* GET `/open/api/v1/data/kline`

**Request Parameters**

| Parameter    | Type   | Required or Not|  Description |
| :--------:   | :-----:  |  :-----:  |  :-----:  |
| market       | string   |  √   |  Trading pair   |
| interval     | string   |  √   |  Interval (Min: 1m，5m，15m，30m，60m. Hr: 1h. Day: 1d. Month: 1M)|
| startTime    | long     |  √   |  Start time (by second, milliseconds / 1000) |
| limit        | long     |  ×   |  The number of returns |

**Return Value**

```json
{
    "code": 200,
    "data": [
        [
            1557728040,
            "7054.7",
            "7056.26",
            "7056.29",
            "7054.16",
            "9.817734",
            "69264.52975125"
        ],
        [
            1557728100,
            "7056.26",
            "7042.17",
            "7056.98",
            "7042.16",
            "23.694823",
            "167007.92840231"
        ],
        [
            1557728160,
            "7042.95",
            "7037.11",
            "7043.27",
            "7036.53",
            "22.510102",
            "158461.98283462"
        ]
    ],
    "msg": "OK"
}
```

**Returned value explanation**

| Returned value    |  Description|
| :--------:  | :-----:  |
| time        |  Start time (by second, milliseconds / 1000)  |
| open        |  Open price   |
| close       |  Close price   |
| high        |  Highest price   |
| low         |  Lowest price   |
| vol         |  volume   |
| amount      |  The trading volume in pricing currency   |

----

## **Obtain account assets information**

* GET `/open/api/v1/private/account/info`

**Request Parameters**

| Parameter    | Type   | Required or Not|  Description |
| :--------:   | :-----:  |  :-----:  |  :-----:  |
| api_key         | string   |  √   |  api key   |
| req_time          | string   |  √   |  timestamp   |
| sign          | string   |  √   |  signature   |

**Return Value**

```json
{
    "BTC": {
        "frozen": "0",
        "available": "130440.28790112"
    },
    "ETH": {
        "frozen": "27.6511928",
        "available": "12399653.86856669"
    }
}
```

**Returned value explanation**

| Returned value    |  Description|
| :--------:   | :-----:  |
| frozen        |  Frozen amount   |
| available        |  Available amount   |

----

## **Obtain current orders information**

* GET `/open/api/v1/private/current/orders`

**Request Parameters**

| Parameter    | Type   | Required or Not|  Description |
| :--------:   | :-----:  |  :-----:  |  :-----:  |
| api_key         | string   |  √   |  api key   |
| market          | string   |  √   |  Trading pair   |
| page_num           | integer   |  √   |  pages   |
| page_size           | integer   |  √   |  The size of each page   |
| req_time            | string   |  √   |  timestamp   |
| trade_type            | integer   |  √   |  Trade type, 0/1/2 (All/Buy/Sell)   |
| sign          | string   |  √   |  signature   |

**Return Value**

```json
{
    "code": 200,
    "data": [
        {
            "id": "4921e6be-cfb9-4058-89d3-afbeb6be7d78",
            "market": "MX_ETH",
            "price": "0.439961",
            "status": "1",
            "totalQuantity": "2",
            "tradedQuantity": "0",
            "tradedAmount": "0",
            "createTime": "2019-05-13 14:31:11",
            "type": 1
        },
        {
            "id": "6170091f-c977-49bf-baa8-b643c70452c7",
            "market": "MX_ETH",
            "price": "0.4399605",
            "status": "1",
            "totalQuantity": "1",
            "tradedQuantity": "0",
            "tradedAmount": "0",
            "createTime": "2019-05-13 14:30:51",
            "type": 1
        }
    ],
    "msg": "OK"
}
```

**Returned value explanation**

| Returned value    |  Description|
| :--------:   | :-----:  |
| id        |  Order id   |
| market        |  Trading pair   |
| price        |  price   |
| status        |  Order status:1.Unfilled 2.Filled  3.Partial filled 4.Cancelled order 5.Partially cancelled   |
| totalQuantity        |  total quantity   |
| tradedQuantity        |  Filled volume   |
| tradedAmount        |  Filled volume (pricing token)   |
| createTime        |  Order creation time   |
| type        |  Order type 1/2(Buy/Sell)   |

----

## **Place Order**

* POST `/open/api/v1/private/order`

**Request Parameters**

| Parameter    | Type   | Required or Not|  Description |
| :--------:   | :-----:  |  :-----:  |  :-----:  |
| api_key         | string   |  √   |  api key   |
| market          | string   |  √   |  Trading pair   |
| price            | string   |  √   |  price   |
| quantity            | string   |  √   |  quantity   |
| req_time            | string   |  √   |  timestamp   |
| trade_type            | integer   |  √   |  Trade type, 0/1/2 (All/Buy/Sell)   |
| sign          | string   |  √   |  signature   |

**Return Value**

```json
{
    "code": 200,
    "data": "de5a6819-5456-45da-9e51-ee258dd34422",
    "msg": "OK"
}
```

**Returned value explanation**

| Returned value    |  Description|
| :--------:   | :-----:  |
| data        |  Order id   |

----

## **Cancel Order**

* DELETE `/open/api/v1/private/order`

**Request Parameters**

| Parameter    | Type   | Required or Not|  Description |
| :--------:   | :-----:  |  :-----:  |  :-----:  |
| api_key         | string   |  √   |  api key   |
| market          | string   |  √   |  Trading pair   |
| req_time            | string   |  √   |  timestamp   |
| trade_no             | string   |  √   |  order id   |
| sign          | string   |  √   |  signature   |

**Return Value**

```json
{
    "code": 200,
    "data": null,
    "msg": "OK"
}
```

**Returned value explanation**

| Returned value    |  Description|
| :--------:   | :-----:  |

----

## **Query history transactions**

* GET `/open/api/v1/private/orders`

**Request Parameters**

| Parameter    | Type   | Required or Not|  Description |
| :--------:   | :-----:  |  :-----:  |  :-----:  |
| api_key         | string   |  √   |  api key   |
| req_time          | string   |  √   |  timestamp   |
| market          | string   |  √   |  Trading pair   |
| trade_type            | string   |  √   |  Trade type, 0/1/2 (All/Buy/Sell)   |
| page_num          | integer   |  √   |  pages   |
| page_size             | integer   |  √   |  The size of each page   |
| sign          | string   |  √   |  signature   |

**Return Value**

```json
{
    "code": 200,
    "data": [
        {
            "id": "f5718b8a-8f93-4880-8e95-281fe28efb91",
            "market": "OMG_ETH",
            "price": "0.011546000000000000",
            "status": "2",
            "totalQuantity": "46.520000000000000000",
            "tradedQuantity": "46.520000000000000000",
            "tradedAmount": "0.537119920000000000",
            "createTime": "2019-04-26 16:37:47.0",
            "type": 1
        },
        {
            "id": "845fdde0-6837-4d56-af8c-e43d72495cc1",
            "market": "OMG_ETH",
            "price": "0.011543000000000000",
            "status": "2",
            "totalQuantity": "7.920000000000000000",
            "tradedQuantity": "7.920000000000000000",
            "tradedAmount": "0.091420560000000000",
            "createTime": "2019-04-26 11:05:42.0",
            "type": 1
        }
    ],
    "msg": "OK"
}
```

**Returned value explanation**

| Returned value    |  Description|
| :--------:   | :-----:  |
| id        |  Order id   |
| market        |  Trading pair   |
| price        |  price   |
| status        |  Order status:1.Unfilled 2.Filled  3.Partial filled 4.Cancelled order 5.Partially cancelled   |
| totalQuantity        |  total quantity   |
| createTime        |  Order creation time   |
| type        |  Order type 1/2(Buy/Sell)   |

----

## **Check the status of order**

* GET `/open/api/v1/private/order`

**Request Parameters**

| Parameter    | Type   | Required or Not|  Description |
| :--------:   | :-----:  |  :-----:  |  :-----:  |
| api_key         | string   |  √   |  api key   |
| req_time          | string   |  √   |  timestamp   |
| market          | string   |  √   |  Trading pair   |
| trade_no            | string   |  √   |  Order id, if there are more than one, separate them with commas, and query up to 20 at a time.   |
| sign          | string   |  √   |  signature   |

**Return Value**

```json
{
    "code": 200,
    "data": {
        "id": "f5718b8a-8f93-4880-8e95-281fe28efb91",
        "market": "OMG_ETH",
        "price": "0.011546",
        "status": "2",
        "totalQuantity": "46.52",
        "tradedQuantity": "46.52",
        "tradedAmount": "0.53711992",
        "createTime": "2019-04-26 16:37:47",
        "type": 1
    },
    "msg": "OK"
}
```

**Returned value explanation**

| Returned value    |  Description|
| :--------:   | :-----:  |
| id        |  Order id   |
| market        |  Trading pair   |
| price        |  price   |
| status        |  Order status:1.Unfilled 2.Filled  3.Partial filled 4.Cancelled order 5.Partially cancelled |
| totalQuantity        |  total quantity   |
| tradedQuantity        |  Filled volume   |
| tradedAmount        |  Filled volume (pricing token)   |
| createTime        |  Order creation time   |
| type        |  Order type 1/2(Buy/Sell)   |

----

> #### demo

* Public Interface

```python
import requests

ROOT_URL = 'https://www.mxc.com'

headers = {
    'Content-Type': 'application/json',
    'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36',
    "Accept": "application/json",
}
```

> ###### Obtain market list information

```python
url = ROOT_URL + '/open/api/v1/data/markets'
response = requests.request('GET', url, headers=headers)
print(response.json())
```

> ###### Obtain Trading Symbol

```python
url = ROOT_URL + '/open/api/v1/data/markets_info'
response = requests.request('GET', url, headers=headers)
print(response.json())
```

> ###### Obtain depth information

```python
symbol = 'BTC_USDT'
depth = 30
params = {'market': symbol,
          'depth': depth}
url = ROOT_URL + '/open/api/v1/data/depth'
response = requests.request('GET', url, params=params, headers=headers)
print(response.json())
```

> ###### Obtain transaction history of a type of token

```python
symbol = 'BTC_USDT'
params = {'market': symbol}
url = ROOT_URL + '/open/api/v1/data/history'
response = requests.request('GET', url, params=params, headers=headers)
print(response.json())
```

> ###### Obtain market information

```python
symbol = 'BTC_USDT'
params = {'market': symbol}
url = ROOT_URL + '/open/api/v1/data/ticker'
response = requests.request('GET', url, params=params, headers=headers)
print(response.json())
```

> ###### Obtain marketing K-line information

```python
import time
symbol = 'BTC_USDT'
params = {'market': symbol,
          'interval': '1m',
          'startTime': int(time.time() / 60) * 60 - 60 * 5,
          'limit': 5}
url = ROOT_URL + '/open/api/v1/data/kline'
response = requests.request('GET', url, params=params, headers=headers)
print(response.json())
```

* Private Interface

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

> ###### Obtain account assets information

```python
url = ROOT_URL + '/open/api/v1/private/account/info'
params = {'api_key': API_KEY,
          'req_time': time.time()}
params.update({'sign': sign(params)})
response = requests.request('GET', url, params=params, headers=headers)
print(response.json())
```

> ###### Obtain current orders information

```python
symbol = 'BTC_USDT'
trade_type = 0
params = {'api_key': API_KEY,
          'req_time': time.time(),
          'market': symbol,
          'trade_type': trade_type,
          'page_num': 1,
          'page_size': 50}
params.update({'sign': sign(params)})
url = ROOT_URL + '/open/api/v1/private/current/orders'
response = requests.request('GET', url, params=params, headers=headers)
print(response.json())
```

> ###### Place order

```python
symbol = 'BTC_USDT'
price = 9999
quantity = 66
trade_type = 1
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

> ###### Cancel order

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

> ###### Query history transactions

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

> ###### Query order status

```python
symbol = 'EOS_ETH'
trade_no = 'f5718b8a-8f93-4880-8e95-281fe28efb91'
params = {'api_key': API_KEY,
          'req_time': time.time(),
          'market': symbol,
          'trade_no': trade_no}
params.update({'sign': sign(params)})
url = ROOT_URL + '/open/api/v1/private/order'
response = requests.request('GET', url, params=params)
print(response.json())
```
