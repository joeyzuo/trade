package com.test.controller;


import com.test.pojo.Account;
import com.test.service.AccountService;
import io.broker.api.client.BrokerApiClientFactory;
import io.broker.api.client.BrokerApiRestClient;
import io.broker.api.client.domain.general.BrokerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.SQLOutput;
import java.util.List;

@Controller
public class AccountController {
    @Autowired   //按类型注入
    private AccountService accountService;

    @RequestMapping("/account/findAll")
    @ResponseBody
    public BrokerInfo findAll(){
        System.out.println("Controller表现层：查询所有账户...");
//        List<Account> list = accountService.findAll();
//        return "list";  //在视图解析器中配置了前缀后缀
        BrokerApiClientFactory factory = BrokerApiClientFactory.newInstance("https://api.hbtc.com");
        BrokerApiRestClient client = factory.newRestClient();

        System.out.println("\n ------BrokerInfo-----");
        BrokerInfo brokerInfo = client.getBrokerInfo();

        return  brokerInfo;
    }
    @RequestMapping("/account/findAllToString")
    @ResponseBody
    public String findAllToString(){
        System.out.println("Controller表现层：查询所有账户...");
//        List<Account> list = accountService.findAll();
//        return "list";  //在视图解析器中配置了前缀后缀
        BrokerApiClientFactory factory = BrokerApiClientFactory.newInstance("https://api.hbtc.com");
        BrokerApiRestClient client = factory.newRestClient();

        System.out.println("\n ------BrokerInfo-----");

        BrokerInfo brokerInfo = client.getBrokerInfo();
        System.out.println(brokerInfo);
        return  brokerInfo.toString();
    }
}


