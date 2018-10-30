package com.daka.service.recharge;

import com.daka.constants.SystemConstant;
import com.daka.dao.recharge.RechargeLogDAO;
import com.daka.entry.Page;
import com.daka.entry.PageData;
import com.daka.entry.RechargeLogVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RechargeLogService {

	@Autowired
	private RechargeLogDAO rechargeLogDAO;

	public List<PageData> queryDividendslistPage(Page page) {
		return rechargeLogDAO.queryDividendslistPage(page);
	}

	public List<RechargeLogVO> selectRechargeLog(Integer id, String types) {
		return rechargeLogDAO.selectRechargeLog(id, types);
	}
	
	public boolean checkstatus(Integer customerId){
		Map<String, Object> map=new HashMap<>();
		map.put("customerId", customerId);
		map.put("type", 1);
		BigDecimal accumulativeRecharge=rechargeLogDAO.findAllByType(map);
		map.put("type", "2,5");
		BigDecimal cumulativeMultiple=rechargeLogDAO.findAllByType(map);
		int x=SystemConstant.LIMITATION_OF_CASH.compareTo(accumulativeRecharge);//累计充值金额是否小于提现限制金额
		int y=SystemConstant.LIMITATION_OF_CASH.compareTo(cumulativeMultiple);//累计复投金额是否小于提现限制金额
		if(x<0||y<0){
			return false;
		}
		return true;
	}

	//查询累计充值/复投的钱
	public BigDecimal findAllByType(Map<String, Object> map) {
		return rechargeLogDAO.findAllByType(map);
	}

	public BigDecimal queryAccountByType(Integer customerId) {
		return rechargeLogDAO.queryAccountByType(customerId);
	}

	public void insert(RechargeLogVO vo) {
		rechargeLogDAO.insert(vo);
	}
}
