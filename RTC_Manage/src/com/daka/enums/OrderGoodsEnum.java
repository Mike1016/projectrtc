package com.daka.enums;

public enum OrderGoodsEnum
{

	NORMAL(0, "正常"), REFUND(-1, "退款");

	private int value;
	private String desc;

	OrderGoodsEnum(int value, String desc)
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
