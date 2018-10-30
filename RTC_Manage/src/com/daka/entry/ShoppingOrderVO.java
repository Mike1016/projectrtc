package com.daka.entry;

import java.util.Date;
import java.util.Random;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.daka.constants.SystemConstant;
import com.daka.enums.OrderEnum;

/**
 * @author Administrator
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ShoppingOrderVO
{

	private Integer id;
	private Integer customerId;
	private String orderNo;
	private Integer receiovingAddressId;
	private String remark;
	private Integer state;
	private String createTime;
	private String updateTime;



	public Integer getReceiovingAddressId()
	{
		return receiovingAddressId;
	}

	public void setReceiovingAddressId(Integer receiovingAddressId)
	{
		this.receiovingAddressId = receiovingAddressId;
	}

	public String getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(String updateTime)
	{
		this.updateTime = updateTime;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getCustomerId()
	{
		return customerId;
	}

	public void setCustomerId(Integer customerId)
	{
		this.customerId = customerId;
	}

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public Integer getState()
	{
		return state;
	}

	public void setState(Integer state)
	{
		this.state = state;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

}
