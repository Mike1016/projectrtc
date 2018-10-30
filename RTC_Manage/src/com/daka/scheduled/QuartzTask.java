package com.daka.scheduled;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.daka.constants.SystemConstant;
import com.daka.dao.fiveInvest.IFiveTimesInvestDAO;
import com.daka.entry.FiveInvest;
import com.daka.entry.FiveInvestLog;
import com.daka.enums.FiveTimesInvestStateEnum;
import com.daka.service.customer.CustomerService;
import com.daka.service.dictionaries.DictionariesService;
import com.daka.service.ranking.RankingService;

@Component
@EnableScheduling
public class QuartzTask
{
	Log log = LogFactory.getLog(this.getClass());
	@Autowired
	CustomerService customerService;
	@Autowired
	RankingService rankingService;

	@Autowired
	IFiveTimesInvestDAO fiveTimesInvestDAO;

	// 每分钟 0 */1 * * * ?
	// 每5分钟 0 */5 * * * ?

	/**
	 * 每天返利 { 0 0 1 * * ? }
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void bonusTask()
	{
		try
		{
			log.info("========================== start bonusTask ==========================");
			customerService.saveWalletTask();
			log.info("========================== end bonusTask ==========================");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// @Scheduled(cron = "0 */1 * * * ?")
	// public void fiveTimesInvest()
	// {
	// try
	// {
	// insertFiveTimesInvestJob();
	// } catch (Exception e)
	// {
	// e.printStackTrace();
	// log.info("========================== fiveTimesInvest
	// error==========================");
	// }
	// }

	@Transactional(rollbackFor =
	{ Exception.class, RuntimeException.class, Throwable.class }, readOnly = false)
	public void insertFiveTimesInvestJob()
	{
		log.info("========================== start fiveTimesInvest ==========================");

		// 1. 获取所有五倍复投状态为待分红的数据
		List<FiveInvest> allExcutingRecord = fiveTimesInvestDAO.getAllExcutingFiveInvestRecord();

		BigDecimal rate = new BigDecimal(DictionariesService.dictionaries.get(new Integer(1101)));

		// 2. 生成5倍复投记录表数据
		List<FiveInvestLog> fiveLogParam = new ArrayList<>();
		for (FiveInvest fiveInvest : allExcutingRecord)
		{
			FiveInvestLog param = new FiveInvestLog();
			param.setFiveInvestId(fiveInvest.getId());
			param.setCustomerId(fiveInvest.getCustomerId());
			param.setCapital(fiveInvest.getDynamicAccount());
			param.setRate(rate);
			param.setCurrentReturnAccount(fiveInvest.getDynamicAccount().multiply(rate));
			param.setCreateTime(SystemConstant.DATE_FORMAT_2.format(new Date()));
			fiveLogParam.add(param);
			log.info("=======当前五倍复投返利动态金额为：" + fiveInvest.getDynamicAccount());
			fiveInvest.setDynamicAccount(fiveInvest.getDynamicAccount().add(param.getCurrentReturnAccount()));
			fiveInvest.setState((fiveInvest.getDynamicAccount().compareTo(fiveInvest.getLimitAccount()) == -1)
					? FiveTimesInvestStateEnum.RUNNING.getValue()
					: FiveTimesInvestStateEnum.FINISHED.getValue());
			log.info("========执行当前五倍复投返利后总金额为：" + fiveInvest.getDynamicAccount());
			log.info("========执行当前五倍复投返利后状态为：" + fiveInvest.getState());
			log.info("========执行当前五倍复投返利单次金额为：" + param.getCurrentReturnAccount());
			log.info("========执行当前五倍复投返利利率为：" + param.getRate());
		}
		fiveTimesInvestDAO.saveFiveTimesInvestLog(fiveLogParam);
		fiveTimesInvestDAO.updateFiveTimesInvest(allExcutingRecord);
		log.info("========================== end fiveTimesInvest ==========================");
	}

	/**
	 * 每周排名 { 0 0 0 ? * MON }
	 */
	// @Scheduled(cron = "0 0 0 ? * MON")
	// public void rankingTask()
	// {
	// try
	// {
	// log.info("========================== start rankingTask
	// ==========================");
	// rankingService.saveRankingTask();
	// log.info("========================== end rankingTask
	// ==========================");
	// } catch (Exception e)
	// {
	// e.printStackTrace();
	// }
	// }
}
