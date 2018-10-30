package com.daka.enums;

public enum BonusLogEnum
{
	 
	EVERYDAY_REBATE (1, "每天返利"), 
	COMMISSIONS (2, "佣金复投"),
	TEAM (3, "团队"),
	CASH (4, "提现"),
	BALANCE_RETURN (5, "余额复投"),
	INDEX_LEVEL_ACTIVATION (6, "指数下级激活");

	private int value;
	private String desc;

	BonusLogEnum(int value, String desc)
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
