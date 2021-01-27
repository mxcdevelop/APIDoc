import hmac
import json
import time
import urllib.parse
from requests import Session


class MxcClient(object):
    api_base = "https://contract.mxc.com"

    def __del__(self):
        if self.session:
            self.session.close()

    def __init__(self, access_key="",  secret_key="", api_base="", **kwargs):
        self.session = Session()
        self.access_key = access_key
        self.secret_key = secret_key
        if api_base:
            self.api_base = api_base.rstrip('/')
        self.proxies = kwargs.get('proxies', {})

    def sign(self, to_be_sign):
        return hmac.new(self.secret_key.encode('utf-8'), to_be_sign.encode('utf-8'), 'sha256').hexdigest()

    def mxc_get(self, endpoint, payload=None, is_private=False):
        payload = dict() if payload is None else payload
        url = f'{self.api_base}{endpoint}'
        p_str = urllib.parse.urlencode(sorted(payload.items()))
        ts = int(time.time() * 1000)
        headers = {
            'Content-Type': 'application/json',
            'ApiKey': self.access_key,
            'Request-Time': str(ts)
        }
        if is_private is True:
            headers.update({'Signature': self.sign(f'{self.access_key}{ts}{p_str}')})
        url = f'{url}?{p_str}' if p_str else url
        self.session.cookies.clear()
        resp = self.session.request('GET', url, headers=headers, timeout=5, proxies=self.proxies)
        return resp.json()

    def mxc_post(self, endpoint, payload, is_private=False):
        url = f'{self.api_base}{endpoint}'
        data = json.dumps(payload)
        ts = int(time.time() * 1000)
        headers = {
            'Content-Type': 'application/json',
            'ApiKey': self.access_key,
            'Request-Time': str(ts)
        }
        if is_private is True:
            headers.update({'Signature': self.sign(f'{self.access_key}{ts}{data}')})
        self.session.cookies.clear()
        resp = self.session.request('POST', url, data=data, headers=headers, timeout=5, proxies=self.proxies)
        return resp.json()

    # 公共接口部分

    def get_system_time(self):
        """
        获取服务器时间
        :return:
        """
        return self.mxc_get('/api/v1/contract/ping')

    def get_contract_detail(self, symbol=None):
        """
        获取合约信息
        :param symbol: 合约名
        :return:
        """
        endpoint = '/api/v1/contract/detail'
        if symbol:
            endpoint = f'{endpoint}?symbol={symbol}'
        return self.mxc_get(endpoint)

    def get_support_currencies(self):
        """
        获取可划转币种
        :return:
        """
        return self.mxc_get('/api/v1/contract/support_currencies')

    def get_contract_depth(self, symbol, limit=None):
        """
        获取合约深度信息
        :param symbol: 合约名
        :param limit: 档位数
        :return:
        """
        endpoint = f'/api/v1/contract/depth/{symbol}'
        if limit:
            return self.mxc_get(endpoint, payload={'limit': limit})
        else:
            return self.mxc_get(endpoint)

    def get_contract_depth_commits(self, symbol, limit):
        """
        获取合约最近N条深度信息快照
        :param symbol: 合约名
        :param limit: 条数
        :return:
        """
        endpoint = f'/api/v1/contract/depth_commits/{symbol}/{limit}'
        return self.mxc_get(endpoint)

    def get_contract_index_price(self, symbol):
        """
        获取合约指数价格
        :param symbol: 合约名
        :return:
        """
        endpoint = f'/api/v1/contract/index_price/{symbol}'
        return self.mxc_get(endpoint)

    def get_contract_fair_price(self, symbol):
        """
        获取合约合理价格
        :param symbol: 合约名
        :return:
        """
        endpoint = f"/api/v1/contract/fair_price/{symbol}"
        return self.mxc_get(endpoint)

    def get_contract_funding_rate(self, symbol):
        """
        获取合约资金费率
        :param symbol: 合约名
        :return:
        """
        endpoint = f"/api/v1/contract/funding_rate/{symbol}"
        return self.mxc_get(endpoint)

    def get_contract_kline(self, symbol, interval, start, end):
        """
        获取蜡烛图数据
        :param symbol: 合约名
        :param interval: 间隔: Min1、Min5、Min15、Min30、Min60、Hour4、Hour8、Day1、Week1、Month1
        :param start: 开始时间戳，单位S
        :param end: 结束时间戳，单位S
        :return:
        """
        endpoint = f"/api/v1/contract/kline/{symbol}"
        payload = {
            'interval': interval,
            'start': start,
            'end': end
        }
        return self.mxc_get(endpoint, payload)

    def get_contract_kline_index_price(self, symbol, interval, start, end):
        """
        获取指数价格蜡烛图数据
        :param symbol: 合约名
        :param interval: 间隔: Min1、Min5、Min15、Min30、Min60、Hour4、Hour8、Day1、Week1、Month1
        :param start: 开始时间戳，单位S
        :param end: 结束时间戳，单位S
        :return:
        """
        endpoint = f"/api/v1/contract/kline/index_price/{symbol}"
        payload = {
            'interval': interval,
            'start': start,
            'end': end
        }
        return self.mxc_get(endpoint, payload)

    def get_contract_kline_fair_price(self, symbol, interval, start, end):
        """
        获取指数价格蜡烛图数据
        :param symbol: 合约名
        :param interval: 间隔: Min1、Min5、Min15、Min30、Min60、Hour4、Hour8、Day1、Week1、Month1
        :param start: 开始时间戳，单位S
        :param end: 结束时间戳，单位S
        :return:
        """
        endpoint = f"/api/v1/contract/kline/fair_price/{symbol}"
        payload = {
            'interval': interval,
            'start': start,
            'end': end
        }
        return self.mxc_get(endpoint, payload)

    def get_contract_deals(self, symbol, limit=None):
        """
        获取成交数据
        :param symbol: 合约名
        :param limit: 结果集数量，最大为100，不填默认返回100条
        :return:
        """
        endpoint = f"/api/v1/contract/deals/{symbol}"
        if limit:
            return self.mxc_get(endpoint, payload={'limit': limit})
        else:
            return self.mxc_get(endpoint)

    def get_contract_ticker(self, symbol=None):
        """
        获取合约行情数据
        :param symbol: 合约名
        :return:
        """
        endpoint = f"/api/v1/contract/ticker"
        if symbol:
            return self.mxc_get(endpoint, payload={'symbol': symbol})
        else:
            return self.mxc_get(endpoint)

    def get_contract_risk_reserve(self):
        """
        获取所有合约风险基金余额
        :return:
        """
        endpoint = f"/api/v1/contract/risk_reverse"
        return self.mxc_get(endpoint)

    def get_contract_risk_reserve_history(self, symbol, page_num=1, page_size=20):
        """
        获取合约风险基金余额历史
        :param symbol: 合约名
        :param page_num: 当前页数,默认为1
        :param page_size: 每页大小,默认20,最大100
        :return:
        """
        endpoint = f"/api/v1/contract/risk_reverse/history"
        payload = {
            'symbol': symbol,
            'page_num': page_num,
            'page_size': page_size
        }
        return self.mxc_get(endpoint, payload)

    def get_contract_funding_rate_history(self, symbol, page_num=1, page_size=20):
        """
        获取合约资金费率历史
        :param symbol: 合约名
        :param page_num: 当前页数,默认为1
        :param page_size: 每页大小,默认20,最大100
        :return:
        """
        endpoint = f"/api/v1/contract/funding_rate/history"
        payload = {
            'symbol': symbol,
            'page_num': page_num,
            'page_size': page_size
        }
        return self.mxc_get(endpoint, payload)

    # 私有接口部分

    def get_account_assets(self):
        """
        获取用户所有资产信息
        :return:
        """
        endpoint = f"/api/v1/private/account/assets"
        return self.mxc_get(endpoint, is_private=True)

    def get_account_asset(self, currency):
        """
        获取用户单个币种资产信息
        :param currency: 币种
        :return:
        """
        endpoint = f"/api/v1/private/account/asset/{currency}"
        return self.mxc_get(endpoint, is_private=True)

    def get_account_transfer_record(self, currency=None, state=None, type_=None, page_num=1, page_size=20):
        """
        获取用户资产划转记录
        :param currency: 币种
        :param state: 状态:WAIT 、SUCCESS 、FAILED
        :param type_: 类型:IN 、OUT
        :param page_num: 当前页数,默认为1
        :param page_size: 每页大小,默认20,最大100
        :return:
        """
        endpoint = f"/api/v1/private/account/transfer_record"
        payload = {
            'currency': currency,
            'state': state,
            'type': type_,
            'page_num': page_num,
            'page_size': page_size
        }
        payload = {k: v for k, v in payload.items() if v is not None}
        return self.mxc_get(endpoint, payload, is_private=True)

    def get_history_positions(self, symbol=None, type_=None, page_num=1, page_size=20):
        """
        获取用户历史持仓信息
        :param symbol: 合约名
        :param type_: 仓位类型， 1多 2空
        :param page_num: 当前页数,默认为1
        :param page_size: 每页大小,默认20,最大100
        :return:
        """
        endpoint = f"/api/v1/private/position/history_positions"
        payload = {
            'symbol': symbol,
            'type': type_,
            'page_num': page_num,
            'page_size': page_size
        }
        payload = {k: v for k, v in payload.items() if v is not None}
        return self.mxc_get(endpoint, payload, is_private=True)

    def get_open_positions(self, symbol=None):
        """
        获取用户当前持仓
        :param symbol: 合约名
        :return:
        """
        endpoint = f"/api/v1/private/position/open_positions"
        payload = {
            'symbol': symbol,
        }
        payload = {k: v for k, v in payload.items() if v is not None}
        return self.mxc_get(endpoint, payload, is_private=True)

    def get_funding_records(self, symbol=None, position_id=None, page_num=1, page_size=20):
        """
        获取用户资金费用明细
        :param symbol: 合约名
        :param position_id: 仓位id
        :param page_num: 当前页数,默认为1
        :param page_size: 每页大小,默认20,最大100
        :return:
        """
        endpoint = f"/api/v1/private/position/funding_records"
        payload = {
            'symbol': symbol,
            'position_id': position_id,
            'page_num': page_num,
            'page_size': page_size
        }
        payload = {k: v for k, v in payload.items() if v is not None}
        return self.mxc_get(endpoint, payload, is_private=True)

    def get_open_orders(self, symbol=None, page_num=1, page_size=20):
        """
        获取用户当前未结束订单
        :param symbol: 合约名
        :param page_num: 当前页数,默认为1
        :param page_size: 每页大小,默认20,最大100
        :return:
        """
        endpoint = f"/api/v1/private/order/open_orders"
        if symbol:
            endpoint = f'{endpoint}/{symbol}'
        payload = {
            'page_num': page_num,
            'page_size': page_size
        }
        return self.mxc_get(endpoint, payload, is_private=True)

    def get_history_orders(self, symbol=None, states=None, category=None, start_time=None,
                           end_time=None, side=None, page_num=1, page_size=20):
        """
        获取用户所有历史订单
        :param symbol: 合约名
        :param states: 订单状态,1:待报,2未完成,3已完成,4已撤销,5无效;多个用 ',' 隔开
        :param category: 订单类别,1:限价委托,2:强平代管委托,4:ADL减仓
        :param start_time: 开始时间，开始时间和结束时间的跨度一次最大只能查90天，不传时间默认返回最近7天的数据
        :param end_time: 结束时间，开始时间和结束时间的跨度一次最大只能查90天
        :param side: 订单方向 1开多,2平空,3开空,4平多
        :param page_num: 当前页数,默认为1
        :param page_size: 每页大小,默认20,最大100
        :return:
        """
        endpoint = f"/api/v1/private/order/history_orders"
        payload = {
            'symbol': symbol,
            'states': states,
            'category': category,
            'start_time': start_time,
            'end_time': end_time,
            'side': side,
            'page_num': page_num,
            'page_size': page_size
        }
        payload = {k: v for k, v in payload.items() if v is not None}
        return self.mxc_get(endpoint, payload, is_private=True)

    def get_order_by_external_oid(self, symbol, external_oid):
        """
        根据外部号查询订单
        :param symbol: 合约名
        :param external_oid: 外部订单号
        :return:
        """
        endpoint = f"/api/v1/private/order/external/{symbol}/{external_oid}"
        return self.mxc_get(endpoint, is_private=True)

    def get_order_by_order_id(self, order_id):
        """
        根据订单号查询订单
        :param order_id: 订单号
        :return:
        """
        endpoint = f"/api/v1/private/order/get/{order_id}"
        return self.mxc_get(endpoint, is_private=True)

    def batch_query_by_order_ids(self, order_ids):
        """
        根据订单号批量查询订单
        :param order_ids: 订单号数组，可使用逗号隔开例如:order_ids = 1,2,3(最大50个订单):
        :return:
        """
        endpoint = f"/api/v1/private/order/batch_query"
        payload = {
            'order_ids': ','.join(order_ids) if isinstance(order_ids, list) else order_ids
        }
        return self.mxc_get(endpoint, payload, is_private=True)

    def get_deal_detail_by_id(self, order_id):
        """
        根据订单号获取订单成交明细
        :param order_id: 订单ID
        :return:
        """
        endpoint = f"/api/v1/private/order/deal_details/{order_id}"
        return self.mxc_get(endpoint, is_private=True)

    def get_order_deals(self, symbol=None, start_time=None, end_time=None, page_num=1, page_size=20):
        """
        获取用户所有订单成交明细
        :param symbol: 合约名
        :param start_time: 开始时间，不传默认为向前推7天的时间，传了时间，最大跨度为90天
        :param end_time: 结束时间，开始和结束时间的跨度为90天
        :param page_num: 当前页数,默认为1
        :param page_size: 每页大小,默认20,最大100
        :return:
        """
        endpoint = f"/api/v1/private/order/order_deals"
        payload = {
            'symbol': symbol,
            'start_time': start_time,
            'end_time': end_time,
            'page_num': page_num,
            'page_size': page_size
        }
        payload = {k: v for k, v in payload.items() if v is not None}
        return self.mxc_get(endpoint, payload, is_private=True)

    def get_plan_orders(self, symbol=None, states=None, page_num=1, page_size=20):
        """
        获取计划委托订单列表
        :param symbol: 合约名
        :param states: 状态,1:未触发,2:已取消,3:已执行,4:已失效,5:执行失败;多个用 ',' 隔开
        :param page_num: 当前页数,默认为1
        :param page_size: 每页大小,默认20,最大100
        :return:
        """
        endpoint = f"/api/v1/private/planorder/orders"
        payload = {
            'symbol': symbol,
            'states': states,
            'page_num': page_num,
            'page_size': page_size
        }
        payload = {k: v for k, v in payload.items() if v is not None}
        return self.mxc_get(endpoint, payload, is_private=True)

    def get_stop_orders(self, symbol=None, is_finished=None, page_num=1, page_size=20):
        """
        获取止盈止损订单列表
        :param symbol: 合约名
        :param is_finished: 终态标识:0:未完成，1:已完成
        :param page_num: 当前页数, 默认为1
        :param page_size: 每页大小, 默认20, 最大100
        :return:
        """
        endpoint = f"/api/v1/private/stoporder/orders"
        payload = {
            'symbol': symbol,
            'is_finished': is_finished,
            'page_num': page_num,
            'page_size': page_size
        }
        payload = {k: v for k, v in payload.items() if v is not None}
        return self.mxc_get(endpoint, payload, is_private=True)

    def get_stop_order_details(self, stop_order_id):
        """
        获取止盈止损订单执行明细
        :param stop_order_id: 止盈止损订单id
        :return:
        """
        endpoint = f"/api/v1/private/stoporder/order_details/{stop_order_id}"
        return self.mxc_get(endpoint, is_private=True)

    def get_account_risk_limit(self, symbol=None):
        """
        获取风险限额
        :param symbol: 合约名
        :return:
        """
        endpoint = f"/api/v1/private/account/risk_limit"
        payload = {
            'symbol': symbol
        }
        payload = {k: v for k, v in payload.items() if v is not None}
        return self.mxc_get(endpoint, payload, is_private=True)

    def get_tiered_fee_rate(self, symbol):
        """
        获取用户当前手续费率
        :param symbol: 合约名
        :return:
        """
        endpoint = f"/api/v1/private/account/tiered_fee_rate"
        payload = {
            'symbol': symbol
        }
        return self.mxc_get(endpoint, payload, is_private=True)

    def change_margin(self, position_id, amount, type_):
        """
        增加或减少仓位保证金
        :param position_id: 仓位id
        :param amount: 金额
        :param type_: 类型,ADD:增加,SUB:减少
        :return:
        """
        endpoint = f"/api/v1/private/position/change_margin"
        payload = {
            'positionId': position_id,
            'amount': amount,
            'type': type_
        }
        return self.mxc_post(endpoint, payload, is_private=True)

    def change_leverage(self, position_id, leverage):
        """
        修改杠杆倍数
        :param position_id: 仓位id
        :param leverage: 杠杆倍数
        :return:
        """
        endpoint = f"/api/v1/private/position/change_leverage"
        payload = {
            'positionId': position_id,
            'leverage': leverage
        }
        return self.mxc_post(endpoint, payload, is_private=True)

    def submit_order(self, symbol, price, vol, side, type_, open_type,
                     leverage=None, external_oid=None, stop_loss_price=None, take_profit_price=None):
        """
        下单
        :param symbol: 合约名
        :param price: 价格
        :param vol: 数量
        :param side: 订单方向 1开多,2平空,3开空,4平多
        :param type_: 订单类型,1:限价单,2:Post Only只做Maker,3:立即成交或立即取消,4:全部成交或者全部取消,5:市价单,6:市价转现价
        :param open_type: 开仓类型,1:逐仓,2:全仓
        :param leverage: 杠杆倍数，开仓的时候，如果是逐仓，必传，平仓和全仓不用
        :param external_oid: 外部订单号
        :param stop_loss_price: 止损价
        :param take_profit_price: 止盈价
        :return:
        """
        endpoint = f"/api/v1/private/order/submit"
        payload = {
            'symbol': symbol,
            'price': price,
            'vol': vol,
            'leverage': leverage,
            'side': side,
            'type': type_,
            'openType': open_type,
            'externalOid': external_oid,
            'stopLossPrice': stop_loss_price,
            'takeProfitPrice': take_profit_price
        }
        return self.mxc_post(endpoint, payload, is_private=True)

    def submit_batch_order(self, order_list):
        """
        批量下单
        :param order_list: 订单列表
        :return:
        """
        endpoint = f"/api/v1/private/order/submit_batch"
        return self.mxc_post(endpoint, order_list, is_private=True)

    def cancel_orders(self, order_ids):
        """
        取消订单
        :param order_ids: 订单id列表
        :return:
        """
        endpoint = f"/api/v1/private/order/cancel"
        return self.mxc_post(endpoint, order_ids, is_private=True)

    def cancel_with_external(self, symbol, external_oid):
        """
        根据外部订单号取消订单
        :param symbol: 合约名
        :param external_oid: 外部订单号
        :return:
        """
        endpoint = f"/api/v1/private/order/cancel_with_external"
        payload = {
            'symbol': symbol,
            'externalOid': external_oid
        }
        return self.mxc_post(endpoint, payload, is_private=True)

    def cancel_all_orders(self, symbol=None):
        """
        取消某合约下所有订单
        :param symbol: 合约名
        :return:
        """
        endpoint = f"/api/v1/private/order/cancel_all"
        payload = {
            'symbol': symbol
        }
        payload = {k: v for k, v in payload.items() if v is not None}
        return self.mxc_post(endpoint, payload, is_private=True)

    def change_risk_level(self, symbol, level, position_type):
        """
        修改风险等级
        :param symbol: 合约名
        :param level: 等级
        :param position_type: 1:多仓，2:空仓
        :return:
        """
        endpoint = f"/api/v1/private/account/change_risk_level"
        payload = {
            'symbol': symbol,
            'level': level,
            'position_type': position_type,
        }
        payload = {k: v for k, v in payload.items() if v is not None}
        return self.mxc_post(endpoint, payload, is_private=True)

    def submit_plan_order(self, symbol, price, vol, side, order_type, open_type, trigger_price,
                          trigger_type, execute_cycle, trend, leverage=None):
        """
        计划委托下单
        :param symbol: 合约名
        :param price: 执行价, 市价时可不传
        :param vol: 数量
        :param side: 1开多, 2平空, 3开空, 4平多
        :param order_type: 订单类型, 1:限价单, 2:Post Only只做Maker, 3:立即成交或立即取消, 4:全部成交或者全部取消, 5:市价单
        :param open_type: 订单方向 1开多, 2平空, 3开空, 4平多
        :param trigger_price: 触发价
        :param trigger_type: 触发类型, 1:大于等于，2:小于等于
        :param execute_cycle: 执行周期, 1: 24小时,2: 7天
        :param trend: 触发价格类型, 1:最新价，2:合理价，3:指数价
        :param leverage: 杠杆倍数，开仓的时候，如果是逐仓，必传，平仓和全仓不用
        :return:
        """
        endpoint = f"/api/v1/private/planorder/place"
        payload = {
            'symbol': symbol,
            'price': price,
            'vol': vol,
            'leverage': leverage,
            'side': side,
            'orderType': order_type,
            'openType': open_type,
            'triggerPrice': trigger_price,
            'triggerType': trigger_type,
            'executeCycle': execute_cycle,
            'trend': trend
        }
        return self.mxc_post(endpoint, payload, is_private=True)

    def cancel_plan_order(self, order_ids):
        """
        取消计划委托订单
        :param order_ids: 订单id列表
        :return:
        """
        endpoint = f"/api/v1/private/planorder/cancel"
        return self.mxc_post(endpoint, order_ids, is_private=True)

    def cancel_all_plan_order(self, symbol=None):
        """
        取消所有计划委托订单
        :param symbol: 合约名,传入symbol只取消该合约下的订单，不传取消所有合约下的订单
        :return:
        """
        endpoint = f"/api/v1/private/planorder/cancel_all"
        payload = {
            'symbol': symbol
        }
        payload = {k: v for k, v in payload.items() if v is not None}
        return self.mxc_post(endpoint, payload, is_private=True)

    def cancel_stop_orders(self, order_ids):
        """
        取消止盈止损委托单
        :param order_ids: 订单id列表
        :return:
        """
        endpoint = f"/api/v1/private/stoporder/cancel"
        return self.mxc_post(endpoint, order_ids, is_private=True)

    def cancel_all_stop_orders(self, position_id=None, symbol=None):
        """
        取消所有止盈止损委托单
        :param position_id: 仓位id,传入positionId，只取消对应仓位的委托单，不传则判断symbol
        :param symbol: 合约名,传入symbol只取消该合约下的委托单，不传取消所有合约下的委托单
        :return:
        """
        endpoint = f"/api/v1/private/stoporder/cancel_all"
        payload = {
            'positionId': position_id,
            'symbol': symbol
        }
        payload = {k: v for k, v in payload.items() if v is not None}
        return self.mxc_post(endpoint, payload, is_private=True)

    def stop_order_change_price(self, order_id, stop_loss_price=None, take_profit_price=None):
        """
        修改限价单止盈止损价格
        :param order_id: 限价单订单id
        :param stop_loss_price: 止损价，止盈价和止损价同时为空或者同时为0时，则表示取消订单的止盈止损
        :param take_profit_price: 止盈价，止盈价和止损价同时为空或者同时为0时，则表示取消订单的止盈止损
        :return:
        """
        endpoint = f"/api/v1/private/stoporder/change_price"
        payload = {
            'orderId': order_id,
            'stopLossPrice': stop_loss_price,
            'takeProfitPrice': take_profit_price
        }
        payload = {k: v for k, v in payload.items() if v is not None}
        return self.mxc_post(endpoint, payload, is_private=True)

    def stop_order_change_plan_price(self, stop_plan_order_id, stop_loss_price=None, take_profit_price=None):
        """
        修改止盈止损委托单止盈止损价格
        :param stop_plan_order_id: 止盈止损委托单订单id
        :param stop_loss_price: 止损价,和止盈价至少有一个不为空，且必须大于0
        :param take_profit_price: 止盈价,和止损价至少有一个不为空，且必须大于0
        :return:
        """
        endpoint = f"/api/v1/private/stoporder/change_plan_price"
        payload = {
            'stopPlanOrderId': stop_plan_order_id,
            'stopLossPrice': stop_loss_price,
            'takeProfitPrice': take_profit_price
        }
        payload = {k: v for k, v in payload.items() if v is not None}
        return self.mxc_post(endpoint, payload, is_private=True)


if __name__ == '__main__':
    proxies = {'http': 'http://127.0.0.1:7890', 'https': 'http://127.0.0.1:7890'}
    # client = MxcClient(access_key='', secret_key='')
    client = MxcClient(access_key='', secret_key='',
                       api_base='https://contract.mxc.com', proxies=proxies)
    # 公共接口部分
    print(client.get_system_time())
    # print(client.get_contract_detail())
    # print(client.get_contract_detail('ETH_USDT'))
    # print(client.get_support_currencies())
    # print(client.get_contract_depth('ETH_USDT'))
    # print(client.get_contract_depth('ETH_USDT', 5))
    # print(client.get_contract_depth_commits('ETH_USDT', 1))
    # print(client.get_contract_index_price('ETH_USDT'))
    # print(client.get_contract_fair_price('ETH_USDT'))
    # print(client.get_contract_funding_rate('ETH_USDT'))
    # print(client.get_contract_kline('ETH_USDT', 'Min30', int(time.time()) - 60 * 30 * 200, int(time.time())))
    # print(client.get_contract_kline_index_price('ETH_USDT', 'Min30',
    #                                             int(time.time()) - 60 * 30 * 200, int(time.time())))
    # print(client.get_contract_kline_fair_price('ETH_USDT', 'Min30',
    #                                            int(time.time()) - 60 * 30 * 200, int(time.time())))
    # print(client.get_contract_deals('ETH_USDT'))
    # print(client.get_contract_deals('ETH_USDT', 5))
    # print(client.get_contract_ticker())
    # print(client.get_contract_ticker('ETH_USDT'))
    # print(client.get_contract_risk_reserve())
    # print(client.get_contract_risk_reserve_history('ETH_USDT'))
    # print(client.get_contract_risk_reserve_history('ETH_USDT', 1, 100))
    # print(client.get_contract_funding_rate_history('ETH_USDT'))
    # print(client.get_contract_funding_rate_history('ETH_USDT', 1, 100))

    # 私有接口部分
    # print(client.get_account_assets())
    # print(client.get_account_asset('USDT'))
    # print(client.get_account_transfer_record())
    # print(client.get_account_transfer_record('MUSDT'))
    # print(client.get_history_positions())
    # print(client.get_open_positions())
    # print(client.get_funding_records())
    # print(client.get_open_orders())
    # print(client.get_history_orders())
