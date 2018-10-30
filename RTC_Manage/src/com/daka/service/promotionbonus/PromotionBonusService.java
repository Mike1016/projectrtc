package com.daka.service.promotionbonus;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daka.dao.customer.CustomerDAO;
import com.daka.dao.promotionbonus.IPromotionBonusDAO;
import com.daka.dao.recharge.RechargeLogDAO;
import com.daka.entry.CustomerVO;
import com.daka.entry.Page;
import com.daka.entry.PageData;
import com.daka.entry.PromotionBonusVO;
import com.daka.entry.RechargeLogVO;
import com.daka.enums.SystemEnum;
import com.daka.service.dictionaries.DictionariesService;
import com.daka.util.DateUtils;

@Service
public class PromotionBonusService {
	@Autowired
	private IPromotionBonusDAO promotionbonusdao;
	@Autowired
	private RechargeLogDAO rechargedao;
	@Autowired
	private CustomerDAO customerdao;
	
	private final Lock backgroundPromotionLock=new ReentrantLock();
	
	/**分页查询
	 * @param page
	 * @return
	 */
	public List<PageData> querydatalistPage(Page page) {
		return promotionbonusdao.querydatalistPage(page);
	}
	
	/**激活推广奖金
	 * @param id
	 * @throws Exception 
	 */
	public void update(int id) throws Exception{
		backgroundPromotionLock.lock();
		PromotionBonusVO vo=promotionbonusdao.findById(id);
		vo.setUnfreezeMoney(vo.getAccount().multiply(new BigDecimal(DictionariesService.dictionaries.get(1501))));
		promotionbonusdao.update(vo);//激活推广奖金
		
		CustomerVO customer=customerdao.queryCustomerById(vo.getCustomerId());
		customer.setWaitingDividendsWallet(customer.getWaitingDividendsWallet().add(vo.getAccount()));
		customerdao.updateByCustomer(customer);//增加待分红
		
		RechargeLogVO rechargelog=new RechargeLogVO();
		rechargelog.setAccount(vo.getAccount());
		rechargelog.setCreateTime(DateUtils.getCurrentTimeYMDHMS());
		rechargelog.setCustomerId(vo.getCustomerId());
		rechargelog.setType(SystemEnum.PROMOTION_ACTIVATION.getCode());
		rechargelog.setRemark(SystemEnum.PROMOTION_ACTIVATION.getValue());
		rechargelog.setOrderId(vo.getId());
		rechargedao.insert(rechargelog);//添加待分红记录
		
		backgroundPromotionLock.unlock();
	}
	
	
}
