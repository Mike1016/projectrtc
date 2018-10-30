package com.daka.controller.bonus;

import com.daka.entry.Page;
import com.daka.entry.PageData;
import com.daka.service.bonus.BonusLogService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/bonus")
public class BonusController {

    @Autowired
    private BonusLogService bonusLogService;

    @RequestMapping("/toList")
    public Object toList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("bonus/list");
        return mv;
    }

    @RequestMapping("/queryData")
    @ResponseBody
    public Object queryData(HttpServletRequest request) {
        PageData pd = new PageData(request);
        Page page = new Page();
        page.setPd(pd);
        List<PageData> bList = bonusLogService.queryBonuslistPage(page);
        JSONObject result = new JSONObject();
        result.put("status", "200");
        result.put("message", "OK");
        result.put("count", page.getTotalResult());
        result.put("data", bList);
        return result;
    }
}
