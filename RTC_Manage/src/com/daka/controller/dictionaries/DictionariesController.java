package com.daka.controller.dictionaries;

import com.daka.entry.Page;
import com.daka.entry.PageData;
import com.daka.service.dictionaries.DictionariesService;
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
@RequestMapping("/dictionaries")
public class DictionariesController {

    @Autowired
    private DictionariesService dictionariesService;

    @RequestMapping("/toList")
    public Object toList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("dictionaries/list");
        return mv;
    }

    @RequestMapping("/queryData")
    @ResponseBody
    public Object queryData(HttpServletRequest request) {
        PageData pd = new PageData(request);
        Page page = new Page();
        page.setPd(pd);
        List<PageData> dList = dictionariesService.queryDictionarieslistPage(page);
        JSONObject result = new JSONObject();
        result.put("status","200");
        result.put("message","OK");
        result.put("count",page.getTotalResult());
        result.put("data",dList);
        return result;
    }

    @RequestMapping("/toReview")
    public Object toReview(HttpServletRequest request) {
        String id = request.getParameter("id");
        Map<String, Object> map = dictionariesService.queryById(Integer.parseInt(id));
        ModelAndView mv = new ModelAndView("/dictionaries/review");
        mv.addObject("result", map);
        return mv;
    }

    @RequestMapping("toUpdate")
    @ResponseBody
    public Object toUpdate(HttpServletRequest request, Integer id, String parameter) {
        dictionariesService.toUpdate(id, parameter);
        JSONObject result = new JSONObject();
        result.put("status","200");
        result.put("message","OK");
        return result;
    }
}
