package com.daka.service.goods;

import com.daka.dao.goods.GoodsAttrDAO;
import com.daka.entry.GoodsAttrVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsAttrService {
    @Autowired
    GoodsAttrDAO goodsAttrDAO;

    /**
     * app商品参数
     * @param id
     * @return
     */
    public List<GoodsAttrVO> queryAppGoodsAttrById(int id){
        return goodsAttrDAO.queryAppGoodsAttrById(id);
    }

    public void saveGoodsAttr(GoodsAttrVO goodsAttrVO) {
        goodsAttrDAO.saveGoodsAttr(goodsAttrVO);
    }
}
