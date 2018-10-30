package com.daka.controller.present;

import com.daka.entry.*;
import com.daka.service.present.PresentService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/present")
public class PresentController {

    @Autowired
    private PresentService presentService;

    @RequestMapping("/toList")
    public Object toList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("present/list");
        return mv;
    }

    @RequestMapping("/queryData")
    @ResponseBody
    public Object queryData(HttpServletRequest request) {
        PageData pd = new PageData(request);
        Page page = new Page();
        page.setPd(pd);
        List<PageData> pList = presentService.queryPresentlistPage(page);
        JSONObject result = new JSONObject();
        result.put("status","200");
        result.put("message","OK");
        result.put("count",page.getTotalResult());
        result.put("data",pList);
        return result;
    }

    @RequestMapping("/toReview")
    public Object toReview(HttpServletRequest request) {
        String id = request.getParameter("id");
        Map<String,Object> map = presentService.queryPersentById(Integer.parseInt(id));
        ModelAndView mv = new ModelAndView("/present/review");
        mv.addObject("result", map);
        return mv;
    }

    @RequestMapping("/toUpdate")
    @ResponseBody
    public Object toUpdate(HttpServletRequest request, PresentVO presentVO) {
        presentService.toUpdate(presentVO);
        JSONObject result = new JSONObject();
        result.put("status","200");
        result.put("message","OK");
        return result;
    }

}
