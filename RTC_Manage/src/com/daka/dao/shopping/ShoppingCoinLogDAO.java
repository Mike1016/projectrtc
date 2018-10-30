package com.daka.dao.shopping;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.daka.entry.ShoppingCoinLogVO;

public interface ShoppingCoinLogDAO
{

	List<ShoppingCoinLogVO> selectShoppingCoinList(Integer id); // 商城币

	void saveCoin(ShoppingCoinLogVO shoppingCoinLogVO); // 消费记录

	ShoppingCoinLogVO queryCoin(int orderId); // 查询

	void saveShopCoinLog(@Param("rebate") BigDecimal rebate, @Param("proportion") BigDecimal proportion,
			@Param("remark") String remark);
}
