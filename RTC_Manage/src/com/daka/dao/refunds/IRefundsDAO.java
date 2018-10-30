package com.daka.dao.refunds;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.daka.entry.RefundsVO;

public interface IRefundsDAO {
	
	void insert(RefundsVO vo);//生成退款请求记录
	
	List<RefundsVO> findByCustomer(@Param("customerId")int customerId);//用户的待审核，审核通过的退款申请

}
