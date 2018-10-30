package com.daka.enums;

public enum FiveTimesInvestStateEnum
{

	RUNNING(0, "正在分红"), FINISHED(1, "分红完成"), TAKEDCASH(2, "提现完成");

	private int value;
	private String desc;

	FiveTimesInvestStateEnum(int value, String desc)
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
