package com.daka.dao.goods;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.daka.entry.GoodsTypeVO;
import com.daka.entry.Page;
import com.daka.entry.PageData;

public interface GoodsTypeDAO
{

	List<GoodsTypeVO> queryAppGoodsType(); // app类型显示

	int validateRepeat(@Param("name") String name, @Param("id") String id); // 校验重复

	void updateGoodsType(GoodsTypeVO goodsTypeVO); // 更新

	GoodsTypeVO queryGoodsTypeById(String id); // 根据ID查询

	void saveGoodsType(GoodsTypeVO goodsTypeVO); // 新增

	List<PageData> queryGoodsTypelistPage(Page page); // 分页查询

	void delById(String id);
	
	int validateReference(String id);
}
