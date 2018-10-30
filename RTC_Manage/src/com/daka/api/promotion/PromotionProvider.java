package com.daka.api.promotion;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daka.api.base.Result;
import com.daka.entry.CustomerVO;
import com.daka.entry.PromotionBonusVO;
import com.daka.interceptor.appsession.SessionContext;
import com.daka.service.promotion.PromotionService;

@RequestMapping("/app/promotion")
@RestController
public class PromotionProvider
{

	@Autowired
	private PromotionService promotionService;

	/**
	 * 获取当前用户的推广奖金记录
	 * 
	 * @param request
	 * @param sessionId
	 * @return
	 */
	@RequestMapping("/queryCurrentPromotionData")
	public Result queryCurrentPromotionData(HttpServletRequest request, String sessionId)
	{
		CustomerVO customerVO = SessionContext.getConstomerInfoBySessionId(request, sessionId);
		List<PromotionBonusVO> result;
		try
		{
			result = promotionService.queryCurrentPromotionData(customerVO);
		} catch (Exception e)
		{
			e.printStackTrace();
			return Result.newFailure("获取数据失败", 0);
		}
		return Result.newSuccess(result, "成功", 1);
	}

	/**
	 * 激活推广奖金(单条)
	 * 
	 * @param request
	 * @param sessionId
	 * @return
	 */
	@RequestMapping("/unfreezeOnePromotionData")
	public Result unfreezeOnePromotionData(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			promotionService.unfreezeOneRecord(request, response);
		} catch (Exception e)
		{
			e.printStackTrace();
			return Result.newFailure("激活失败", 0);
		}
		return Result.newSuccess("激活成功", 1);
	}

	/**
	 * 激活推广奖金
	 * 
	 * @param request
	 * @param sessionId
	 * @return
	 */
	@RequestMapping("/unfreezeAllPromotionData")
	public Result unfreezeAllPromotionData(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			promotionService.unfreezeAllRecord(request, response);
		} catch (Exception e)
		{
			e.printStackTrace();
			return Result.newFailure("激活失败", 0);
		}
		return Result.newSuccess("激活成功", 1);
	}

}
