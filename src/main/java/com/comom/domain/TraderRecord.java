package com.comom.domain;
import lombok.Data;
import io.broker.api.client.domain.contract.ContractOrderResult;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TraderRecord extends ContractOrderResult {
    private String yinggui;
}
