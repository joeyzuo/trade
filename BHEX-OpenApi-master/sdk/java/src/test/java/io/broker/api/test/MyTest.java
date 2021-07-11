package io.broker.api.test;

import com.google.common.collect.Lists;
import io.broker.api.client.BrokerApiClientFactory;
import io.broker.api.client.BrokerContractApiRestClient;
import io.broker.api.client.BrokerOptionApiRestClient;
import io.broker.api.client.domain.contract.*;
import io.broker.api.client.domain.contract.request.*;
import io.broker.api.client.domain.general.BrokerInfo;
import io.broker.api.client.domain.general.TradeType;
import io.broker.api.client.domain.market.Candlestick;
import io.broker.api.client.domain.market.CandlestickInterval;
import io.broker.api.client.domain.market.OrderBook;
import io.broker.api.client.domain.market.TradeHistoryItem;
import io.broker.api.test.constant.Constants;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MyTest {

    public static void main(String[] args) {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug");

        BrokerApiClientFactory factory = BrokerApiClientFactory.newInstance(Constants.API_BASE_URL, Constants.ACCESS_KEY, Constants.SECRET_KEY);
        BrokerContractApiRestClient client = factory.newContractRestClient();
        BrokerOptionApiRestClient optionclient = factory.newOptionRestClient();

        List<ContractPositionResult> positionResults = client.getContractPositions(
                ContractPositionRequest.builder()
                        .symbol("FIL-SWAP-USDT")
                        .side(PositionSide.SHORT)
                        .build()
        );
        System.out.println(positionResults);

        OrderBook orderBook = client.getContractOrderBook("BTC-SWAP-USDT", null);
        System.out.println(orderBook);
//        ContractOrderResult orderResult = client.newContractOrder(
//                ContractOrderRequest.builder().symbol("BTC-SWAP-USDT")
//                        .leverage("50")
//                        .orderType(OrderType.LIMIT)
//                        .side(OrderSide.SELL_CLOSE)
//                        .price("57070")
//                        .quantity("1")
//                        .priceType(PriceType.INPUT)
//                        .timeInForce(io.broker.api.client.domain.contract.TimeInForce.GTC)
//                        .triggerPrice("")
//                        .clientOrderId(UUID.randomUUID().toString())
//                        .build()
//        );
//        System.out.println(orderResult);

        List<ContractMatchResult> matchResults = client.getContractMyTrades(
                ContractMyTradeRequest.builder()
                        .symbol("BTC-SWAP-USDT").fromId(865159096098204928l)
                        .build()
        );
        System.out.println(matchResults);

        List<ContractOrderResult> historyOrders = client.getContractHistoryOrders(
                ContractHistoryOrderRequest.builder().limit(20).build()
        );
        System.out.println(historyOrders);
        for (ContractOrderResult historyOrder : historyOrders) {
            ContractOrderResult sOrderResult = client.getContractOrder(OrderType.LIMIT, "", historyOrder.getOrderId());
            System.out.println(sOrderResult);
        }
    }
}
