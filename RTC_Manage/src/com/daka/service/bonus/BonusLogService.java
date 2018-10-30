package com.daka.service.bonus;

import com.daka.dao.bonus.BonusLogDAO;
import com.daka.entry.*;
import com.daka.service.customer.CustomerService;
import com.daka.service.putforward.PutforwardService;
import com.daka.util.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BonusLogService {
	@Autowired
	private BonusLogDAO bonusLogDAO;
	@Autowired
	private PutforwardService pfs;
	@Autowired
	private CustomerService cms;

	public List<PageData> queryBonuslistPage(Page page) {
		return bonusLogDAO.queryBonuslistPage(page);
	}

	public List<BonusLogVO> selectBonusList(Integer id, String type) {
		return bonusLogDAO.selectBonusList(id, type);
	}
	
	public void insert(BonusLogVO vo){//增加余额钱包钱币变化的记录
		bonusLogDAO.insert(vo);
	}
	
	public void cashFailure(int id){//提现失败，钱币返还给用户
		PutforwardLogVO vo=pfs.findById(id);
		vo.setState(2);
		pfs.updateState(vo);//提现审核不通过
		
		BonusLogVO bvo=new BonusLogVO();
		bvo.setCustomerId(vo.getCustomerId());
		bvo.setAccount(vo.getAccount());
		bvo.setCreateTime(DateUtils.getCurrentTimeYMDHMS());
		bvo.setType(4);
		bvo.setRemark("提现失败，退款");
		bonusLogDAO.insert(bvo);//提现失败，退款
		
		CustomerVO cvo=cms.queryCustomerById(vo.getCustomerId());
		if(vo.getType()==1){
			cvo.setDividendsWallet(cvo.getDividendsWallet().add(vo.getAccount()));
		}
		if(vo.getType()==2){
			cvo.setRebateWallet(cvo.getRebateWallet().add(vo.getAccount()));
		}
		cms.updateByCustomer(cvo);//提现失败，退款
	}
	
	public BigDecimal findAllBycusid(Integer customerId){
		return bonusLogDAO.findAllBycusid(customerId);
	}
}
