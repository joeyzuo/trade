package com.comom.mapper;

import com.comom.domain.Mytrades;

import java.util.List;

public interface MytradesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Mytrades record);

    int insertSelective(Mytrades record);

    Mytrades selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Mytrades record);

    int updateByPrimaryKey(Mytrades record);
    List<Mytrades> selectByNoOver();
    List<Mytrades> selectByNeedClose();
    List<Mytrades> selectByOrderStatus(String status);

}