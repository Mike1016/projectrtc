package com.daka.controller.extension;

import com.daka.entry.CustomerVO;
import com.daka.service.customer.CustomerService;
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
@RequestMapping("/extension")
public class ExtensionController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping("/toList")
    public Object toList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("extension/list");
        return mv;
    }

    @RequestMapping("/toReview")
    public Object toReview(HttpServletRequest request) {
        String id = request.getParameter("id");
        List<CustomerVO> eList = customerService.queryExtension(Integer.parseInt(id));
        ModelAndView mv = new ModelAndView("/extension/personList");
        mv.addObject("data", eList);
        return mv;
    }

    @RequestMapping("/toReviewList")
    @ResponseBody
    public Object toReviewList(HttpServletRequest request,String id) {
        id = id.substring(id.lastIndexOf("=")+1,id.length());
        List<CustomerVO> eList = customerService.queryExtension(Integer.parseInt(id));
        JSONObject result = new JSONObject();
        result.put("status", "200");
        result.put("message", "OK");
        result.put("data", eList);
        return result;
    }

    @RequestMapping("/toTeam")
    public Object toTeam(HttpServletRequest request) {
        String id = request.getParameter("id");
        List<CustomerVO> tList = customerService.queryTeam(Integer.valueOf(id));
        ModelAndView mv = new ModelAndView("/extension/teamList");
        mv.addObject("data", tList);
        return mv;
    }

    @RequestMapping("/toTeamList")
    @ResponseBody
    public Object toTeamList(HttpServletRequest request,String id) {
        id = id.substring(id.lastIndexOf("=")+1,id.length());
        List<CustomerVO> tList = customerService.queryTeam(Integer.valueOf(id));
        JSONObject result = new JSONObject();
        result.put("status", "200");
        result.put("message", "OK");
        result.put("data", tList);
        return result;
    }
}
