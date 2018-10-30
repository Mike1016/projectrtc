package com.daka.controller.rechargewithdrawal;

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
import com.daka.service.rechargewithdrawal.RechargeWithawalService;

@RequestMapping("/rechargeWithdrawal")
@Controller
public class RechargeWithdrawalController {
	@Autowired
	private RechargeWithawalService rechargeservice;
	
	@RequestMapping("/toList")
	public Object toList(HttpServletRequest request){
		ModelAndView mv=new ModelAndView("rechargeRecord/list");
		return mv;
	}
	
	@RequestMapping("/queryData")
	@ResponseBody
	public Object queryData(HttpServletRequest request){
		PageData pd = new PageData(request);
        Page page = new Page();
        page.setPd(pd);
        List<PageData> list= rechargeservice.queryrechargeorderlistPage(page);
        JSONObject result = new JSONObject();
        result.put("status", "200");
        result.put("message", "OK");
        result.put("count", page.getTotalResult());
        result.put("data", list);
        return result;
	}

}
