package com.daka.dao.bonus;

import com.daka.entry.BonusLogVO;

import com.daka.entry.Page;
import com.daka.entry.PageData;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface BonusLogDAO {

	List<PageData> queryBonuslistPage(Page page);

	List<BonusLogVO> selectBonusList(@Param("id") Integer id, @Param("types") String types); // 分红钱包
	
	void insert(BonusLogVO vo);//增加分红变化记录
	
	BigDecimal findAllBycusid(@Param("customerId")Integer customerId);//查询用户分红钱包记录总和
	
	void saveWalletTask(@Param("type") Integer type,
						@Param("rebate") BigDecimal rebate,
						@Param("proportion") BigDecimal proportion,
						@Param("remark") String remark,
						@Param("createTime") String createTime); //定时任务
}
