package com.daka.api.capital;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.daka.constants.SystemConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daka.api.base.Result;
import com.daka.entry.BonusLogVO;
import com.daka.entry.CustomerVO;
import com.daka.entry.RechargeLogVO;
import com.daka.entry.ShoppingCoinLogVO;
import com.daka.entry.dto.RebateLogDTO;
import com.daka.enums.SystemEnum;
import com.daka.interceptor.appsession.SessionContext;
import com.daka.service.bonus.BonusLogService;
import com.daka.service.customer.CustomerService;
import com.daka.service.rebate.RebateLogService;
import com.daka.service.recharge.RechargeLogService;
import com.daka.service.shopping.ShoppingCoinLogService;
import com.daka.util.DateUtils;

/**
 * 资金详情
 */
@RestController
@RequestMapping("/app/capital")
public class CapitalProvider
{
	@Autowired
	RechargeLogService rechargeLogService;
	@Autowired
	RebateLogService rebateLogService;
	@Autowired
	BonusLogService bonusLogService;
	@Autowired
	ShoppingCoinLogService shoppingCoinLogService;
	@Autowired
	CustomerService customerService;

	/**
	 * 体验金
	 */
	@RequestMapping("/V1/record")
	public Result tastesMoney(HttpServletRequest request, String sessionId)
	{
		List<RechargeLogVO> list = null;
		try
		{
			CustomerVO vo = SessionContext.getConstomerInfoBySessionId(request, sessionId);
			list = rechargeLogService.selectRechargeLog(vo.getId(), "3");
		} catch (Exception e)
		{
			e.printStackTrace();
			return Result.newFailure("请联系管理员");
		}
		return Result.newSuccess(list, "成功");
	}

	/**
	 * 佣金钱包
	 */
	@RequestMapping("/V2/record")
	public Result rebate(HttpServletRequest request, String sessionId)
	{
		List<RebateLogDTO> list = null;
		try
		{
			CustomerVO vo = SessionContext.getConstomerInfoBySessionId(request, sessionId);
			list = rebateLogService.selectRebate(vo.getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			return Result.newFailure("请联系管理员");
		}
		return Result.newSuccess(list, "成功");
	}

	/**
	 * 余额钱包
	 */
	@RequestMapping("/V3/record")
	public Result dividendsWallet(HttpServletRequest request, String sessionId)
	{
		List<BonusLogVO> list = null;
		try
		{
			CustomerVO vo = SessionContext.getConstomerInfoBySessionId(request, sessionId);
			list = bonusLogService.selectBonusList(vo.getId(), "1,2,3,4,5,6");
		} catch (Exception e)
		{
			e.printStackTrace();
			return Result.newFailure("请联系管理员");
		}
		return Result.newSuccess(list, "成功");
	}

	/**
	 * 商城币
	 */
	@RequestMapping("/V4/record")
	public Result shoppingCoin(HttpServletRequest request, String sessionId)
	{
		List<ShoppingCoinLogVO> list = null;
		try
		{
			CustomerVO vo = SessionContext.getConstomerInfoBySessionId(request, sessionId);
			list = shoppingCoinLogService.selectShoppingCoinList(vo.getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			return Result.newFailure("请联系管理员");
		}
		return Result.newSuccess(list, "成功");
	}

	/**
	 * 待分红
	 */
	@RequestMapping("/V5/record")
	public Result waitingDividendsWallet(HttpServletRequest request, String sessionId)
	{
		List<RechargeLogVO> list = null;
		try
		{
			CustomerVO vo = SessionContext.getConstomerInfoBySessionId(request, sessionId);
			list = rechargeLogService.selectRechargeLog(vo.getId(), "1,2,3,4,5,6");
		} catch (Exception e)
		{
			e.printStackTrace();
			return Result.newFailure("请联系管理员");
		}
		return Result.newSuccess(list, "成功");
	}

	/**
	 * 复投
	 */
	@RequestMapping("/recast")
	public Result recast(HttpServletRequest request, String sessionId, RechargeLogVO rechargeLogVO)
	{
		try
		{
			CustomerVO vo = SessionContext.getConstomerInfoBySessionId(request, sessionId);
			vo = customerService.queryCustomerById(vo.getId());
			switch (rechargeLogVO.getType())
			{
				case 1:
					if (vo.getRebateWallet().compareTo(rechargeLogVO.getAccount()) == -1)
					{
						return Result.newFailure("余额不足");
					}
					rechargeLogVO.setType(SystemEnum.COMMISSION_RE_CAST.getCode());
					rechargeLogVO.setRemark(SystemEnum.COMMISSION_RE_CAST.getValue());
					break;
				case 2:
					if (vo.getDividendsWallet().compareTo(rechargeLogVO.getAccount()) == -1)
					{
						return Result.newFailure("余额不足");
					}
					rechargeLogVO.setType(SystemEnum.DIVIDED_TO_BONUS.getCode());
					rechargeLogVO.setRemark(SystemEnum.DIVIDED_TO_BONUS.getValue());
					break;
				default:
					return Result.newFailure("类型不正确");
			}

			rechargeLogVO.setCustomerId(vo.getId());
			rechargeLogVO.setCreateTime(DateUtils.getCurrentTimeYMDHMS());
			customerService.saveOrUpdateAchievement(rechargeLogVO);
			// 余额钱包
			CustomerVO customerVO = new CustomerVO();
			customerVO.setId(vo.getId());
			customerVO.setDividendsWallet(vo.getDividendsWallet().subtract(rechargeLogVO.getAccount()));
			//更新提现权限
			Map<String, Object> map = new HashMap<>();
			map.put("customerId", vo.getId());
			map.put("type", "2,5");
			BigDecimal cumulativeMultiple = rechargeLogService.findAllByType(map);
			if (cumulativeMultiple.add(rechargeLogVO.getAccount()).compareTo(SystemConstant.LIMITATION_OF_CASH) >= 0
					&& vo.getStatus() == 0) {
				customerVO.setStatus(1);
			}
			customerService.updateByCustomer(customerVO);

			BonusLogVO bonusLogVO = new BonusLogVO();
			BeanUtils.copyProperties(rechargeLogVO, bonusLogVO);
			bonusLogVO.setAccount(bonusLogVO.getAccount().negate());
			bonusLogVO.setType(SystemEnum.BALANCE_RE_CAST.getCode());
			bonusLogVO.setRemark(SystemEnum.BALANCE_RE_CAST.getValue());
			bonusLogService.insert(bonusLogVO);
		} catch (Exception e)
		{
			e.printStackTrace();
			return Result.newFailure("请联系管理员");
		}
		return Result.newSuccess("成功");
	}
}
