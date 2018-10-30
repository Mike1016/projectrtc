package com.daka.api.index;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daka.api.base.Result;
import com.daka.service.apppicture.AppPictureService;

/**
 * 资金详情
 */
@RestController
@RequestMapping("/app/index")
public class IndexProvider
{
	@Autowired
	private AppPictureService appPictureService;

	/**
	 * 体验金
	 */
	@RequestMapping("/getIndexPic")
	public Result getIndexPic(HttpServletRequest request, String sessionId)
	{
		List<String> list = null;
		try
		{
			list = appPictureService.queryImagePathByType(new Integer(4));
		} catch (Exception e)
		{
			e.printStackTrace();
			return Result.newFailure("请联系管理员");
		}
		return Result.newSuccess(list, "成功", 1);
	}
}
