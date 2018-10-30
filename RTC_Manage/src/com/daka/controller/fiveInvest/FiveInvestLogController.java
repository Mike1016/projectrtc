package com.daka.controller.fiveInvest;

import com.daka.entry.Page;
import com.daka.entry.PageData;
import com.daka.service.fiveInvest.FiveTimesInvestService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/fiveInvestLog")
public class FiveInvestLogController {

    @Autowired
    FiveTimesInvestService fiveTimesInvestService;

    @RequestMapping("/toList")
    public Object toList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("fiveInvest/listLog");
        return mv;
    }

    @RequestMapping("/queryData")
    public Object queryData(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            PageData pd = new PageData(request);
            Page page = new Page();
            page.setPd(pd);
            List<PageData> fiveInvestLogList = fiveTimesInvestService.queryFiveInvestLoglistPage(page);
            result.put("status", "200");
            result.put("message", "OK");
            result.put("count",page.getTotalResult());
            result.put("data",fiveInvestLogList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
