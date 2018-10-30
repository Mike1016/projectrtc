package com.daka.entry;

import java.math.BigDecimal;

public class RankingVO {

    private Integer id;
    private String phone;
    private BigDecimal account;
    private Integer state;

    public RankingVO() {
    }

    public RankingVO(Integer id, String phone, BigDecimal account, Integer state) {
        this.id = id;
        this.phone = phone;
        this.account = account;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
