package com.test.service;

import com.test.pojo.Account;

import java.util.List;

public interface AccountService {
    public List<Account> findAll();

    // 保存帐户信息
    public void saveAccount(Account account);
}
