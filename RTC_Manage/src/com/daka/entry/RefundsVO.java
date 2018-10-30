package com.daka.entry;

import java.math.BigDecimal;

public class RefundsVO {
	private Integer id;
	private Integer customerId;
	private BigDecimal totalPutforwardAmount;
	private BigDecimal totalRechargeAmount;
	private BigDecimal refundableAmount;
	private String createTime;
	private String reviewTime;
	private Integer state;
	
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
	public BigDecimal getTotalPutforwardAmount() {
		return totalPutforwardAmount;
	}
	public void setTotalPutforwardAmount(BigDecimal totalPutforwardAmount) {
		this.totalPutforwardAmount = totalPutforwardAmount;
	}
	public BigDecimal getTotalRechargeAmount() {
		return totalRechargeAmount;
	}
	public void setTotalRechargeAmount(BigDecimal totalRechargeAmount) {
		this.totalRechargeAmount = totalRechargeAmount;
	}
	public BigDecimal getRefundableAmount() {
		return refundableAmount;
	}
	public void setRefundableAmount(BigDecimal refundableAmount) {
		this.refundableAmount = refundableAmount;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getReviewTime() {
		return reviewTime;
	}
	public void setReviewTime(String reviewTime) {
		this.reviewTime = reviewTime;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
}
