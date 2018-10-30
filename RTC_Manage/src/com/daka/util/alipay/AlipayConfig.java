package com.daka.util.alipay;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;

import net.sf.json.JSONObject;

public class AlipayConfig
{
	// **************手机网站支付信息***************************************************//
	// 商户appid
	public static String APPID = "2018101861701659";
	// 商户id
	public static String seller_id = "2088331138835904";
	// 私钥 pkcs8格式的
	public static String RSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQClw2K4iWD8zsnyVziVIUQQsmxZfWCdtD+qIdV1hBC83LEiT24TOf/jBCr5vinwZSWHUVmLbCOj4dntMRtAlxhgfTcH9HU1erHMU4z2SS1yAS18jSfYd1ap4rIOAhZ0BzcYbR27dpSHcUj8IS/i61M6bmOks/1gozZtcByzOoFMnS/56qo2pz7r2VEnejksO64v1L/iZNvaFqBFd5xwQm7Zb6ULbrTPY2wFZdnSE6iIY93mHpZiBE6AM9KWjcBO3FPiPpgsQgQgyggRZZsQrkaWcjYThaxb4ajk77DSZBDgzGVCXrKJnaykaNU69JK5f/v5QtkkH2Eb9cUYnWwW2vbFAgMBAAECggEAMiA1eCxC4e/+bYQmniIjHRF3ZlBdqRLata3piNHY5eOuxPtUBAhj8thNzpvMvt83TAYzl8+pr2MarXlODmgifPsgaw1nbDagEXOqisdyMKlege2FgRYFZPYidc330DpmVMKaXRfsrlB5i5TVkD8tOdwr0aCEHHb+fHWMmD42312hnnJJThB6W8WLx8nRm9ZXnn66zA0dmF4+TXptkja4ga0i5/c3spEiMc5xveTNLpg1SJQOaCHRjQzOjtQHyAEydIu5drEHZaLenz8F3K55pc6qyXw++sAYNdvSaoPZwemkUREOB2zULsPTN9Ms+xJvyTtiQJ3WGQ+tC5ybz3PsQQKBgQD/6Qc9arpreGLq8OKLrrJCfQWPlU7FLe9JHFCC45Voi3RERH9DD9ApdScZNA6pa28hbBNUBJx50HlIKpkz4DZ65oSnOhB/VkHXKVbjeEqNj7zkIjLNQFq6NJcQgbC0oSN0qeKSzvXH/3YR4T1QbK/nWKntsIUzBhu5SJ6aq4aNuQKBgQCl0kPsG4AAydSZjmKlYfIPU8HrKAOYXxBWrGlT00Dr2CcVYEPaGV4HpbSyCroCWa0mFJkbZzG12pw9v3JbjT/ysUsH7xY8V0c2YcwMXTJs1biKs05exY9Q/fRUsQlIzEMlhcnSX2UqiJTNEagb9SLRvppFEtKldS+pi4LRKP0XbQKBgBR6FPY5IAmRbHjYJYh/xgvD7Tn7/H2Sm18CQP74LvBamEcOJBC9py5qDWLa44Fy80V9XF6Qr+SfZdT8c53PBwz2fQ7UepV11Xbd+/HfojeJlNe8VMxPpfLYWm4bsoQBkNC490RAAzKpokzz9PAGglzEn3RBd6cJihZdVTOTHgUhAoGAQpWoCIsSXz6sS+zM17P4ywbK+25Z4zlpCiS5NpvBmfJ13zOEi3NcOp3jbQq5nm/8AyqKQGKzp15dFr5lYTXa0801X741o404XzHuCZjywpkfPTK66XtTDSaskcV1APJklBCg9gSwTuABQEcyxectUCPFNq9UPnRL6zzGDs7yshECgYEApEHg6ctZ9N98oxAqhd8gtCq+yWXDrpWdyGmw5ICJwR+50SGCS1ZImOymibsOtcnfrWpJco/HPFpX8N8AHGjzKuB8E9W+69ebltT+HwUyv2VOWuKvxJU599K4KUOW3VxqgUeuyGLqfW4iXckVX0G5n0tiFbcXKD89DI4VBh3uzXE=";
	// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://154.222.142.152/GCR_Manage/app/alipay/payNotify.do";
	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	// 商户可以自定义同步跳转地址
	public static String return_url = "http://154.222.142.152/GCR_Manage/webapp/my.html";
	// 请求网关地址
	public static String URL = "https://openapi.alipay.com/gateway.do";
	// 编码
	public static String CHARSET = "UTF-8";
	// 返回格式
	public static String FORMAT = "json";

	// 调用的接口版本
	public static String version = "1.0";
	// 商品标题/订单标题
	public static String subject = "GCR商城充值";
	// 支付宝公钥
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArV1p8pU5kO7k1oJf0jVqJmpzgnVFxtrf73qIcSAJdvRzcZnp9Xjsd4y7HQrc1/bkexiWDrAZd3HltKtQjgwwMV896RXrUCqRW4/OtMYn6Abo5LrlgIGt1XebpKzRH8YFGOjUMylEDL1IoQJ7gplUTzZXa3mMD2HyFBbmNk5FQ79I8aElbJbcipalEWtVPvS5MAgXhWruSvoOR2as2PEa8sfk0MJr3O+YvW151fl4m9clam5eswr6tMD4fFRnuH0m2FyUjJBDYCz+82i7bfpi17wJASimB6+A/qVTX4sc8VnI5Ga2/33fpDpjiv8Ftl0/zdF8WyvBccu9fKESqO7RjwIDAQAB";
	// 日志记录目录
	public static String log_path = "/log";

	public static String method = "alipay.trade.wap.pay";
	// RSA2
	public static String SIGNTYPE = "RSA2";
	// 超时关闭交易时间
	public static String timeoutExpress = "15m";

	public static String product_code = "QUICK_WAP_WAY";

	// ***************转账到支付宝账户**************************************************//
	// APPID
	public static String Transfer_appid = "2018091761417198";
	// 转账商户私钥
	public static String Transfer_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCpOw25ACWazrExG5EqXPTJR0XGmQ5o6pp03jrtq3ELSi2h6k63oDW0P2AxcPhU55WtxMDrwx+DkYf8gW2ZfjYVkY0j2+vYQwdPZbkpclgMtHM4g3LZVdNpJhNuIPVoc+xDCObQu4orjKwlqF2IsHjJHEXQ7vX8eLzycFFAS0xJm4Eg9klKZWjEzbFw+UUbKXZ2sstLqfhjviz/6llOwRlufTyK92zJdzrJRebrsRGiXh/mS4FSENDEFTvqd1IJjiijW5YFh4aZgLfCBmT9P+FgF+pAnAM00QH8Pd3FRitzbAkkaFy3/HIPg6POMEEidgkdKP4U+siu1P+OIcH+nglnAgMBAAECggEAUSYSJH4fiiZG7WdsqsW8U7Ark1ndgQ3OVvAhjcpCAMnnK9cxO/hFCFPDirHDQuNx8MuCPwtn1y036iseJRZSVPFgnqtcYm1x2e7LZUaBVkZJYfYWYoU3RRqPAqYnR5ke194y4DCtxshD3CLqBxuoL7ew+sk7h39WC2M0cwIoaLqyzYqSpI8C2Q2dsHNCJM9+tJwplB7PdTmSBtyOgpfwr1rdZbPcxlVGMUa1Wns1PMBcKTRAJdltC+RFCZ3px1UdWutz1yT/YBC+gau7dmh6MaPOL2kSgehDttW5duq+N74LssNuETvgrNw7BKmoOdQFwTTRqkSALtsNXy81IeSDMQKBgQDaaGDRcn5O2+nFc+1qvN59H6cj1sJh1WrVnSYjkIZMlS29T8kYsDgKUciJ5yP7wZ30hGXIGi/paWQ7PKyzcHVCSIcL/Lq9XIyEwuHQ/9Exu7Z7IfXaWsgbEPlyc+yZnQb2pMLeWKPyod/Xmew8aUnHwHE0TJXbTxPRkvpQqvGZgwKBgQDGW838A1tiPyuV2dCTtVQ3M2hGJAsG9LKXhlw4v26AWKNY149YTOi9CB/4mVf1cL6pw891ESfEhuoElwYCecKPxw2SeAlbPZJw64qXQ3kkDJoh6swB0uR5xLEaMYeJghi7OxRbB3c98ZmAgM0YGVgNYUI/tqaQpNLa1v8ywXQfTQKBgBIjpx7eFnVwOEso5Kf/xa64qUYFuSEs+3GAWsGLaEwF+8WEUdxWDmF1B2XEp61qGDdNo7Jp0l7dXPC6ilFg3qxBn820EMJXZGhGU0DrbjFmOLKjUGrxLTtqPn1t+VGMxB5J8tFKpVsqbI/YfdFKoVEQv5YrYirTt36paVa3y9cHAoGASWDkznmfcLTVttWg50OJJ/KIfIoQ2j3jY1J9AzEt+6TOqKr1iEDLSEKdMSXo+A8BOWQKdtAl2fne0FveCURpyS5lwu1M6MfaSJOZ7WM0iTVwP2PQnEwb4T/2FC6GaUQLShgM66/TWMLrejIrrOKTRD/adh9ndtLCfdpTBb+y6XECgYAGRPmIQrGmJzDCiG+tuUlLHjRK3fn6T/9WCaEgszSsXESKzbzreMGU3vAzBSc54xdpRjjtZbIUaB14FLCfG4gN4BSevXO8GDtlsarn5kvkJYBfQykrSRIZi907SqtFxw06C1yQN4P5Q0T7YazdX/PWrHQBGOUP6G8P5MAVvZ3Ppg==";
	// 转账支付宝公钥
	public static String Transfer_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsU1FVW3Zgl58uKme3j1L0Y9O+M7GCq0DoFJW6frl85jqrIYHHZSf3/Cff5ZgaIqVPhQk1qfROr1aS5rJGz3R21ARCkr2pcWv3xaPQel6vWZpbLlMuwzMIGrENFn9L+TiEXxL+tXlHJekaH8SoUq0RIuH7/ym78A4BnGFRbLOTrjvWOJWyBwjJnJmM5FahhNCAk0lVO9kPqhvNHqXLKlYjBZsKO3zgNRA7FliXppREtlzr9xpuXDoVwr3H3yZiZM6bWmMsZ5fTMuGHoRDDebSGSAvEUV19jqc8SFEDHm0s198mKHUnrCnR8XU9CmBOG67FAndNDookh5kg676IVr9iQIDAQAB";
	// 收款方支付宝信息类型 支付宝登录号
	public final static String Transfer_payee_type = "ALIPAY_LOGONID";

	public final static String payer_show_name = "GCR商城";

	public final static String remark = "会员提现";

	public static void main(String[] args) throws AlipayApiException, IOException
	{
		//*****************充值测试***********************************************//////////
		
		 //获得初始化的AlipayClient
		 AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL,
		 AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY,
		 AlipayConfig.FORMAT, AlipayConfig.CHARSET,
		 AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);
		 AlipayTradeWapPayRequest alipayRequest=new
		 AlipayTradeWapPayRequest();
		 //设置同步回调地址
		 //alipayRequest.setReturnUrl(AlipayConfig.return_url);
		 //设置异步回调地址
		 alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
		 //设置中途退出返回按钮
		 String quit_url = "https://docs.open.alipay.com/203/107090/";
		 String no=UUID.next()+"";
		 String money=0.01+"";
		
		 alipayRequest.setBizContent("{\"out_trade_no\":"+no+","
		 + "\"total_amount\":"+money+","
		 + "\"subject\":\"手机网站支付测试\","
		 + "\"quit_url\":\""+ quit_url +"\","
		 + "\"product_code\":\"QUICK_WAP_WAY\"}");
		 AlipayTradeWapPayResponse response =
		 alipayClient.pageExecute(alipayRequest);
		 if(response.isSuccess()){
		 System.out.println("调用成功");
		 } else {
		 System.out.println("调用失败");
		 }
		 System.out.println(response.getBody());
		
		//*****************提现测试***********************************************//////////
		
//		AlipayClient alipayClient = new DefaultAlipayClient(URL, Transfer_appid, Transfer_private_key, "json", "UTF-8",
//				Transfer_public_key, "RSA2");
//		AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
//		String orderno = UUID.next() + "";
//		request.setBizContent("{" + "\"out_biz_no\":" + orderno + "," + "\"payee_type\":\"ALIPAY_LOGONID\","
//				+ "\"payee_account\":\"18339795172\"," + "\"amount\":\"8\"," + "\"payer_show_name\":\"GCR商城\","
//				+ "\"payee_real_name\":\"秦亚琦\"," + "\"remark\":\"会员提现\"" + "}");
//		AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
//		if (response.isSuccess())
//		{
//
//			// alipay_fund_trans_toaccount_transfer_response
//			String body = response.getBody();
//			System.out.println(body);
//			JSONObject bodyJson = JSONObject.fromObject(body);
//			Object transferResponse = bodyJson.get("alipay_fund_trans_toaccount_transfer_response");
//			JSONObject transferResponseJson = JSONObject.fromObject(transferResponse);
//			String code = String.valueOf(transferResponseJson.get("code"));
//			String orderId = String.valueOf(transferResponseJson.get("order_id"));
//
//			System.out.println("调用成功");
//		} else
//		{
//			System.out.println("调用失败");
//		}

	}

	// 转账到支付宝账户

	public static Map<String, String> TransferAlipay(String alipay, String amount, String out_pay_no,
			String payee_real_name) throws AlipayApiException
	{
		AlipayClient alipayClient = new DefaultAlipayClient(URL, Transfer_appid, Transfer_private_key, "json", "UTF-8",
				Transfer_public_key, "RSA2");
		AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
		request.setBizContent("{" + "\"out_biz_no\":" + out_pay_no + "," + "\"payee_type\":\"ALIPAY_LOGONID\","
				+ "\"payee_account\":\"" + alipay + "\"," + "\"amount\":\"" + amount + "\","
				+ "\"payer_show_name\":\"GCR商城\"," + "\"payee_real_name\":\"" + payee_real_name + "\","
				+ "\"remark\":\"会员提现\"" + "}");
		AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
		if (response.isSuccess())
		{
			System.out.println("调用成功");
			String body = response.getBody();
			System.out.println(body);
			JSONObject bodyJson = JSONObject.fromObject(body);
			Object transferResponse = bodyJson.get("alipay_fund_trans_toaccount_transfer_response");
			JSONObject transferResponseJson = JSONObject.fromObject(transferResponse);
			String code = String.valueOf(transferResponseJson.get("code"));
			String orderId = String.valueOf(transferResponseJson.get("order_id"));
			// String account = transferResponseJson.getString("amount");
			// String sign = transferResponseJson.getString("sign");
			Map<String, String> map = new HashMap<>();
			map.put("code", code);
			map.put("orderId", orderId);
			// map.put("account", account);
			// .put("sign", sign);
			return map;
		} else
		{
			System.out.println("调用失败");
		}
		return new HashMap<>();
	}
}
