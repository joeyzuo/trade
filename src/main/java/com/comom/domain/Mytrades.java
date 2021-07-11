package com.comom.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Mytrades {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    private String orderId;

    private String clientOrderId;

    private String closeOrderId;

    private String symbol;

    private Double price;

    private Double closePrice;

    private String leverage;

    private Integer origQty;

    private String orderType;

    private String orderStatus;

    public String getCloseOrderStatus() {
        return closeOrderStatus;
    }

    public void setCloseOrderStatus(String closeOrderStatus) {
        this.closeOrderStatus = closeOrderStatus;
    }

    private String closeOrderStatus;

    private Double openFee;

    private Double closeFee;

    private String side;

    private Double profit;
    private Double sysProfit;

    public Double getSysProfit() {
        return sysProfit;
    }

    public void setSysProfit(Double sysProfit) {
        this.sysProfit = sysProfit;
    }




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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getClientOrderId() {
        return clientOrderId;
    }

    public void setClientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    public String getCloseOrderId() {
        return closeOrderId;
    }

    public void setCloseOrderId(String closeOrderId) {
        this.closeOrderId = closeOrderId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }

    public String getLeverage() {
        return leverage;
    }

    public void setLeverage(String leverage) {
        this.leverage = leverage;
    }

    public Integer getOrigQty() {
        return origQty;
    }

    public void setOrigQty(Integer origQty) {
        this.origQty = origQty;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }



    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getOpenFee() {
        return openFee;
    }

    public void setOpenFee(Double openFee) {
        this.openFee = openFee;
    }

    public Double getCloseFee() {
        return closeFee;
    }

    public void setCloseFee(Double closeFee) {
        this.closeFee = closeFee;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }
}