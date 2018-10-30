package com.daka.service.goods;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daka.dao.goods.GoodsDAO;
import com.daka.dao.user.SysUserDAO;
import com.daka.entry.GoodsAttrVO;
import com.daka.entry.GoodsDetailVO;
import com.daka.entry.GoodsVO;
import com.daka.entry.Page;
import com.daka.entry.PageData;
import com.daka.entry.dto.GoodsDTO;
import com.daka.enums.GoodsDetailEnum;

@Service
public class GoodsService
{

	@Autowired
	private GoodsDAO goodsDao;

	@Autowired
	private SysUserDAO userDao;

	/**
	 * 分页查询所有商品信息
	 * 
	 * @param page
	 * @return
	 */
	public List<PageData> queryGoodslistPage(Page page)
	{
		return goodsDao.queryGoodslistPage(page);
	}

	/**
	 * 保存商品
	 * 
	 * @param goodsVO
	 */
	public void saveGoods(GoodsVO goodsVO)
	{

		goodsDao.saveGoods(goodsVO);

		int goodsId = goodsVO.getId();

		saveGoodDetail(goodsVO, goodsId);
	}

	private void saveGoodDetail(GoodsVO goodsVO, int goodsId)
	{
		String attrN = Optional.ofNullable(goodsVO.getAttrN()).orElse("");
		String attrV = Optional.ofNullable(goodsVO.getAttrV()).orElse("");

		String[] attrNArr = attrN.split(",");
		String[] attrVArr = attrV.split(",");

		if (!(attrN.isEmpty() || attrV.isEmpty() || attrNArr.length != attrVArr.length))
		{
			List<GoodsAttrVO> attrVos = new ArrayList<>();
			for (int i = 0; i < attrNArr.length; i++)
			{
				GoodsAttrVO temp = new GoodsAttrVO();
				temp.setGoodsId(goodsId);
				temp.setAttrName(attrNArr[i]);
				temp.setAttrValue(attrVArr[i]);
				attrVos.add(temp);
			}

			goodsDao.batchSaveAttr(attrVos);
		}

		String coverImgs = Optional.ofNullable(goodsVO.getCoverImgs()).orElse("");
		String deitalImgs = Optional.ofNullable(goodsVO.getDeitalImgs()).orElse("");

		List<GoodsDetailVO> goodsDetailVOs = new ArrayList<>();
		if (!coverImgs.isEmpty())
		{
			String[] coverImgsArr = coverImgs.split(",");
			for (String str : coverImgsArr)
			{
				GoodsDetailVO temp = new GoodsDetailVO();
				temp.setGoodsId(goodsId);
				temp.setType(GoodsDetailEnum.COVER.getValue());
				temp.setGoodsAttachmentContent(str);
				goodsDetailVOs.add(temp);
			}

		}
		if (!deitalImgs.isEmpty())
		{
			String[] deitalImgsArr = deitalImgs.split(",");
			for (String str : deitalImgsArr)
			{
				GoodsDetailVO temp = new GoodsDetailVO();
				temp.setGoodsId(goodsId);
				temp.setType(GoodsDetailEnum.DETAIL.getValue());
				temp.setGoodsAttachmentContent(str);
				goodsDetailVOs.add(temp);
			}
		}

		if (!goodsDetailVOs.isEmpty())
		{
			goodsDao.batchSaveDetail(goodsDetailVOs);
		}
	}

	/**
	 * 根据id删除商品信息
	 * 
	 * @param id
	 */
	public void deleteGoodsById(String id)
	{
		goodsDao.deleteGoodsById(id);
	}

	/**
	 * app商品显示 多个
	 *
	 * @param map
	 * @return
	 */
	public List<GoodsDTO> queryAppGoods(Map<String, Object> map)
	{
		return goodsDao.queryAppGoods(map);
	}

	/**
	 * app商品显示 一个
	 *
	 * @param id
	 * @return
	 */
	public GoodsDTO queryAppGood(int id)
	{
		return goodsDao.queryAppGood(id);
	}

	/**
	 * app查询商品总数、新品数、促销数
	 *
	 * @return
	 */
	public Map<String, Object> queryAppGoodsCount()
	{
		return goodsDao.queryAppGoodsCount();
	}

	public boolean validateGoodsName(String name)
	{
		return goodsDao.queryGoodsCountByName(name) == 0 ? true : false;
	}

	public Object queryGoodsInfoById(String id)
	{
		GoodsVO goodsInfo = goodsDao.queryGoodsInfoById(id);
		goodsInfo.setGoodsAttrs(goodsDao.queryGoodsAttrByGoodsId(id));
		goodsInfo.setGoodsDetailVOs(goodsDao.queryGoodsDetailByGoodsId(id));
		goodsInfo.setCreateUser(userDao.queryUserById(Integer.parseInt(goodsInfo.getCreateUser())).getUsername());
		return goodsInfo;
	}

	public void updateGoods(GoodsVO goods)
	{
		goodsDao.updateGoods(goods);
		goodsDao.delGoodsDetailByGoodsId(goods.getId());
		goodsDao.delGoodsAttrByGoodsId(goods.getId());
		saveGoodDetail(goods, goods.getId());
	}


    /**
     * 修改商品剩余量(减)
     * @param map
     */
    void updateGoodsCountReduce(List<Map<String ,String>> map){
        goodsDao.updateGoodsCountReduce(map);
    }

    /**
     * 修改商品剩余量(加)
     * @param map
     */
    void updateGoodsCountPlus(List<Map<String ,String>> map){
        goodsDao.updateGoodsCountPlus(map);
    }
}
