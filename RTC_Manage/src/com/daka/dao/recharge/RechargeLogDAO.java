package com.daka.dao.recharge;

import com.daka.entry.Page;
import com.daka.entry.PageData;
import com.daka.entry.RechargeLogVO;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface RechargeLogDAO {

	List<PageData> queryDividendslistPage(Page page); //分页查询分红管理

	List<RechargeLogVO> findByVO(RechargeLogVO vo);//根据属性查询

	void insert(RechargeLogVO vo);//增加充值提现记录

    List<RechargeLogVO> selectRechargeLog(@Param("id") Integer id, @Param("types") String types); //查询待分红

    BigDecimal findAllByType(Map<String, Object> map);//查询累计充值/复投的钱


    BigDecimal queryAccountByType(@Param("customerId")Integer customerId); //根据用户id查询用户累计充值金额
    
	void saveWalletTask(@Param("type") Integer type,
						@Param("rebate") BigDecimal rebate,
						@Param("proportion") BigDecimal proportion,
						@Param("remark") String remark,
						@Param("createTime") String createTime) throws SQLException; //待分红定时任务

}
