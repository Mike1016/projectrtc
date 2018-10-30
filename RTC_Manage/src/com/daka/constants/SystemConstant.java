package com.daka.constants;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

public interface SystemConstant
{
	String SYS_USER = "user";

	String APP_USER = "app_user";

	String SUCCESS_CODE = "200";

	String FAIL_CODE = "500";

	String STATUS = "status";

	String MESSAGE = "msg";

	String PUBLIC_USER = "public_user";

	String LOCAL_PATH = "E:/resource/";

	double WEALTH_RESTRCTION = 1000;// 当剩余财富值低于此值时，无法完成卖出财富操作

	String CUSTOMER_LEVEL = "A";

	long CUSTOMER_VITALITY = 100;

	String CUSTOMER_ADDRESS = "陕西省-西安市";

	String CUSTOMER_SECURITYPASSWORD = "1234";

	String DATE_FORMART_PATTEN_1 = "yyyyMMddHHmmss";

	String DATE_FORMART_PATTEN_2 = "yyyy-MM-dd HH:mm:ss";

	BigDecimal DOUBLING = BigDecimal.valueOf(1.8);// 充值、复投翻倍比例

	SimpleDateFormat DATE_FORMAT_1 = new SimpleDateFormat(DATE_FORMART_PATTEN_1);

	SimpleDateFormat DATE_FORMAT_2 = new SimpleDateFormat(DATE_FORMART_PATTEN_2);

	int ROUND_HALF_UP_NUM = 2; // 四舍五入保留位数

	BigDecimal LIMITATION_OF_CASH = new BigDecimal(88);// 提现限制金额

	BigDecimal ACTIVATION_AMOUNT = new BigDecimal(8);// 激活额度限制

	String TIGGER_PARAM = "parameterList";

}
