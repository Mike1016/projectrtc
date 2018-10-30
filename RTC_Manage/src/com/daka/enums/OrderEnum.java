package com.daka.enums;

public enum OrderEnum
{

	WAITING_PAY(0, "等待付款"), CANCLE(1, "取消"), WAITING_SEND_GOODS(2, "等待发货"), WAITING_ACCEPT_GOODS(3, "等待收货"), FINISHED(4,
			"完成");

	private int value;
	private String desc;

	OrderEnum(int value, String desc)
	{
		this.value = value;
		this.desc = desc;
	}

	public int getValue()
	{
		return this.value;
	}

	public String getDesc()
	{
		return this.desc;
	}

}
