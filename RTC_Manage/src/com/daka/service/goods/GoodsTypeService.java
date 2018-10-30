package com.daka.service.goods;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daka.dao.goods.GoodsTypeDAO;
import com.daka.entry.GoodsTypeVO;
import com.daka.entry.Page;
import com.daka.entry.PageData;

@Service
public class GoodsTypeService
{
	@Autowired
	GoodsTypeDAO goodsTypeDAO;

	/**
	 * app 商品类型查询
	 *
	 * @return
	 */
	public List<GoodsTypeVO> queryAppGoodsType()
	{
		return goodsTypeDAO.queryAppGoodsType();
	}

	public void saveGoodsType(GoodsTypeVO goodsTypeVO)
	{
		goodsTypeDAO.saveGoodsType(goodsTypeVO);
	}

	public List<PageData> queryGoodslistPage(Page page)
	{
		return goodsTypeDAO.queryGoodsTypelistPage(page);
	}

	public boolean validateRepeat(String name, String id)
	{
		return goodsTypeDAO.validateRepeat(name, id) == 0 ? true : false;
	}

	public void updateGoodsType(GoodsTypeVO goodsTypeVO)
	{
		goodsTypeDAO.updateGoodsType(goodsTypeVO);
	}

	public GoodsTypeVO queryGoodsTypeById(String id)
	{

		return goodsTypeDAO.queryGoodsTypeById(id);
	}

	public void delById(String id)
	{
		goodsTypeDAO.delById(id);
	}

	public boolean validateReference(String id)
	{
		return goodsTypeDAO.validateReference(id) == 0 ? true : false;
	}

}
