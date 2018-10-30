package com.daka.enums;

public enum SystemEnum
{
	PAYMENT(1, "支付宝充值到待分红"), 
	COMMISSION_RE_CAST(2, "佣金钱包转入待分红"), 
	BALANCE_RE_CAST(5, "余额钱包转入待分红"), 
	DIVIDED_TO_BONUS(6,"余额复投"), 
	BONUS_TO_DIVIDED(1, "每日待分红收益"), 
	DIVIDED_TO_SHOPPING_COIN(1, "每日待分红收益"), 
	EVERY_TO_BONUS(6, "每日分红到余额钱包"),
	PROMOTION_ACTIVATION(7,"推广奖金激活"),
	AGENT_DIVIDED(6, "直属下级激活");

	private Integer code;
	private String value;

	SystemEnum(Integer code, String value)
	{
		this.code = code;
		this.value = value;
	}

	public Integer getCode()
	{
		return code;
	}

	public String getValue()
	{
		return value;
	}
}