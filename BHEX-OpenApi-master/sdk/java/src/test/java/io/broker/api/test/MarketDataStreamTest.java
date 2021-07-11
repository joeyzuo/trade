package io.broker.api.test;

import io.broker.api.client.BrokerApiClientFactory;
import io.broker.api.client.BrokerApiWebSocketClient;
import io.broker.api.client.BrokerContractApiRestClient;
import io.broker.api.client.domain.contract.*;
import io.broker.api.client.domain.contract.request.ContractOrderRequest;
import io.broker.api.client.domain.event.DepthEvent;
import io.broker.api.client.domain.market.CandlestickInterval;
import io.broker.api.client.domain.market.OrderBookEntry;
import io.broker.api.test.constant.Constants;

import java.util.Date;
import java.util.UUID;

public class MarketDataStreamTest {

    public static int i = 0;


    private static BrokerApiWebSocketClient client;
    private static BrokerContractApiRestClient contractApiRestClient;
    public static void main(String[] args) {
        BrokerApiClientFactory factory = BrokerApiClientFactory.newInstance(Constants.API_BASE_URL, Constants.ACCESS_KEY, Constants.SECRET_KEY);
        BrokerContractApiRestClient contractApiRestClient = factory.newContractRestClient();

        client = BrokerApiClientFactory
                .newInstance(Constants.API_BASE_URL)
                .newWebSocketClient(Constants.WS_API_BASE_URL, Constants.WS_API_USER_URL);

        // depth
        client.onDepthEvent("BTC-SWAP-USDT", MarketDataStreamTest::dealDepth);

//        // kline
        //client.onCandlestickEvent("BTCUSDT", CandlestickInterval.FIFTEEN_MINUTES, System.out::println);
//
//        // trades
        // client.onTradeEvent("BTCUSDT", System.out::println);
//
//        // ticker for 24 hour
//        client.onTicker24HourEvent("BTCUSDT", System.out::println);
//
//        // index
        client.onIndexEvent("BTCUSDT", System.out::println);
    }

    public static void dealDepth(DepthEvent depthEvent) {
        try {
            Thread.sleep(5 * 1000);
            System.out.println(new Date());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public double getPrice(String type, DepthEvent depthEvent) {
        System.out.println(depthEvent);
        return 1.01;
    }
}
