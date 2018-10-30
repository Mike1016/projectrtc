package com.daka.controller.goods;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.daka.entry.GoodsTypeVO;
import com.daka.entry.Page;
import com.daka.entry.PageData;
import com.daka.service.goods.GoodsTypeService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/goodsType")
public class GoodsTypeController
{

	@Autowired
	private GoodsTypeService goodsTypeService;

	@RequestMapping("/toList")
	public Object toList(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView("goodsType/list");
		return mv;
	}

	@RequestMapping("/toAdd")
	public Object toAdd(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView("goodsType/edit_add");
		return mv;
	}

	@RequestMapping("/queryData")
	@ResponseBody
	public Object queryData(HttpServletRequest request)
	{
		PageData pd = new PageData(request);
		Page page = new Page();
		page.setPd(pd);
		List<PageData> goodsList = goodsTypeService.queryGoodslistPage(page);
		JSONObject result = new JSONObject();
		result.put("status", "200");
		result.put("message", "OK");
		result.put("count", page.getTotalResult());
		result.put("data", goodsList);
		return result;
	}

	@RequestMapping("/toEdit")
	public Object toEdit(HttpServletRequest request, String id)
	{
		ModelAndView mv = new ModelAndView("goodsType/edit_add");
		mv.addObject("result", goodsTypeService.queryGoodsTypeById(id));
		return mv;
	}

	@RequestMapping("/update")
	@ResponseBody
	public Object update(HttpServletRequest request, GoodsTypeVO goodsTypeVO)
	{
		boolean flag = true;
		try
		{
			goodsTypeService.updateGoodsType(goodsTypeVO);
		} catch (Exception e)
		{
			flag = false;
		}
		return flag;
	}

	@RequestMapping("/save")
	@ResponseBody
	public Object save(HttpServletRequest request, GoodsTypeVO goodsTypeVO)
	{
		boolean flag = true;
		try
		{
			goodsTypeService.saveGoodsType(goodsTypeVO);
		} catch (Exception e)
		{
			flag = false;
		}
		return flag;
	}

	@RequestMapping("/validateName")
	@ResponseBody
	public Object validateName(HttpServletRequest request, String name, String id)
	{
		boolean flag = true;
		try
		{
			return goodsTypeService.validateRepeat(name, id);
		} catch (Exception e)
		{
			flag = false;
		}
		return flag;
	}

	@RequestMapping("/del")
	@ResponseBody
	public Object del(HttpServletRequest request, String id)
	{
		boolean flag = true;
		try
		{
			goodsTypeService.delById(id);
		} catch (Exception e)
		{
			flag = false;
		}
		return flag;
	}

	@RequestMapping("/validateReference")
	@ResponseBody
	public Object validateReference(HttpServletRequest request, String id)
	{
		boolean flag = true;
		try
		{
			return goodsTypeService.validateReference(id);
		} catch (Exception e)
		{
			flag = false;
		}
		return flag;
	}
}
