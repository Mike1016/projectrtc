package com.daka.controller.ranking;


import com.daka.entry.Page;
import com.daka.entry.PageData;
import com.daka.entry.RankingVO;
import com.daka.service.ranking.RankingService;
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
@RequestMapping("/ranking")
public class RankingController {

    @Autowired
    private RankingService rankingService;

    @RequestMapping("/toList")
    public Object toList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("ranking/list");
        return mv;
    }

    @RequestMapping("/queryData")
    @ResponseBody
    public Object queryData(HttpServletRequest request) {
        PageData pd = new PageData(request);
        Page page = new Page();
        page.setPd(pd);
        List<PageData> rList = rankingService.queryRankinglistPage(page);
        JSONObject result = new JSONObject();
        result.put("status", "200");
        result.put("message", "OK");
        result.put("count", page.getTotalResult());
        result.put("data", rList);
        return result;
    }

    @RequestMapping("/toAdd")
    public Object toAdd(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("ranking/add");
        return mv;
    }

    @RequestMapping("/insertAccount")
    @ResponseBody
    public Object insertAccount(HttpServletRequest request, RankingVO rankingVO) {
        if (rankingService.queryPhone(rankingVO.getPhone()) == null) {
            rankingService.insertAccount(rankingVO);
            JSONObject result = new JSONObject();
            result.put("status", "200");
            result.put("message", "OK");
            return result;
        } else {
            JSONObject result = new JSONObject();
            result.put("status", "500");
            result.put("message", "电话号码有误!");
            return result;
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(HttpServletRequest request,String id) {
        rankingService.deleteById(Integer.parseInt(id));
        JSONObject result = new JSONObject();
        result.put("status", "200");
        result.put("message", "OK");
        return result;
    }

    @RequestMapping("/toReview")
    public Object toReview(HttpServletRequest request) {
        String id = request.getParameter("id");
        Map<String, Object> map = rankingService.queryRankingById(Integer.parseInt(id));
        ModelAndView mv = new ModelAndView("/ranking/review");
        mv.addObject("result", map);
        return mv;
    }

    @RequestMapping("/updateRanking")
    @ResponseBody
    public Object updateRanking(HttpServletRequest request,RankingVO rankingVO) {
        rankingService.updateRanking(rankingVO);
        JSONObject result = new JSONObject();
        result.put("status", "200");
        result.put("message", "OK");
        return result;
    }
}
