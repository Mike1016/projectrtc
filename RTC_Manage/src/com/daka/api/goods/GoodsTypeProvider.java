package com.daka.api.goods;

import com.daka.api.base.Result;
import com.daka.dao.goods.GoodsTypeDAO;
import com.daka.entry.GoodsTypeVO;
import com.daka.service.goods.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/app/goodsType")
@RestController
public class GoodsTypeProvider {
    @Autowired
    GoodsTypeService goodsTypeService;

    @RequestMapping("/typeShow")
    public Result typeShow() {
        List<GoodsTypeVO> data = goodsTypeService.queryAppGoodsType();
        return Result.newSuccess(data, "成功",1);
    }
}
