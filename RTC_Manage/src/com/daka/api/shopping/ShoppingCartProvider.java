package com.daka.api.shopping;

import com.daka.api.base.Result;
import com.daka.entry.CustomerVO;
import com.daka.entry.ShoppingCartVO;
import com.daka.entry.dto.GoodsDTO;
import com.daka.entry.dto.ShoppingCartDTO;
import com.daka.interceptor.appsession.SessionContext;
import com.daka.service.goods.GoodsService;
import com.daka.service.shopping.ShoppingCartService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/app/shoppingCart")
@RestController
public class ShoppingCartProvider {
    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/showInfo")
    public Result ShowInfo(HttpServletRequest request, String sessionId) {
        CustomerVO vo = SessionContext.getConstomerInfoBySessionId(request, sessionId);
        if (vo == null) return Result.newSuccess("请重新登录", 5000);
        List<ShoppingCartDTO> data = shoppingCartService.queryShoppingCartInfo(vo.getId());
        return Result.newSuccess(data,"成功", 1);
    }

    @RequestMapping("/dels")
    public Result updateIsSoftDel(HttpServletRequest request, String sessionId) {
        CustomerVO vo = SessionContext.getConstomerInfoBySessionId(request, sessionId);
        String strId = request.getParameter("ids");
        if (vo == null || strId == null || strId == "") return Result.newSuccess("删除失败", 0);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("customerId", vo.getId());
        String[] ids = strId.split(",");
        map.put("id", ids);
        int row = shoppingCartService.updateShoppingCartState(map);
        if (row > 0) return Result.newSuccess("成功", 1);
        return Result.newSuccess("删除失败", 0);
    }

    @RequestMapping("/plusOrReduce")
    public Result updateCount(HttpServletRequest request, String sessionId) {
        CustomerVO vo = SessionContext.getConstomerInfoBySessionId(request,sessionId);
        String id =request.getParameter("id");
        String count =request.getParameter("count");
        if (vo == null || id == null || id == "") return Result.newSuccess("操作失败", 0);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("count", count);
        map.put("id", id);
        int row = shoppingCartService.updateShoppingCartCount(map);
        if (row > 0) return Result.newSuccess("操作成功", 1);
        return Result.newSuccess("操作失败", 0);
    }
}
