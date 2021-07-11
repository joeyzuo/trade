package com.comom.mapper;

import com.comom.domain.ContractOrder;import java.util.List;

public interface ContractOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ContractOrder record);

    int insertSelective(ContractOrder record);

    ContractOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ContractOrder record);

    int updateByPrimaryKey(ContractOrder record);

    List<ContractOrder> selectByStatus(String Status);

    List<ContractOrder> selectByNoClose();

    List<ContractOrder> selectByNoCloseOrder(String symbol);
}