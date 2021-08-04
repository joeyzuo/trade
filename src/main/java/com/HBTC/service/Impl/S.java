package com.HBTC.service.Impl;

import com.HBTC.pojo.Constants;
import com.comom.UntilFartory;
import com.comom.domain.*;
import com.comom.mapper.ContractOrderMapper;
import com.comom.mapper.MyTradeMapper;
import com.comom.mapper.MytradesMapper;
import com.comom.mapper.StrategyrecordMapper;
import io.broker.api.client.BrokerApiClientFactory;
import io.broker.api.client.BrokerApiRestClient;
import io.broker.api.client.BrokerApiWebSocketClient;
import io.broker.api.client.BrokerContractApiRestClient;
import io.broker.api.client.domain.account.NewOrder;
import io.broker.api.client.domain.account.NewOrderResponse;
import io.broker.api.client.domain.account.OrderStatus;
import io.broker.api.client.domain.account.TimeInForce;
import io.broker.api.client.domain.contract.*;
import io.broker.api.client.domain.contract.request.CancelContractOrderRequest;
import io.broker.api.client.domain.contract.request.ContractMyTradeRequest;
import io.broker.api.client.domain.contract.request.ContractOrderRequest;
import io.broker.api.client.domain.contract.request.ContractPositionRequest;
import io.broker.api.client.domain.event.DepthEvent;
import io.broker.api.client.domain.market.OrderBook;
import io.broker.api.client.domain.market.OrderBookEntry;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class S {
    @Resource
    MytradesMapper mytradesMapper;
    @Resource
    MyTradeMapper myTradeMapper;
    @Resource
    ContractOrderMapper contractOrderMapper;
    @Resource
    StrategyrecordMapper strategyrecordMapper;
    private static BrokerApiRestClient restClient;
    private static BrokerContractApiRestClient contractApiRestClient;
    private static List<TraderRecord> traderRecords;

    private void setContractApiRestClient() {
        BrokerApiClientFactory factory = BrokerApiClientFactory.newInstance(Constants.API_BASE_URL, Constants.ACCESS_KEY, Constants.SECRET_KEY);
        contractApiRestClient = factory.newContractRestClient();
        restClient = factory.newRestClient();
    }


    public void trading(String symbol, Integer strategyInsId) {
        BrokerApiClientFactory factory = BrokerApiClientFactory.newInstance(Constants.API_BASE_URL, Constants.ACCESS_KEY, Constants.SECRET_KEY);
        BrokerApiRestClient client = factory.newRestClient();

//        System.out.println("\n ------market buy-----");
//        NewOrderResponse response3 = client.newOrder(NewOrder.marketBuy(symbol, "1"));
//        System.out.println(response3);
        System.out.println("\n ------limit buy-----");
        NewOrderResponse response1 = client.newOrder(NewOrder.limitBuy(symbol, TimeInForce.GTC, "1", "12.29"));
        System.out.println(response1);
    }

    public void tradingContract() {
        if (contractApiRestClient == null) {
            setContractApiRestClient();
        }
        BrokerApiWebSocketClient wclient = BrokerApiClientFactory
                .newInstance(Constants.API_BASE_URL)
                .newWebSocketClient(Constants.WS_API_BASE_URL, Constants.WS_API_USER_URL);


        // depth
       // wclient.onDepthEvent("BTC-SWAP-USDT", this::dealDepth, true);
        while (true) {
            try {
                Thread.sleep(1500);
                updateContractOrder();
                closeContractOrder(0.02, "BTC-SWAP-USDT");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private String lsSide = "";
    private String lsPrice = "";

    public void dealDepth(DepthEvent depthEvent) {
        Boolean isDoing = false;
        Double amountBigs = 0.0;
        Double amountAsks = 0.0;
        Integer bidI = 0;
        Double bPrice = 0.0;
        for (OrderBookEntry bid : depthEvent.getBids()) {
            if (bidI < 20) {
                amountBigs += Double.valueOf(bid.getQty());
                if (bidI == 19)
                    bPrice = Double.valueOf(bid.getPrice()) - 1;
            }


            bidI += 1;
        }
        ;
        Integer askI = 0;
        double aPrice = 0.0;
        for (OrderBookEntry ask : depthEvent.getAsks()) {
            if (askI < 20) {
                amountAsks += Double.valueOf(ask.getQty());
                if (askI == 19)
                    aPrice = Double.valueOf(ask.getPrice()) - 1;
            }

            askI += 1;
        }
        String price = "0";
        OrderSide side = OrderSide.SELL_OPEN;
        if (amountAsks > (amountBigs * 2)) {
            side = OrderSide.SELL_OPEN;
            price = depthEvent.getAsks().get(1).getPrice();
            isDoing = true;
        } else if (amountBigs > (amountAsks * 2)) {
            side = OrderSide.BUY_OPEN;
            price = depthEvent.getBids().get(1).getPrice();
            isDoing = true;
        }
        System.out.println("在执行中--------------");

        if (isDoing) {

            if (!lsPrice.equals(price)) {
                newContractOrder("BTC-SWAP-USDT", "20", side, price, "1");
            }

            lsPrice = price;
            lsSide = side.name();


        }

    }


    public void cancelContractOrder(ContractOrder contractOrder) {
        ContractOrderResult orderResultCancel = contractApiRestClient.cancelContractOrder(
                CancelContractOrderRequest.builder().orderId(contractOrder.getOrderId())
                        .orderType(OrderType.LIMIT)
                        .build()
        );
        contractOrder.setStatus(OrderStatus.CANCELED.name());
        contractOrderMapper.updateByPrimaryKeySelective(contractOrder);
    }


    public boolean isHavaOrder() {
        boolean b = true;
        List<ContractOrder> contractOrders = contractOrderMapper.selectByStatus("NEW,PARTIALLY_FILLED,PENDING_CANCEL");
        for (ContractOrder contractOrder : contractOrders) {
            if (contractOrder.getStatus().equals("NEW")) {
                if ((restClient.getServerTime() - contractOrder.getCreateTimeStamp()) > 30000) {
                    cancelContractOrder(contractOrder);
                }
            }
        }
        if (contractOrders.isEmpty())
            b = false;
        if (!b) {
            List<ContractOrder> contractOrders1 = contractOrderMapper.selectByNoClose();
            if (!contractOrders1.isEmpty())
                b = true;
        }
        return b;
    }

    public void newContractOrder(String symbol, String leverage, OrderSide orderSide, String price, String qty, ContractOrder closeOrder) {
        newContractOrder(symbol, leverage, orderSide, price, qty, OrderType.LIMIT, PriceType.INPUT, closeOrder);
    }

    public void newContractOrder(String symbol, String leverage, OrderSide orderSide, String price, String qty) {
        newContractOrder(symbol, leverage, orderSide, price, qty, OrderType.LIMIT, PriceType.INPUT, null);
    }

    public void initContractOrder(ContractOrderResult orderResult, ContractOrder contractOrder) {
        BeanUtils.copyProperties(orderResult, contractOrder);
        if (contractOrder.getCreateTime() == null) {
            contractOrder.setCreateTimeStamp(orderResult.getTime());
            contractOrder.setCreateTime(new Date(orderResult.getTime()));
        }
        contractOrder.setUpdateTime(new Date(orderResult.getTime()));
        contractOrder.setUpdateTimeStamp(orderResult.getTime());
        contractOrder.setPrice(Double.valueOf(orderResult.getPrice()));
        contractOrder.setOrderType(orderResult.getOrderType().name());
        contractOrder.setPriceType(orderResult.getPriceType().name());
        contractOrder.setStatus(orderResult.getStatus().name());
        contractOrder.setTimeInForce(orderResult.getTimeInForce().name());
        contractOrder.setSide(orderResult.getSide().name());
        if (contractOrder.getRefOrderId() == null)
            contractOrder.setRefOrderId(0L);

    }

    public void initMyTradeDomain(ContractMatchResult matchResult, MyTrade mytrade) {
        BeanUtils.copyProperties(matchResult, mytrade);
        mytrade.setCreateTimeStamp(matchResult.getTime());
        mytrade.setCreateTime(new Date(matchResult.getTime()));
        mytrade.setPrice(Double.valueOf(matchResult.getPrice()));
        mytrade.setQuantity(Double.valueOf(matchResult.getQuantity()));
        mytrade.setFee(Double.valueOf(matchResult.getFee()));
        mytrade.setSide(matchResult.getSide().name());
        mytrade.setOrderType(matchResult.getOrderType().name());
    }

    public void newContractOrder(String symbol, String leverage, OrderSide orderSide, String price, String qty, OrderType orderType, PriceType pt, ContractOrder closeOrder) {
        if (contractApiRestClient == null) {
            setContractApiRestClient();
        }
        ContractOrderResult orderResult = contractApiRestClient.newContractOrder(
                ContractOrderRequest.builder().symbol(symbol)
                        .leverage(leverage)
                        .orderType(orderType)
                        .side(orderSide)
                        .price(price)
                        .quantity(qty)
                        .priceType(pt)
                        .timeInForce(io.broker.api.client.domain.contract.TimeInForce.GTC)
                        .triggerPrice("")
                        .clientOrderId(UUID.randomUUID().toString())
                        .build()
        );
        ContractOrder contractOrder = new ContractOrder();
        initContractOrder(orderResult, contractOrder);
        contractOrderMapper.insert(contractOrder);
        if (closeOrder != null) {
            closeOrder.setRefOrderId(orderResult.getOrderId());
            contractOrderMapper.updateByPrimaryKeySelective(closeOrder);
        }
    }

    public void updateContractOrder() {
        System.out.println("updateContractOrder");
        if (contractApiRestClient == null) {
            setContractApiRestClient();
        }
        List<ContractOrder> contractOrders = contractOrderMapper.selectByStatus("NEW,PARTIALLY_FILLED,PENDING_CANCEL");
        for (ContractOrder contractOrder : contractOrders) {
            ContractOrderResult orderResult = contractApiRestClient.getContractOrder(OrderType.LIMIT, "", contractOrder.getOrderId());
            if (!orderResult.getStatus().name().equals(contractOrder.getStatus()) || !orderResult.getExecuteQty().equals(contractOrder.getExecuteQty())) {
                initContractOrder(orderResult, contractOrder);
                contractOrderMapper.updateByPrimaryKey(contractOrder);
                insetTrade(contractOrder);
            }
            if (contractOrder.getStatus().equals("NEW")) {
                if ((restClient.getServerTime() - contractOrder.getCreateTimeStamp()) > 50000) {
                    cancelContractOrder(contractOrder);
                }
            }
        }
    }

    public void closeContractOrder(double pRate, String symbol) {
        System.out.println("closeContractOrder");
        if (contractApiRestClient == null) {
            setContractApiRestClient();
        }
        OrderBook orderBook = contractApiRestClient.getContractOrderBook(symbol, 5);
        OrderBookEntry ask = orderBook.getAsks().get(0);
        OrderBookEntry bid = orderBook.getBids().get(0);
        List<ContractOrder> contractOrders = contractOrderMapper.selectByNoCloseOrder(symbol);
        for (ContractOrder contractOrder : contractOrders) {
            Double p = 0.0;
            OrderSide side = OrderSide.SELL_CLOSE;
            if (contractOrder.getSide().equals("BUY_OPEN")) {
                p = Double.valueOf(bid.getPrice());
            } else {
                p = Double.valueOf(ask.getPrice());
                side = OrderSide.BUY_CLOSE;
            }
            Double fee = 0.0;
            if (contractOrder.getFee() != null)
                fee += contractOrder.getFee();
            Double profitRate = UntilFartory.profitRate(contractOrder.getSymbol(), contractOrder.getSide(), contractOrder.getPrice(), p, Integer.valueOf(contractOrder.getExecuteQty()), fee);
            System.out.println("profitRate:" + profitRate);
            if (profitRate > pRate) {
                newContractOrder(symbol, "20", side, p.toString(), contractOrder.getExecuteQty(), contractOrder);
            } else if (profitRate < -4) {
                newContractOrder(symbol, "20", side, p.toString(), contractOrder.getExecuteQty(), contractOrder);
            }
        }
        isIco(contractOrders);
    }

    public void insetTrade(ContractOrder contractOrder) {
        System.out.println("insetTrade");
        List<ContractMatchResult> matchResults = contractApiRestClient.getContractMyTrades(
                ContractMyTradeRequest.builder()
                        .symbol(contractOrder.getSymbol())
                        .build()
        );
        for (ContractMatchResult matchResult : matchResults) {
            if (matchResult.getOrderId().toString().equals(contractOrder.getOrderId().toString())) {
                Boolean isInset = false;
                if (contractOrder.getRefTradeId() == null) {
                    isInset = true;
                    contractOrder.setRefTradeId(matchResult.getTradeId().toString());
                } else {
                    String[] split = contractOrder.getRefTradeId().split(",");
                    Boolean isHava = false;
                    for (String s : split) {
                        if (s.equals(matchResult.getTradeId().toString())) {
                            isHava = true;
                            break;
                        }
                    }
                    if (!isHava) {
                        isInset = true;
                        contractOrder.setRefTradeId(contractOrder.getRefTradeId().toString() + "," + matchResult.getTradeId().toString());
                    }
                }
                if (isInset) {
                    contractOrderMapper.updateByPrimaryKeySelective(contractOrder);
                    MyTrade myTrade = new MyTrade();
                    initMyTradeDomain(matchResult, myTrade);
                    myTradeMapper.insertSelective(myTrade);
                }
            }
        }
    }

    public void isIco(List<ContractOrder> contractOrders) {
        System.out.println("isIco");
        if (contractApiRestClient == null) {
            setContractApiRestClient();
        }
        String symbol = "";
        List<ContractOrder> l = new ArrayList<>();
        for (ContractOrder contractOrder : contractOrders) {
            if (contractOrder.getRefOrderId() == null || contractOrder.getRefOrderId().intValue() == 0) {
                l.add(contractOrder);
            }
        }
        Map<String, List<ContractOrder>> map = l.stream().collect(Collectors.groupingBy(o -> o.getSymbol() + "," + o.getSide(), Collectors.toList()));
        for (String key : map.keySet()) {
            String[] temp = key.split(",");
            String sysmbol = temp[0];
            PositionSide side = PositionSide.LONG;
            if (temp[1].equals("BUY_OPEN")) {
                side = PositionSide.LONG;
            } else {
                side = PositionSide.SHORT;
            }

            List<ContractPositionResult> positionResults = contractApiRestClient.getContractPositions(
                    ContractPositionRequest.builder()
                            .symbol(sysmbol)
                            .side(side)
                            .build()
            );
            if (positionResults.isEmpty()) {
                List<ContractOrder> v = map.get(key);
                for (ContractOrder contractOrder : v) {
                    contractOrder.setRefOrderId(-1L);
                    contractOrderMapper.updateByPrimaryKeySelective(contractOrder);
                }
            }
        }


    }

    public AjaxResult createStrategy(Strategyrecord strategyrecord){
        int i = strategyrecordMapper.insertSelective(strategyrecord);
        System.out.println(strategyrecord.getId());
        return AjaxResult.success("insert success");
    }


}
