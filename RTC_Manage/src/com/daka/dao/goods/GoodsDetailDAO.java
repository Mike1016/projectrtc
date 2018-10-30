package com.daka.dao.goods;

import com.daka.entry.GoodsDetailVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsDetailDAO {

    List<GoodsDetailVO> queryAppGoodsDetailById(@Param("id") int id); //app 商品详情

    List<GoodsDetailVO> queryAppGoodsImgById(@Param("id") int id); //app 商品实体图片

    void saveGoodsDetail(GoodsDetailVO goodsDetailVO);
}
