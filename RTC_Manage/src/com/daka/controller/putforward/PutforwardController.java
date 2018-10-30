package com.daka.controller.putforward;

import com.daka.entry.Page;
import com.daka.entry.PageData;
import com.daka.entry.PutforwardLogVO;
import com.daka.service.bonus.BonusLogService;
import com.daka.service.putforward.PutforwardService;
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
@RequestMapping("/putforward")
public class PutforwardController {

    @Autowired
    private PutforwardService putforwardService;

    @Autowired
    private BonusLogService bonusLogService;

    @RequestMapping("/toList")
    public Object toList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("putforward/list");
        return mv;
    }

    @RequestMapping("/queryData")
    @ResponseBody
    public Object queryData(HttpServletRequest request) {
        PageData pd = new PageData(request);
        Page page = new Page();
        page.setPd(pd);
        List<PageData> pList = putforwardService.queryPutforwardlistPage(page);
        JSONObject result = new JSONObject();
        result.put("status","200");
        result.put("message","OK");
        result.put("count",page.getTotalResult());
        result.put("data",pList);
        return result;
    }

    @RequestMapping("/toReview")
    public Object toReview(HttpServletRequest request)
    {
        String id = request.getParameter("cId");
        Map<String, Object> map = putforwardService.queryById(Integer.parseInt(id));
        ModelAndView mv = new ModelAndView("/putforward/review");
        mv.addObject("result", map);
        return mv;
    }

    @RequestMapping("/updateState")
    @ResponseBody
    public Object updateState(HttpServletRequest request, PutforwardLogVO putforwardLogVO) {
        if (putforwardLogVO.getState() == 1) {
            putforwardService.updateState(putforwardLogVO);
        } else if (putforwardLogVO.getState() == 2) {
            bonusLogService.cashFailure(putforwardLogVO.getId());
        }
        JSONObject result = new JSONObject();
        result.put("status","200");
        result.put("message","OK");
        return result;
    }

}
