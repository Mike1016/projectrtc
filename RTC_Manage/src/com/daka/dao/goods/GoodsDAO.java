package com.daka.dao.goods;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.daka.entry.GoodsAttrVO;
import com.daka.entry.GoodsDetailVO;
import com.daka.entry.GoodsVO;
import com.daka.entry.Page;
import com.daka.entry.PageData;
import com.daka.entry.dto.GoodsDTO;

public interface GoodsDAO
{

	List<PageData> queryGoodslistPage(Page page); // 分页查询

	void saveGoods(GoodsVO goodsVO); // 保存商品信息

	void deleteGoodsById(String id); // 根据id删除商品信息

	void batchSaveAttr(List<GoodsAttrVO> goodsVO);

	void batchSaveDetail(List<GoodsDetailVO> goodsVO);

	List<GoodsDTO> queryAppGoods(Map<String, Object> map); // app商品显示

	GoodsDTO queryAppGood(@Param("id") int id); // app商品显示

	Map<String, Object> queryAppGoodsCount(); // app商品数量

	int queryGoodsCountByName(String name);

	GoodsVO queryGoodsInfoById(String id);

	List<GoodsAttrVO> queryGoodsAttrByGoodsId(String id);

	List<GoodsDetailVO> queryGoodsDetailByGoodsId(String id);

	void updateGoods(GoodsVO goods);

	void delGoodsDetailByGoodsId(Integer id);

	void delGoodsAttrByGoodsId(Integer id);

    void updateGoodsCountReduce(List<Map<String, String>> map); //修改商品剩余量(减)

    void updateGoodsCountPlus(List<Map<String ,String>> map); //修改商品剩余量(加)
}
