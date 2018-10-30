package com.daka.service.shopping;

import com.daka.dao.shopping.ShoppingCoinLogDAO;
import com.daka.entry.ShoppingCoinLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCoinLogService {
	@Autowired
	ShoppingCoinLogDAO shoppingCoinLogDAO;

	public List<ShoppingCoinLogVO> selectShoppingCoinList(Integer id) {
		return shoppingCoinLogDAO.selectShoppingCoinList(id);
	}

	/**
	 * 消费商城币记录添加
	 *
	 * @param shoppingCoinLogVO
	 */
	public void saveCoin(ShoppingCoinLogVO shoppingCoinLogVO) {
		shoppingCoinLogDAO.saveCoin(shoppingCoinLogVO);
	}

	public ShoppingCoinLogVO queryCoin(int orderId){
		return shoppingCoinLogDAO.queryCoin(orderId);
	}
}
