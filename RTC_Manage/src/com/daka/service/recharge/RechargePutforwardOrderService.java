package com.daka.service.recharge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.api.domain.Data;
import com.daka.dao.recharge.RechargePutforwardOrderDao;
import com.daka.entry.RechargePutforwardOrderVO;
import com.daka.util.DateUtil;

@Service
public class RechargePutforwardOrderService {
	@Autowired
	private RechargePutforwardOrderDao rpfod;
	
	
	public void insertTransfer(RechargePutforwardOrderVO vo){
		rpfod.insertTransfer(vo);
	}
	
	/**今日是否可提现
	 * @param customerId
	 * @return
	 */
	public boolean recetlyMentionedToday(int customerId){
		RechargePutforwardOrderVO vo=rpfod.queryRecentDate(customerId);
		if(vo==null){//没有提现记录，可进行提现判断
			return true;
		}
		String lostputforwardtime=vo.getPayCompleteTime();
		lostputforwardtime=lostputforwardtime.substring(0, 10);//截取日期到日
		String today=DateUtil.fomatDataString(new Data());
		if(lostputforwardtime.equals(today)){
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		System.out.println("2018-09-20 18:48:08".length());
		System.out.println("2018-09-20 18:48:08".substring(0,10));
	}

}
