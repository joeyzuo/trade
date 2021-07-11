package com.HBTC.controller;

import com.HBTC.pojo.Constants;
import com.HBTC.service.Impl.S;
import com.comom.domain.AjaxResult;
import io.broker.api.client.BrokerApiClientFactory;
import io.broker.api.client.BrokerApiRestClient;
import io.broker.api.client.domain.general.BrokerInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/hbtc")
public class C {
    @Resource
    S s;
    @RequestMapping("/BrokerInfo")
    @ResponseBody
    public BrokerInfo findAll(){
        System.out.println("Controller表现层：查询所有账户...");
//        List<Account> list = accountService.findAll();
//        return "list";  //在视图解析器中配置了前缀后缀
        BrokerApiClientFactory factory = BrokerApiClientFactory.newInstance(Constants.API_BASE_URL);
        BrokerApiRestClient client = factory.newRestClient();
        System.out.println("\n ------BrokerInfo-----");
        BrokerInfo brokerInfo = client.getBrokerInfo();
        return  brokerInfo;
    }

    @RequestMapping("/getStrategy")
    @ResponseBody
    public AjaxResult getStrategy(Integer id){
        AjaxResult result=AjaxResult.success(s.getStrategy(id));
        return  result;
    }
    @RequestMapping("/impStrategy")
    @ResponseBody
    public AjaxResult impStrategy(){
        s.tradingContract();
        return new AjaxResult();
    }
}
