package com.mxc.contract.ws.example;

import com.mxc.contract.ws.Client;
import com.mxc.contract.ws.common.Command;
import com.mxc.contract.ws.common.Interval;
import com.mxc.contract.ws.dto.req.SubscribeParam;
import com.mxc.contract.ws.event.handler.MessageEventBus;

/**
 * example of subscribing or unsubscribing public channels
 */
public class PublicChannelExample {

    public static void main(String[] args) throws InterruptedException {

        MessageEventBus eventBus = new MessageEventBus(false);
        Client client = new Client(eventBus, false);

        //sub k-line
        client.sub(Command.SUB_KLINE, new SubscribeParam("BTC_USDT", Interval.Min15));

        //sub fair price
        client.sub(Command.SUB_FAIR_PRICE, new SubscribeParam("BTC_USDT"));

        //sub index price
        client.sub(Command.SUB_INDEX_PRICE, new SubscribeParam("BTC_USDT"));

        //sub funding rate
        client.sub(Command.SUB_FUNDING_RATE, new SubscribeParam("BTC_USDT"));

        //sub transaction
        client.sub(Command.SUB_DEAL, new SubscribeParam("BTC_USDT"));

        //sub depth for compress
        client.sub(Command.SUB_DEPTH, new SubscribeParam("BTC_USDT", true));
        //sub depth
        client.sub(Command.SUB_DEPTH, new SubscribeParam("BTC_USDT"));

        //sub full depth
        client.sub(Command.SUB_FULL_DEPTH, new SubscribeParam("BTC_USDT"));
        //sub full depth for limit 10
        client.sub(Command.SUB_FULL_DEPTH, new SubscribeParam("BTC_USDT", 10));

        //sub all tickers
        client.sub(Command.SUB_TICKERS, null);

        //sub ticker
        client.sub(Command.SUB_TICKER, new SubscribeParam("BTC_USDT"));

        Thread.sleep(20000L);

        //unsub k-line
        client.unsub(Command.UNSUB_KLINE, new SubscribeParam("BTC_USDT", Interval.Min15));

        //unsub fair price
        client.unsub(Command.UNSUB_FAIR_PRICE, new SubscribeParam("BTC_USDT"));

        //unsub index price
        client.unsub(Command.UNSUB_INDEX_PRICE, new SubscribeParam("BTC_USDT"));

        //unsub funding rate
        client.unsub(Command.UNSUB_FUNDING_RATE, new SubscribeParam("BTC_USDT"));

        //unsub transaction
        client.unsub(Command.UNSUB_DEAL, new SubscribeParam("BTC_USDT"));

        //unsub depth
        client.unsub(Command.UNSUB_DEPTH, new SubscribeParam("BTC_USDT"));

        //unsub full depth
        client.unsub(Command.UNSUB_FULL_DEPTH, new SubscribeParam("BTC_USDT"));

        //unsub all tickers
        client.unsub(Command.UNSUB_TICKERS, null);

        //unsub ticker
        client.unsub(Command.UNSUB_TICKER, new SubscribeParam("BTC_USDT"));

        Thread.sleep(1000);

        System.out.println("-------------------unsub all public channels,no more data from public channels will be pushed-------------------");
    }
}
