package com.daka.enums;

public enum GoodsDetailEnum
{

	COVER(1, "首页展示"), DETAIL(2, "详情展示");

	private int value;
	private String desc;

	GoodsDetailEnum(int value, String desc)
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
