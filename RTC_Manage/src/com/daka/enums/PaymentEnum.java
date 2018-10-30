package com.daka.enums;

public enum PaymentEnum
{

	RECHARGE(0, "充值"), UNFREEZEONEPROMOTION(1, "单笔激活推广奖金"), UNFREEZEALLPROMOTION(2, "全部激活推广奖金");

	private int value;
	private String desc;

	PaymentEnum(int value, String desc)
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
