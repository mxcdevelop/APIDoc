#coding: utf-8
# @Author : shixiao
# @time   : 2018/7/18 14:23
# @File   : mxc.py
# @Software ： PyCharm

import time
import hashlib
import json
import urllib
import requests

MXC_URL = 'https://www.mxc.com'

def get_symbols():
    """
    获取交易对
    :return:
    """
    url = MXC_URL + '/open/api/v1/data/markets'
    response_data = http_get_request(url, {})
    return response_data

def get_symbols_info():
    """
    获取精度
    :return:
    """
    url = MXC_URL + '/open/api/v1/data/markets_info'
    response_data = http_get_request(url,{})
    return response_data

def get_depth(symbol, depth):
    """
    盘口列表
    :param symbol 交易对
    :param depth: 深度 必填：{ 10, 1, 2, 3, 4, 5 }
    :return:
    """
    depth = 10 if depth == 0 else depth
    params = {'market': symbol,
              'depth': depth}
    url = MXC_URL + '/open/api/v1/data/depth'
    response_data = http_get_request(url, params)
    return response_data

def get_detail(symbol_str):
    """
    历史成交记录
    :param symbol_str 交易对
    :return:
    """
    params = {'market': symbol_str}

    url = MXC_URL + '/open/api/v1/data/history'
    response_data = http_get_request(url, params)
    return response_data

def get_ticker(symbol_str):
    """

    :param symbol_str: 交易对
    :return:
    """
    params = {'market': symbol_str}

    url = MXC_URL + '/open/api/v1/data/ticker'
    response_data = http_get_request(url, params)
    return response_data


def send_order(symbol_str, price, count, trade_type, access_key, secret_key):
    """
    下单
    :param symbols: key
    :param symbol_str: 交易对
    :param price: 价格
    :param count: 数量
    :param trade_type: 交易类型：1/2 (买/卖)
    :param kwargs: 是否不显示在盘口中
    :return:
    """
    url = MXC_URL + '/open/api/v1/private/order'

    params = dict()
    params['api_key'] = access_key
    params['req_time'] = int(time.time())
    params['market'] = symbol_str
    params['price'] = price
    params['quantity'] = count
    params['trade_type'] = trade_type
    params['sign'] = build_mxc_sign(params, secret_key)
    response_data = http_post_request(url, params)
    return response_data


def cancal_order(symbol_str, trade_no, access_key, secret_key):
    """
    取消委托单
    :param symbols: key
    :param symbol_str: 交易对
    :param trade_no: 单号
    :return:
    """
    url = MXC_URL + '/open/api/v1/private/order'
    params = dict()
    params['api_key'] = access_key
    params['req_time'] = int(time.time())
    params['market'] = symbol_str
    params['trade_no'] = trade_no
    params['sign'] = build_mxc_sign(params, secret_key)
    response_data = http_delete_request(url, params)
    return response_data

def get_userinfo(access_key, secret_key):
    """
    获取用户现货账户信息
    :return:
    """
    url = MXC_URL + "/open/api/v1/private/account/info"
    params = dict()
    params['api_key'] = access_key
    params['req_time'] = int(time.time())
    params['sign'] = build_mxc_sign(params, secret_key)
    response_data = http_get_request(url, params)
    return response_data

def get_current_order(symbol_str, deal_type, access_key, secret_key):
    """
    获取当前委托单
    :param symbols: key
    :param symbol_str: 交易对
    :param deal_type: 交易类型，0/1/2 (所有/买/卖)
    :return:
    """
    url = MXC_URL + '/open/api/v1/private/current/orders'
    params = dict()
    params['api_key'] = access_key
    params['req_time'] = int(time.time())
    params['market'] = symbol_str
    params['trade_type'] = deal_type
    params['page_num'] = 1
    params['page_size'] = 70
    params['sign'] = build_mxc_sign(params, secret_key)
    response_data = http_get_request(url, params)
    return response_data

def get_finish_order(symbol_str, deal_type, access_key, secret_key):
    """
    获取成交订单
    :param symbols: key
    :param symbol_str: 交易对
    :param deal_type: 交易类型，1/2 (买/卖)
    :return:
    """
    url = MXC_URL + '/open/api/v1/private/orders'
    params = dict()
    params['api_key'] = access_key
    params['req_time'] = int(time.time())
    params['market'] = symbol_str
    params['trade_type'] = deal_type
    params['page_num'] = 1
    params['page_size'] = 70
    params['sign'] = build_mxc_sign(params, secret_key)
    response_data = http_get_request(url, params)
    return response_data

def build_mxc_sign(params,secret_key):
    """
    MXC加签方法
    :param params:
    :param secret_key:
    :return:
    """
    sign = ''
    for key in sorted(params.keys()):
        sign += key + '=' + str(params[key]) +'&'
    response_data = sign + 'api_secret=' + secret_key
    return hashlib.md5(response_data.encode("utf8")).hexdigest()


def http_get_request(url, params):
    post_data = urllib.parse.urlencode(params)
    return http_request('GET',url,post_data)

def http_post_request(url, params):
    return http_request('POST',url, params)

def http_delete_request(url, params):
    return http_request('DELETE',url,params)

def http_request(method_type, url, params, data=None, add_to_headers=None):
    headers = {
        'Content-Type': 'application/json',
        'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36',
        "Accept": "application/json",
    }
    if add_to_headers:
        headers.update(add_to_headers)
    try:
        response = requests.request(method_type, url=url, params=params, data=data, headers=headers, timeout=45)
        if response.status_code == 200:
            return response.json()
        else:
            print(response.text)
            return
    except BaseException as e:
        print("%s , %s" % (url, e))
        return
