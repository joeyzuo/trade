package com.comom.domain;

import java.util.Date;

public class Strategyrecord {
    private Integer id;

    private Date createTime;

    private String symbol;

    private String strategy;

    private Double startPrice;

    private Double overPrice;

    private String direction;

    private Integer gridNumber;

    private Double stopLoss;

    private Double fee;

    private Double needFunds;

    private Double tradeNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public Double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Double startPrice) {
        this.startPrice = startPrice;
    }

    public Double getOverPrice() {
        return overPrice;
    }

    public void setOverPrice(Double overPrice) {
        this.overPrice = overPrice;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Integer getGridNumber() {
        return gridNumber;
    }

    public void setGridNumber(Integer gridNumber) {
        this.gridNumber = gridNumber;
    }

    public Double getStopLoss() {
        return stopLoss;
    }

    public void setStopLoss(Double stopLoss) {
        this.stopLoss = stopLoss;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getNeedFunds() {
        return needFunds;
    }

    public void setNeedFunds(Double needFunds) {
        this.needFunds = needFunds;
    }

    public Double getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(Double tradeNum) {
        this.tradeNum = tradeNum;
    }
}