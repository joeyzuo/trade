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
     * GET---无参测试
     *
     * @date 2018年7月13日 下午4:18:50
     */

    static SqlSessionFactory sqlSessionFactory = null;
    //BeforeClass用于在Junit启用测试用例前执行一次全局初始化工作，来创建sqlSessionFactory对象
    @BeforeClass
    public static void init() throws IOException {
        Reader reader = Resources.getResourceAsReader("mybatis-config-cs.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    @Test
    public void doGetTestOne() {
        SqlSession session = null;
        //openSession创建一个新的SqlSession对象，SqlSession提供了增删改查的方法调用

        try {
            session = sqlSessionFactory.openSession();
            //selectList用于查询多条数据
            //namespace.sqlId
            Strategyrecord strategyrecord = session.selectOne("com.comom.domain.Strategyrecord",1);


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (session != null) {
                //将Connection归还到连接池供其他Session重用
                session.close();
            }
        }
    }


}
