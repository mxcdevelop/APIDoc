package com.mxc.contract.ws.example;

import com.mxc.contract.ws.Client;
import com.mxc.contract.ws.common.FilterEventEnum;
import com.mxc.contract.ws.dto.req.FilterDto;
import com.mxc.contract.ws.event.handler.MessageEventBus;

import java.util.*;

/**
 * example of subscribing or unsubscribing private channels
 */
public class PrivateChannelExample {

    public static void main(String[] args) throws InterruptedException {
        MessageEventBus eventBus = new MessageEventBus(false);
        Client client = new Client(eventBus, false);
        //param is false ,do not sub personal channels, if param is true or null ,all personal channels will be subscribed
        client.login(false);
        //waiting for login success
        Thread.sleep(200);

        // filter the channels that you want to received<br/>
        // if empty or null, all type and contact channels will be subscribed<br/>
        // if not empty only channels that contains in dtoList will will be subscribed

        //sub all personal channels
        client.filterEvent(null);

        //sub all personal channels
        client.filterEvent(Collections.emptyList());

        //sub channels contains list
        client.filterEvent(getFilers());

        System.out.println("your personal channels are subscribed when your data changed");
    }

    private static List<FilterDto> getFilers() {
        List<FilterDto> list = new ArrayList<>();
        //target symbols
        Set<String> set = new HashSet<>();
        set.add("BTC_USDT");
        set.add("ETH_USDT");
        set.add("EOS_USDT");
        set.add("SUSHI_USDT");

        // order
        FilterDto dto = new FilterDto(FilterEventEnum.FILTER_ORDER, set);
        list.add(dto);

        // deal
        dto = new FilterDto(FilterEventEnum.FILTER_ORDER_DEAL, set);
        list.add(dto);

        // position
        dto = new FilterDto(FilterEventEnum.FILTER_POSITION, set);
        list.add(dto);

        // all asset,not support filter by symbol
        dto = new FilterDto(FilterEventEnum.FILTER_ASSET, null);
        list.add(dto);


        // adl ,not support filter by symbol
        dto = new FilterDto(FilterEventEnum.FILTER_ADL, null);
        list.add(dto);

        // plan order
        dto = new FilterDto(FilterEventEnum.FILTER_PLAN_ORDER, set);
        list.add(dto);

        // stop order
        dto = new FilterDto(FilterEventEnum.FILTER_STOP_ORDER, set);
        list.add(dto);


        // stop plan order
        dto = new FilterDto(FilterEventEnum.FILTER_STOP_PLAN_ORDER, set);
        list.add(dto);


        //risk limit
        dto = new FilterDto(FilterEventEnum.FILTER_RISK_LIMIT, set);
        list.add(dto);

        return list;
    }
}
