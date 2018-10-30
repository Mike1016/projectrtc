package com.daka.enums;

public enum RechargeLogEnum
{
	
	ALIPAY_RECHARGE (1, "支付宝充值"), 
	COMMISSIONS (2, "佣金复投"),
	BBIN (3, "体验金"),
	TEAM (4, "团队"),
	BALANCE_RETURN (5, "余额复投"),
	EVERYDAY_REBATE (6, "每天返利"),
	PROMOTION_BONUS_ACTIVATION (7, "推广奖金激活");

	private int value;
	private String desc;

	RechargeLogEnum(int value, String desc)
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
