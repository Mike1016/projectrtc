package com.daka.dao.goods;

import com.daka.entry.GoodsAttrVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsAttrDAO {

    List<GoodsAttrVO> queryAppGoodsAttrById(@Param("id") int id); //app商品参数

    void saveGoodsAttr(GoodsAttrVO goodsAttrVO);
}
