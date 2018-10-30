package com.daka.dao.fiveInvest;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.daka.entry.FiveInvest;
import com.daka.entry.FiveInvestLog;
import com.daka.entry.Page;
import com.daka.entry.PageData;

@Repository
public interface IFiveTimesInvestDAO
{

	/**
	 * 查询所有五倍复投的数据
	 * 
	 * @return
	 */
	List<FiveInvest> getAllExcutingFiveInvestRecord();

	/**
	 * 保存五倍返利日返利记录
	 * 
	 * @param fiveLogParam
	 */
	void saveFiveTimesInvestLog(List<FiveInvestLog> fiveLogParam);

	/**
	 * 更新五倍返利的数据 主要是状态跟动态奖金
	 * 
	 * @param fiveLogParam
	 */
	void updateFiveTimesInvest(List<FiveInvest> fiveLogParam);

	/**
	 * 增加五倍复投数据
	 * 
	 * @param fiveInvest
	 */
	void saveFiveInvest(FiveInvest fiveInvest);

	List<PageData> queryFiveInvestlistPage(Page page) throws Exception; // 五倍复投
																		// 分页查询

	List<PageData> queryFiveInvestLoglistPage(Page page) throws Exception; // 五倍复投日志
																			// 分页查询

}
