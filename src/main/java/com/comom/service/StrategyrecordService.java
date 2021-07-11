package com.comom.service;

import com.comom.domain.Strategyrecord;
import com.comom.mapper.StrategyrecordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("strategyrecordService")
public class StrategyrecordService {
    @Resource
    StrategyrecordMapper strategyrecordMapper;

    public Strategyrecord selectByPrimaryKey(Integer id){
        return  strategyrecordMapper.selectByPrimaryKey(id);
    };
}
