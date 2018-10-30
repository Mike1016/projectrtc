package com.daka.controller.shopping;

import com.daka.entry.Page;
import com.daka.entry.PageData;
import com.daka.entry.ShoppingOrderVO;
import com.daka.service.shopping.ShoppingOrderService;
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
@RequestMapping("/shoppingOrder")
public class ShoppingOrderController {

    @Autowired
    private ShoppingOrderService shoppingOrderService;

    @RequestMapping("/toList")
    public Object toList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("shoppingOrder/list");
        return mv;
    }

    @RequestMapping("/queryData")
    @ResponseBody
    public Object queryData(HttpServletRequest request) {
        PageData pd = new PageData(request);
        Page page = new Page();
        page.setPd(pd);
        List<PageData> sList = shoppingOrderService.queryShoppingOrderlistPage(page);
        JSONObject result = new JSONObject();
        result.put("status", "200");
        result.put("message", "OK");
        result.put("count",page.getTotalResult());
        result.put("data",sList);
        return result;
    }

    @RequestMapping("/toReview")
    public Object toReview(HttpServletRequest request)
    {
        String id = request.getParameter("id");
        Map<String,Object> map = shoppingOrderService.queryByOrderId(Integer.parseInt(id));
        ModelAndView mv = new ModelAndView("/shoppingOrder/review");
        mv.addObject("result", map);
        return mv;
    }

    @RequestMapping("/toUpdate")
    @ResponseBody
    public Object toUpdate(HttpServletRequest request,ShoppingOrderVO shoppingOrderVO) {
        if (shoppingOrderVO.getState() == 6) {
            shoppingOrderService.toUpdate(shoppingOrderVO);
            shoppingOrderService.updateRefund(shoppingOrderVO.getState(),shoppingOrderVO.getId(),shoppingOrderVO.getCustomerId());
        } else {
            shoppingOrderService.toUpdate(shoppingOrderVO);
        }
        JSONObject result = new JSONObject();
        result.put("status","200");
        result.put("message","OK");
        return result;
    }

    @RequestMapping("/updateState")
    @ResponseBody
    public Object updateState(HttpServletRequest request,ShoppingOrderVO shoppingOrderVO) {
        shoppingOrderService.updateState(shoppingOrderVO);
        JSONObject result = new JSONObject();
        result.put("status","200");
        result.put("message","OK");
        return result;
    }
}
