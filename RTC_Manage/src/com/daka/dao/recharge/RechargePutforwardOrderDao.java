package com.daka.dao.recharge;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.daka.entry.Page;
import com.daka.entry.PageData;
import com.daka.entry.RechargePutforwardOrderVO;

public interface RechargePutforwardOrderDao
{

	void insert(RechargePutforwardOrderVO vo);// 生成订单

	void update(RechargePutforwardOrderVO vo);// 更新订单

	List<RechargePutforwardOrderVO> findByVO(RechargePutforwardOrderVO VO);// 根据属性查询订单

	RechargePutforwardOrderVO findByOrderno(@Param("payOrderNo") String payOrderNo);// 根据商户订单号查询订单

	void insertTransfer(RechargePutforwardOrderVO vo);// 增加提现订单
	
	RechargePutforwardOrderVO queryRecentDate(@Param("customerId")int customerId);//查询用户的最近一条提现记录

	List<PageData> queryrechargeorderlistPage(Page page);//分页查看充值提现记录
	
}
