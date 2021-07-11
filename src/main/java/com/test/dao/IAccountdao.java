package com.test.dao;


import com.test.pojo.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface IAccountdao {

    public List<Account> findAll();

    public void saveAccount(Account account);
}


