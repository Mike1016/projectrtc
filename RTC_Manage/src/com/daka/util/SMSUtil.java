package com.daka.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SMSUtil {
	private static String ENCODING = "UTF-8";
	private static String URI_SEND_SMS =
			"https://sms.yunpian.com/v2/sms/single_send.json";
	private static String APIKEY = "a8497716110be70dcbe3e7d2ec2b7241";

	/**
	 * 基于HttpClient 4.3的通用POST方法
	 *
	 * @param url       提交的URL
	 * @param paramsMap 提交<参数，值>Map
	 * @return 提交响应
	 */

	public static String post(String url, Map<String, String> paramsMap) {
		CloseableHttpClient client = HttpClients.createDefault();
		String responseText = "";
		CloseableHttpResponse response = null;
		try {
			HttpPost method = new HttpPost(url);
			if (paramsMap != null) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> param : paramsMap.entrySet()) {
					NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
					paramList.add(pair);
				}
				method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
			}
			response = client.execute(method);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseText = EntityUtils.toString(entity, ENCODING);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return responseText;
	}

	/**
	 * 智能匹配模板接口发短信
	 *
	 * @param message   　短信内容
	 * @param mobile 　接受的手机号
	 * @return json格式字符串
	 * @throws IOException
	 */

	public static String sendSms(String message,
								 String mobile) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", APIKEY);
		params.put("text", "【GCR旅游商城】您的验证码是" + message + "。如非本人操作，请忽略本短信");
		params.put("mobile", mobile);
		return post(URI_SEND_SMS, params);
	}

	public static void main(String[] args) {
		String mobile = "18710828311";
		try {
			System.out.println(SMSUtil.sendSms("1234", mobile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
