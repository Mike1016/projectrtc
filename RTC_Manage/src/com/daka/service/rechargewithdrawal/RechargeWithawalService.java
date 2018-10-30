package com.daka.service.rechargewithdrawal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daka.dao.recharge.RechargePutforwardOrderDao;
import com.daka.entry.Page;
import com.daka.entry.PageData;

@Service
public class RechargeWithawalService {
	@Autowired
	private RechargePutforwardOrderDao rechargeorderdao;
	
	/**分页查询
	 * @param page
	 * @return
	 */
	public List<PageData> queryrechargeorderlistPage(Page page){
		return rechargeorderdao.queryrechargeorderlistPage(page);
	}

}
