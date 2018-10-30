package com.daka.entry;


public class RebateLogVO {

	private java.lang.Integer id;
	private java.lang.Integer customerId;
	private java.lang.Integer agentId;
	private java.lang.Integer type;
	private java.math.BigDecimal account;
	private String createTime;


	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(java.lang.Integer customerId) {
		this.customerId = customerId;
	}

	public java.lang.Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(java.lang.Integer agentId) {
		this.agentId = agentId;
	}

	public java.lang.Integer getType() {
		return type;
	}

	public void setType(java.lang.Integer type) {
		this.type = type;
	}

	public java.math.BigDecimal getAccount() {
		return account;
	}

	public void setAccount(java.math.BigDecimal account) {
		this.account = account;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
