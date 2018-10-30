package com.daka.service.goods;

import com.daka.dao.goods.GoodsDetailDAO;
import com.daka.entry.GoodsDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsDetailService {
    @Autowired
    GoodsDetailDAO goodsDetailDAO;

    /**
     * app 商品详情显示
     *
     * @param id
     * @return
     */
    public List<GoodsDetailVO> queryAppGoodsDetailById(int id) {
        return goodsDetailDAO.queryAppGoodsDetailById(id);
    }

    /**
     * app 商品实体图片显示
     *
     * @param id
     * @return
     */
    public List<GoodsDetailVO> queryAppGoodsImgById(int id) {
        return goodsDetailDAO.queryAppGoodsImgById(id);
    }

    public void saveGoodsDetail(GoodsDetailVO goodsDetailVO) {
        goodsDetailDAO.saveGoodsDetail(goodsDetailVO);
    }
}
