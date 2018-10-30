package com.daka.entry;


public class RechargePutforwardOrderVO {

	private Integer id;
	private Integer customerId;
	private String payOrderNo;
	private String payOutOrderNo;
	private double payRealityTotal;
	private double payApplyTotal;
	private Integer payState;
	private String payApplyTime;
	private String payCompleteTime;
	private String payNote;
	private Integer payType;
	private String phone;


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

	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}

	public String getPayOutOrderNo() {
		return payOutOrderNo;
	}

	public void setPayOutOrderNo(String payOutOrderNo) {
		this.payOutOrderNo = payOutOrderNo;
	}

	public double getPayRealityTotal() {
		return payRealityTotal;
	}

	public void setPayRealityTotal(double payRealityTotal) {
		this.payRealityTotal = payRealityTotal;
	}

	public double getPayApplyTotal() {
		return payApplyTotal;
	}

	public void setPayApplyTotal(double payApplyTotal) {
		this.payApplyTotal = payApplyTotal;
	}

	public Integer getPayState() {
		return payState;
	}

	public void setPayState(Integer payState) {
		this.payState = payState;
	}

	public String getPayApplyTime() {
		return payApplyTime;
	}

	public void setPayApplyTime(String payApplyTime) {
		this.payApplyTime = payApplyTime;
	}

	public String getPayCompleteTime() {
		return payCompleteTime;
	}

	public void setPayCompleteTime(String payCompleteTime) {
		this.payCompleteTime = payCompleteTime;
	}

	public String getPayNote() {
		return payNote;
	}

	public void setPayNote(String payNote) {
		this.payNote = payNote;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
