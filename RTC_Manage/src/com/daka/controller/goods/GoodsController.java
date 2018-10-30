package com.daka.controller.goods;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.daka.constants.SystemConstant;
import com.daka.entry.GoodsVO;
import com.daka.entry.Page;
import com.daka.entry.PageData;
import com.daka.entry.SysUserVO;
import com.daka.service.goods.GoodsService;
import com.daka.service.goods.GoodsTypeService;
import com.daka.util.Tools;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/goods")
public class GoodsController
{

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private GoodsTypeService goodsTypeService;

	@RequestMapping("/toList")
	public Object toList(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView("goods/list");
		return mv;
	}

	@RequestMapping("/queryData")
	@ResponseBody
	public Object queryData(HttpServletRequest request)
	{
		PageData pd = new PageData(request);
		Page page = new Page();
		page.setPd(pd);
		List<PageData> goodsList = goodsService.queryGoodslistPage(page);
		JSONObject result = new JSONObject();
		result.put("status", "200");
		result.put("message", "OK");
		result.put("count", page.getTotalResult());
		result.put("data", goodsList);
		return result;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Object deleteGoods(HttpServletRequest request, String id)
	{
		goodsService.deleteGoodsById(id);
		JSONObject result = new JSONObject();
		result.put("status", "200");
		result.put("message", "OK");
		return result;
	}

	@RequestMapping("/upload")
	@ResponseBody
	public Object upload(@RequestParam(value = "file", required = false) MultipartFile[] file,
			HttpServletRequest request)
	{

		File targetFile = null;
		String msg = "";// 返回存储路径
		int code = 1;
		List imgList = new ArrayList();
		if (file != null && file.length > 0)
		{
			for (int i = 0; i < file.length; i++)
			{
				String fileName = file[i].getOriginalFilename();// 获取文件名加后缀
				if (fileName != null && fileName != "")
				{
					String returnUrl = "../upload/imgs/";// 存储路径
					String path = request.getSession().getServletContext().getRealPath("upload/imgs"); // 文件存储位置
					String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());// 文件后缀
					fileName = new Date().getTime() + "_" + new Random().nextInt(1000) + fileF;// 新的文件名

					// 先判断文件是否存在
					String fileAdd = SystemConstant.DATE_FORMAT_1.format(new Date());
					File file1 = new File(path + "/" + fileAdd);
					// 如果文件夹不存在则创建
					if (!file1.exists() && !file1.isDirectory())
					{
						file1.mkdirs();
					}
					targetFile = new File(file1, fileName);
					try
					{
						file[i].transferTo(targetFile);
						msg = returnUrl + fileAdd + "/" + fileName;
						imgList.add(msg);
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		return JSONArray.fromObject(imgList).toString();

	}

	@RequestMapping("/toAddMainInfo")
	public Object toAddMainInfo(HttpServletRequest request, String id)
	{
		ModelAndView mv = new ModelAndView("goods/addMainInfo");
		mv.addObject("goodsType", goodsTypeService.queryAppGoodsType());
		return mv;
	}

	@RequestMapping("/addMainInfo")
	@ResponseBody
	public Object addMainInfo(HttpServletRequest request, GoodsVO goods)
	{
		boolean flag = true;
		try
		{
			SysUserVO userInfo = Tools.getUserInfo(request);
			goods.setCreateUser(String.valueOf(userInfo.getId()));
			goodsService.saveGoods(goods);
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
			goodsService.deleteGoodsById(id);
		} catch (Exception e)
		{
			flag = false;
		}
		return flag;
	}

	@RequestMapping("/validateGoodsName")
	@ResponseBody
	public Object validateGoodsName(HttpServletRequest request, String name)
	{
		boolean flag = true;
		try
		{
			return goodsService.validateGoodsName(name);
		} catch (Exception e)
		{
			flag = false;
		}
		return flag;
	}

	@RequestMapping("/toGoodsEdit")
	@ResponseBody
	public Object toGoodsEdit(HttpServletRequest request, String id)
	{
		ModelAndView mv = new ModelAndView("goods/edit");
		mv.addObject("goodsInfo", goodsService.queryGoodsInfoById(id));
		mv.addObject("goodsType", goodsTypeService.queryAppGoodsType());
		return mv;
	}

	@RequestMapping("/toGoodsDetail")
	@ResponseBody
	public Object toGoodsDetail(HttpServletRequest request, String id)
	{
		ModelAndView mv = new ModelAndView("goods/detail");
		mv.addObject("goodsInfo", goodsService.queryGoodsInfoById(id));
		return mv;
	}

	@RequestMapping("/updateMainInfo")
	@ResponseBody
	public Object updateMainInfo(HttpServletRequest request, GoodsVO goods)
	{
		boolean flag = true;
		try
		{
			goodsService.updateGoods(goods);
		} catch (Exception e)
		{
			flag = false;
		}
		return flag;
	}
}
