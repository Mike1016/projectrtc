package com.daka.entry;

public class PresentVO {

    private Integer id;
    private Integer customerId;
    private String orderNo;
    private String createTime;
    private String receivingAddress;
    private Integer state;

    public PresentVO() {
    }

    public PresentVO(Integer id, Integer customerId, String orderNo, String createTime, String receivingAddress, Integer state) {
        this.id = id;
        this.customerId = customerId;
        this.orderNo = orderNo;
        this.createTime = createTime;
        this.receivingAddress = receivingAddress;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getReceivingAddress() {
        return receivingAddress;
    }

    public void setReceivingAddress(String receivingAddress) {
        this.receivingAddress = receivingAddress;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
