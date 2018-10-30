package com.daka.controller.rebate;

import com.daka.api.base.Result;
import com.daka.entry.RebateLogVO;
import com.daka.service.rebate.RebateLogService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: mike
 * @Description:
 * @Date: Created in 9:50 2018/10/24
 * @Modified By:
 */
@Controller
@RequestMapping("/rebate")
public class RebateLogController {
    @Autowired
    private RebateLogService rebateLogService;

    /**
     * 导航到list.jsp
     * @date: 11:42 2018/10/24
     * @param: [request]
     * @return: java.lang.Object
     *
     */
    @RequestMapping("/toList")
    public Object toList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("dataanalyse/list");
        return mv;
    }
    /**
     * 按照时间段和type类型统计 一级 二级 三级的返利数据
     *
     * @date: 10:09 2018/10/24
     * @param: [beginTime, endTime, type]
     * @return: java.util.Map<java.lang.Integer   ,   java.math.BigDecimal>
     */

    @RequestMapping("/queryDataByType")
    @ResponseBody
    public Object queryDataByType(String beginTime, String endTime, Integer type) {
        try {
            List<RebateLogVO> logVOList = rebateLogService.queryAllRebateByType(beginTime, endTime, type);
            JSONObject result = new JSONObject();
            result.put("status", "200");
            result.put("message", "OK");
            result.put("data", logVOList);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.newFailure("查询失败", 0);
        }
    }
}
