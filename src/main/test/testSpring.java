import com.HBTC.pojo.Constants;
import com.HBTC.service.Impl.S;
import com.comom.domain.Strategyrecord;

import com.test.service.AccountService;
import io.broker.api.client.BrokerApiClientFactory;
import io.broker.api.client.BrokerContractApiRestClient;
import io.broker.api.client.domain.contract.*;
import io.broker.api.client.domain.contract.request.ContractHistoryOrderRequest;
import io.broker.api.client.domain.contract.request.ContractMyTradeRequest;
import io.broker.api.client.domain.contract.request.ContractOrderRequest;
import io.broker.api.client.domain.market.TradeHistoryItem;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring.xml"})
public class testSpring {
    @Resource
    S s;
    @Test
    public void  run(){


    }
    @Test
    public void trading(){
        //s.newContractOrder("FIL-SWAP-USDT","10",OrderSide.BUY_OPEN,"183.26","1");
       //s.updateContractOrder();
        s.closeContractOrder(0.05, "BTC-SWAP-USDT");
    }

    @Test
    public  void test(){
        BrokerApiClientFactory factory = BrokerApiClientFactory.newInstance(Constants.API_BASE_URL, Constants.ACCESS_KEY, Constants.SECRET_KEY);
        BrokerContractApiRestClient client = factory.newContractRestClient();

        List<ContractOrderResult> historyOrders = client.getContractHistoryOrders(
                ContractHistoryOrderRequest.builder()
                        .limit(10)
                        .side(OrderSide.BUY_OPEN)
                        .build()
        );
        System.out.println(historyOrders);
        List<ContractMatchResult> matchResults = client.getContractMyTrades(
                ContractMyTradeRequest.builder()
                        //.side(OrderSide.BUY)
                        .limit(10)
                        .build()
        );
        System.out.println(matchResults);

        ContractOrderResult orderResult = client.newContractOrder(
                ContractOrderRequest.builder().symbol("FIL-SWAP-USDT")
                        .leverage("50")
                        .orderType(OrderType.LIMIT)
                        .side(OrderSide.BUY_OPEN)
                        .price("36.7")
                        .quantity("1")
                        .priceType(PriceType.INPUT)
                        .timeInForce(TimeInForce.GTC)
                        .triggerPrice("")
                        .clientOrderId(UUID.randomUUID().toString())
                        .build()
        );

        System.out.println(orderResult);
        ContractOrderResult sOrderResult = client.getContractOrder(OrderType.LIMIT, "", 836884512920147200L);
        System.out.println(sOrderResult);
    }
    /**
     * GET---????????????
     *
     * @date 2018???7???13??? ??????4:18:50
     */

    static SqlSessionFactory sqlSessionFactory = null;
    //BeforeClass?????????Junit??????????????????????????????????????????????????????????????????sqlSessionFactory??????
    @BeforeClass
    public static void init() throws IOException {
        Reader reader = Resources.getResourceAsReader("mybatis-config-cs.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    @Test
    public void doGetTestOne() {
        SqlSession session = null;
        //openSession??????????????????SqlSession?????????SqlSession????????????????????????????????????

        try {
            session = sqlSessionFactory.openSession();
            //selectList????????????????????????
            //namespace.sqlId
            Strategyrecord strategyrecord = session.selectOne("com.comom.domain.Strategyrecord",1);


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (session != null) {
                //???Connection???????????????????????????Session??????
                session.close();
            }
        }
    }


}
