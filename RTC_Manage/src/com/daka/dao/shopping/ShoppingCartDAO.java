package com.daka.dao.shopping;

import java.util.List;
import java.util.Map;

import com.daka.entry.ShoppingCartVO;
import com.daka.entry.dto.ShoppingCartDTO;
import org.apache.ibatis.annotations.Param;

public interface ShoppingCartDAO
{

	int saveAppShoppingCart(ShoppingCartVO shoppingCartVO); // app 添加购物车

	List<ShoppingCartDTO> queryShoppingCartInfo(int customerId); // app 查询购物车商品

	int updateShoppingCartState(Map<String, Object> map);// app 删除购物车商品

	int updateShoppingCartCount(Map<String, Object> map); // app 修改数量

	ShoppingCartVO queryCartGoods(ShoppingCartVO shoppingCartVO);

	void updateCartState(@Param("goodsId") String goodsId);
}
