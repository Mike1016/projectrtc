package com.daka.controller.promotionbonus;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.daka.entry.Page;
import com.daka.entry.PageData;
import com.daka.service.promotionbonus.PromotionBonusService;

@RequestMapping("/promotionBonus")
@Controller
public class PromotionBonusController {
	@Autowired
	private PromotionBonusService promotionbonusservice;
	
	@RequestMapping("/toList")
	public Object toList(HttpServletRequest request){
		ModelAndView mv=new ModelAndView("promotionBonus/list");
		return mv;
	}
	
	@RequestMapping("/querydata")
	@ResponseBody
	public Object querydata(HttpServletRequest request){
		 PageData pd = new PageData(request);
	     Page page = new Page();
	     page.setPd(pd);
	     List<PageData> list=promotionbonusservice.querydatalistPage(page);
	     JSONObject result = new JSONObject();
         result.put("status", "200");
         result.put("message", "OK");
         result.put("count",page.getTotalResult());
         result.put("data",list);
		return result;
	}
	
	/**激活推广奖金
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Object updatePromotionbonus(HttpServletRequest request,int id){
		JSONObject result=new JSONObject();
		String status="";
		String message="";
		try {
			promotionbonusservice.update(id);
			status="200";
			message="修改成功";
		} catch (Exception e) {
			e.printStackTrace();
			status="500";
			message="操作失败";
		}
		result.put("status", status);
		result.put("message", message);
		return result;
	}

}
