package com.comom.mapper;

import com.comom.domain.Strategyrecord;

public interface StrategyrecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Strategyrecord record);

    int insertSelective(Strategyrecord record);

    Strategyrecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Strategyrecord record);

    int updateByPrimaryKey(Strategyrecord record);
}