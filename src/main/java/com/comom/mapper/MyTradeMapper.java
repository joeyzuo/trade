package com.comom.mapper;

import com.comom.domain.MyTrade;

public interface MyTradeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MyTrade record);

    int insertSelective(MyTrade record);

    MyTrade selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MyTrade record);

    int updateByPrimaryKey(MyTrade record);
}