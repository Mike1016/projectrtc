package com.daka.entry;

public class PromotionBonusVO
{
	private java.lang.Integer id;
	private java.lang.Integer customerId;
	private java.lang.Integer childCustomerId;
	private java.math.BigDecimal account;
	private String createTime;
	private String unfreezeTime;
	private java.math.BigDecimal unfreezeMoney;
	private java.lang.Integer state;

	private String childPhone;

	public String getChildPhone()
	{
		return childPhone;
	}

	public void setChildPhone(String childPhone)
	{
		this.childPhone = childPhone;
	}

	public java.lang.Integer getId()
	{
		return id;
	}

	public void setId(java.lang.Integer id)
	{
		this.id = id;
	}

	public java.lang.Integer getCustomerId()
	{
		return customerId;
	}

	public void setCustomerId(java.lang.Integer customerId)
	{
		this.customerId = customerId;
	}

	public java.lang.Integer getChildCustomerId()
	{
		return childCustomerId;
	}

	public void setChildCustomerId(java.lang.Integer childCustomerId)
	{
		this.childCustomerId = childCustomerId;
	}

	public java.math.BigDecimal getAccount()
	{
		return account;
	}

	public void setAccount(java.math.BigDecimal account)
	{
		this.account = account;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public String getUnfreezeTime()
	{
		return unfreezeTime;
	}

	public void setUnfreezeTime(String unfreezeTime)
	{
		this.unfreezeTime = unfreezeTime;
	}

	public java.math.BigDecimal getUnfreezeMoney()
	{
		return unfreezeMoney;
	}

	public void setUnfreezeMoney(java.math.BigDecimal unfreezeMoney)
	{
		this.unfreezeMoney = unfreezeMoney;
	}

	public java.lang.Integer getState()
	{
		return state;
	}

	public void setState(java.lang.Integer state)
	{
		this.state = state;
	}

}
