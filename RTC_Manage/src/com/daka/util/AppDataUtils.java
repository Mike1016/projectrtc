package com.daka.util;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.daka.constants.SystemConstant;

public class AppDataUtils
{

	/**
	 * 转换返回到前台的数据
	 * 
	 * @param data
	 *            --数据 List或者map或者其他VO
	 * @param isSuccess
	 *            是否成功
	 * @return 返回到前台的数据
	 */
	public static JSONObject parseDisplayData(Object data, boolean... isSuccess)
	{
		JSONObject result = new JSONObject();
		if (null != isSuccess && isSuccess.length > 0 && isSuccess[0])
		{
			if (data instanceof List)
			{
				result.put("data", JSONArray.fromObject(data));
			} else
			{
				result.put("data", JSONObject.fromObject(data));
			}
			result.put("status", SystemConstant.SUCCESS_CODE);
		} else
		{
			result.put("status", SystemConstant.FAIL_CODE);
			result.put("data", new JSONObject());

		}
		return result;
	}

}
