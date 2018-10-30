package com.daka.entry;

public class FiveInvestLog
{

	private java.lang.Integer id;
	private java.lang.Integer customerId;
	private java.lang.Integer fiveInvestId;
	private java.math.BigDecimal capital;
	private java.math.BigDecimal rate;
	private java.math.BigDecimal currentReturnAccount;
	private String createTime;

	public java.lang.Integer getFiveInvestId()
	{
		return fiveInvestId;
	}

	public void setFiveInvestId(java.lang.Integer fiveInvestId)
	{
		this.fiveInvestId = fiveInvestId;
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

	public java.math.BigDecimal getCapital()
	{
		return capital;
	}

	public void setCapital(java.math.BigDecimal capital)
	{
		this.capital = capital;
	}

	public java.math.BigDecimal getRate()
	{
		return rate;
	}

	public void setRate(java.math.BigDecimal rate)
	{
		this.rate = rate;
	}

	public java.math.BigDecimal getCurrentReturnAccount()
	{
		return currentReturnAccount;
	}

	public void setCurrentReturnAccount(java.math.BigDecimal currentReturnAccount)
	{
		this.currentReturnAccount = currentReturnAccount;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}
}
