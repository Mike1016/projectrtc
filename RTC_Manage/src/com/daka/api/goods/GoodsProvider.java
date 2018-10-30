package com.daka.api.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daka.api.base.Result;
import com.daka.entry.CustomerVO;
import com.daka.entry.GoodsAttrVO;
import com.daka.entry.GoodsDetailVO;
import com.daka.entry.ShoppingCartVO;
import com.daka.entry.dto.GoodsDTO;
import com.daka.interceptor.appsession.SessionContext;
import com.daka.service.goods.GoodsAttrService;
import com.daka.service.goods.GoodsDetailService;
import com.daka.service.goods.GoodsService;
import com.daka.service.shopping.ShoppingCartService;

@RequestMapping("/app/goods")
@RestController
public class GoodsProvider {
    @Autowired
    GoodsService goodsService;

    @Autowired
    GoodsAttrService goodsAttrService;

    @Autowired
    GoodsDetailService goodsDetailService;

    @Autowired
    ShoppingCartService shoppingCartService;

    @RequestMapping("/listPageShow")
    public Result listPageShow(HttpServletRequest request) {
        String goodsName = request.getParameter("goodsName");
        String page = request.getParameter("page");
        if (page == "" || null == page) {
            page = "1";
        }
        String screenType = request.getParameter("screenType");
        List type = new ArrayList<>();

        List newGoods = new ArrayList<>();

        List priceGoods = new ArrayList<>();
        if (screenType != null && screenType != "") {
            String[] arr = screenType.split(",");
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].equals("1004")) {
                    newGoods.add(arr[i]);
                } else if (arr[i].equals("1003") || arr[i].equals("1006") || arr[i].equals("1007")
                        || arr[i].equals("1008")) {
                    type.add(arr[i]);
                } else if (arr[i].equals("1001") || arr[i].equals("1002") || arr[i].equals("1005")
                        || arr[i].equals("1004") || arr[i].equals("10021")) {
                    priceGoods.add(arr[i]);
                }
            }
        } else {
            priceGoods.add("");
        }

        String goodsType = request.getParameter("goodsType");
        String id = request.getParameter("id");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("goodsName", goodsName);
        int pageSize = Integer.valueOf(page);
        map.put("page", (pageSize - 1) * 10);
        map.put("screenType", type);
        map.put("newGoods", newGoods);
        map.put("priceGoods", priceGoods);
        map.put("typeName", goodsType);
        map.put("id", id);
        List<GoodsDTO> data = goodsService.queryAppGoods(map);
        return Result.newSuccess(data, "成功", 1);
    }

    @RequestMapping("/goodsInfo")
    public Result goodsInfo(HttpServletRequest request) {
        String strId = request.getParameter("id");
        List<GoodsAttrVO> parameter = null;
        List<GoodsDetailVO> details = null;
        List<GoodsDetailVO> imgs = null;
        int id = 0;
        if (strId != null && strId != "") {
            id = Integer.valueOf(strId);
            parameter = goodsAttrService.queryAppGoodsAttrById(id);
            details = goodsDetailService.queryAppGoodsDetailById(id);
            imgs = goodsDetailService.queryAppGoodsImgById(id);
        }
        Map<String, Object> business = goodsService.queryAppGoodsCount();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        GoodsDTO good = goodsService.queryAppGood(id);
        good.setGoodsImgs(imgs);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("good", good);
        data.put("details", details);
        data.put("parameter", parameter);
        data.put("business", business);
        return Result.newSuccess(data, "成功", 1);
    }

    @RequestMapping("/ShopCat")
    public Result shopCat(HttpServletRequest request, String sessionId) {
        CustomerVO vo = SessionContext.getConstomerInfoBySessionId(request, sessionId);
        if (vo == null)
            return Result.newSuccess("添加失败", 0);
        if (request.getParameter("id") == null)
            return Result.newSuccess("添加失败", 0);
        ShoppingCartVO cartVO = new ShoppingCartVO();
        cartVO.setCustomerId(vo.getId());
        cartVO.setGoodsId(Integer.valueOf(request.getParameter("id")));
        cartVO.setCount(Integer.valueOf(request.getParameter("count")));
        String id = request.getParameter("id");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", Integer.valueOf(request.getParameter("id")));
        map.put("count", Integer.valueOf(request.getParameter("count")));
        GoodsDTO dto = goodsService.queryAppGood(Integer.valueOf(request.getParameter("id")));
        if (dto != null) {
            if (dto.getGoodsRemainCount() < Integer.valueOf(request.getParameter("count")))
                return Result.newSuccess("商品不足", 0);
            int row = shoppingCartService.saveAppShoppingCart(cartVO);
            if (row > 0)
                return Result.newSuccess("添加成功", 1);
        }
        return Result.newSuccess("添加失败", 0);
    }

    // 发布商品
    @RequestMapping("/publishGoods")
    public Result publishGoods(HttpServletRequest request) {
        return Result.newSuccess("暂未开放", 0);
    }
}
