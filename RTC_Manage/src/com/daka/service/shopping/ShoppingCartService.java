package com.daka.service.shopping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.daka.entry.ShoppingOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daka.dao.shopping.ShoppingCartDAO;
import com.daka.entry.ShoppingCartVO;
import com.daka.entry.dto.ShoppingCartDTO;

@Service
public class ShoppingCartService
{
	@Autowired
	ShoppingCartDAO shoppingCartDAO;

	/**
	 * app 添加购物车
	 *
	 * @param shoppingCartVO
	 */
	public int saveAppShoppingCart(ShoppingCartVO shoppingCartVO)
	{
		ShoppingCartVO shoppingCart = shoppingCartDAO.queryCartGoods(shoppingCartVO);
		if (shoppingCart != null){
			Map<String, Object> map = new HashMap<String ,Object>();
			map.put("id",shoppingCart.getId());
			map.put("count",shoppingCartVO.getCount()+shoppingCart.getCount());
			return shoppingCartDAO.updateShoppingCartCount(map);
		}
		return shoppingCartDAO.saveAppShoppingCart(shoppingCartVO);
	}

	/**
	 * app 显示购物车商品
	 * 
	 * @param customerId
	 * @return
	 */
	public List<ShoppingCartDTO> queryShoppingCartInfo(int customerId)
	{
		return shoppingCartDAO.queryShoppingCartInfo(customerId);
	}

	/**
	 * app 删除购物车数据
	 *
	 * @param map
	 * @return
	 */
	public int updateShoppingCartState(Map<String, Object> map)
	{
		return shoppingCartDAO.updateShoppingCartState(map);
	}

	/**
	 * app 修改购物想和商品的数量
	 *
	 * @param map
	 * @return
	 */
	public int updateShoppingCartCount(Map<String, Object> map)
	{
		return shoppingCartDAO.updateShoppingCartCount(map);
	}

	public ShoppingCartVO queryCartGoods(ShoppingCartVO shoppingCartVO)
	{
		return shoppingCartDAO.queryCartGoods(shoppingCartVO);
	}
}
