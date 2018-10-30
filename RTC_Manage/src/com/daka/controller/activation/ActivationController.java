package com.daka.controller.activation;

import com.daka.entry.ActivationCodeVO;
import com.daka.entry.Page;
import com.daka.entry.PageData;
import com.daka.service.activation.ActivationCodeService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/activation")
public class ActivationController {

    @Autowired
    private ActivationCodeService activationCodeService;

    @RequestMapping("/toList")
    public Object toList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("activation/list");
        return mv;
    }

    @RequestMapping("/queryData")
    @ResponseBody
    public Object queryData(HttpServletRequest request) {
        PageData pd = new PageData(request);
        Page page = new Page();
        page.setPd(pd);
        List<PageData> cList = activationCodeService.queryActivationCodelistPage(page);
        JSONObject result = new JSONObject();
        result.put("status", "200");
        result.put("message", "OK");
        result.put("count", page.getTotalResult());
        result.put("data", cList);
        return result;
    }

    @RequestMapping("/save")
    @ResponseBody
    public Object saveActivationCode(HttpServletRequest request, ActivationCodeVO activationCodeVO, String length) {
        activationCodeService.queryCode();
        activationCodeService.insertBatchActivationCode(Integer.valueOf((length)));
        JSONObject result = new JSONObject();
        result.put("status", "200");
        result.put("message", "OK");
        return result;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Object deleteCode(HttpServletRequest request,Integer id) {
        activationCodeService.deleteCode(id);
        JSONObject result = new JSONObject();
        result.put("status", "200");
        result.put("message", "OK");
        return result;
    }
}
