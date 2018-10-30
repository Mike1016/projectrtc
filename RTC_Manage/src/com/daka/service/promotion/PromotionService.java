package com.daka.service.promotion;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daka.dao.promotionbonus.IPromotionBonusDAO;
import com.daka.entry.CustomerVO;
import com.daka.entry.PromotionBonusVO;
import com.daka.entry.RechargeLogVO;
import com.daka.entry.RechargePutforwardOrderVO;
import com.daka.service.alipay.AlipayService;
import com.daka.service.customer.CustomerService;
import com.daka.service.dictionaries.DictionariesService;
import com.daka.service.recharge.RechargeLogService;
import com.daka.util.DateUtils;

@Service
public class PromotionService
{

	@Autowired
	private AlipayService alipayService;

	@Autowired
	private CustomerService customerservice;

	@Autowired
	private IPromotionBonusDAO promotionDAO;

	@Autowired
	private RechargeLogService rechargeLogService;

	/**
	 * 获取当前用户的推广奖金记录
	 * 
	 * @param customerVO
	 * @return
	 */
	public List<PromotionBonusVO> queryCurrentPromotionData(CustomerVO customerVO) throws Exception
	{
		return promotionDAO.findByCustomerId(customerVO.getId());
	}

	private Map<String, String> contructAliReturnResult(HttpServletRequest request)
	{
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		Set<String> keyset = requestParams.keySet();
		for (String key : keyset)
		{
			StringBuffer buffer = new StringBuffer();
			for (String string : requestParams.get(key))
			{
				buffer.append(string);
			}
			params.put(key, buffer.toString());
		}
		return params;
	}

	/**
	 * 激活一条数据
	 * 
	 * @param response
	 * @param request
	 * 
	 * @throws Exception
	 */
	@Transactional(rollbackFor =
	{ Exception.class, RuntimeException.class, Throwable.class })
	public synchronized void unfreezeOneRecord(HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		// 1.处理返回的request，封装成map
		Map<String, String> contructAliReturnResult = contructAliReturnResult(request);

		// 获取数据
		String out_trade_no = contructAliReturnResult.get("out_trade_no");
		String trade_status = contructAliReturnResult.get("trade_status");
		double total_amount = Double.parseDouble(contructAliReturnResult.get("total_amount"));
		String seller_id = contructAliReturnResult.get("seller_id");// 商户ID
		String app_id = contructAliReturnResult.get("app_id");// appID
		String trade_no = contructAliReturnResult.get("trade_no");

		// 2 验证支付宝回调的签名
		// boolean verify_result =
		// AlipaySignature.rsaCheckV1(contructAliReturnResult,
		// AlipayConfig.ALIPAY_PUBLIC_KEY,
		// AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);

		// 3. 校验信息
		// if (verify_result && trade_status.equals("TRADE_SUCCESS") &&
		// seller_id.equals(AlipayConfig.seller_id)
		// && app_id.equals(AlipayConfig.APPID))
		// {
		// 订单不存在或者已完成的订单并且:false
		boolean orderIsExcuting = alipayService.completedTransaction(out_trade_no);
		RechargePutforwardOrderVO vo = alipayService.findByOrderno(out_trade_no);

		// 校验充值金额是否正确
		PromotionBonusVO promotionInfoInOrder = promotionDAO.queryById(vo.getPhone());
		boolean totalAccountEnough = isTotalAccountEnough(promotionInfoInOrder, total_amount);

		// 所有校验通过，则进行业务处理
		// ==============================业务start==========================
		if (totalAccountEnough && orderIsExcuting && total_amount == vo.getPayApplyTotal())
		{
			// 接收的消息是支付宝发送的 商户是APP本身，申请金额和实际充值金额相等，此次订单是之前预支付时创建的
			// 1. 更新订单信息
			updatePreOrderInfo(trade_no, total_amount, vo);

			// 2. 插入充值记录,且三代返利
			insertRechargeLogAndAchieve(total_amount, vo);

			// 3. 插入推广奖金激活数据到日志表
			insertRemotionRechargLog(vo);

			// 4. 更新推广奖金记录数据
			double promotionMoney = updatePromotionLog(vo.getPhone(), total_amount);

			// 5. 更新用户的待分红钱包，以及推广奖金钱包
			updateCustomerWaittingDivideWallet(vo.getCustomerId(), promotionMoney);
		}
		// ==============================业务end==========================
		// }

	}

	private void insertRemotionRechargLog(RechargePutforwardOrderVO vo)
	{
		// 1. 增加充值记录
		RechargeLogVO revo = new RechargeLogVO();
		revo.setAccount(new BigDecimal(DictionariesService.dictionaries.get(new Integer(1502))));
		revo.setCustomerId(vo.getCustomerId());
		revo.setCreateTime(DateUtils.getCurrentTimeYMDHMS());
		revo.setRemark("推广奖金激活至待分红");
		revo.setType(7);
		rechargeLogService.insert(revo);// 添加激活体验金记录
	}

	private void updateCustomerWaittingDivideWallet(int id, double promotionMoney)
	{
		CustomerVO customer = customerservice.queryCustomerById(id);
		customer.setWaitingDividendsWallet(customer.getWaitingDividendsWallet().add(new BigDecimal(promotionMoney)));
		customer.setRecommendBonus(customer.getRecommendBonus().add(new BigDecimal(promotionMoney)));
		customerservice.updateByCustomer(customer);
	}

	private synchronized double updatePromotionLog(String id, double totalAccount) throws Exception
	{
		PromotionBonusVO bonusVO = queryById(id);
		bonusVO.setUnfreezeMoney(new BigDecimal(totalAccount));
		bonusVO.setUnfreezeTime(DateUtils.getCurrentTimeYMDHMS());
		bonusVO.setState(1);
		updateUnfreezeInfo(bonusVO);
		return bonusVO.getAccount().doubleValue();
	}

	private void updateUnfreezeInfo(PromotionBonusVO bonusVO) throws Exception
	{
		promotionDAO.updateUnfreezeInfoById(bonusVO);
	}

	public PromotionBonusVO queryById(String id) throws Exception
	{
		return promotionDAO.queryById(id);
	}

	public List<PromotionBonusVO> queryByCustomerId(int id) throws Exception
	{
		return promotionDAO.findByCustomerId(id);
	}

	private void insertRechargeLogAndAchieve(double total_amount, RechargePutforwardOrderVO vo) throws Exception
	{
		RechargeLogVO logvo = new RechargeLogVO();
		logvo.setAccount(new BigDecimal(total_amount));// 充值金额
		logvo.setCustomerId(vo.getCustomerId());// 充值用户
		logvo.setType(1);// 支付宝充值
		logvo.setCreateTime(vo.getPayCompleteTime());// 充值时间
		logvo.setRemark("支付宝充值到待分红");// 备注
		logvo.setOrderId(vo.getId());// 支付宝订单id
		customerservice.saveOrUpdateAchievement(logvo); // 产生进入待分红的充值记录、添加充值记录
	}

	private void updatePreOrderInfo(String trade_no, double total_amount, RechargePutforwardOrderVO vo)
	{
		vo.setPayOutOrderNo(trade_no);// 支付宝交易号
		vo.setPayRealityTotal(Double.valueOf(total_amount));// 实际付款金额
		vo.setPayState(2);// 订单状态改为已完成
		vo.setPayCompleteTime(DateUtils.getCurrentTimeYMDHMS());// 订单完成时间
		alipayService.updateorder(vo);// 更新订单信息
	}

	/**
	 * 激活所有推广数据
	 * 
	 * @param customerVO
	 */
	public synchronized void unfreezeAllRecord(HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		// 1.处理返回的request，封装成map
		Map<String, String> contructAliReturnResult = contructAliReturnResult(request);

		// 获取数据
		String out_trade_no = contructAliReturnResult.get("out_trade_no");
		String trade_status = contructAliReturnResult.get("trade_status");
		double total_amount = Double.parseDouble(contructAliReturnResult.get("total_amount"));
		String seller_id = contructAliReturnResult.get("seller_id");// 商户ID
		String app_id = contructAliReturnResult.get("app_id");// appID
		String trade_no = contructAliReturnResult.get("trade_no");

		// 2 验证支付宝回调的签名
		// boolean verify_result =
		// AlipaySignature.rsaCheckV1(contructAliReturnResult,
		// AlipayConfig.ALIPAY_PUBLIC_KEY,
		// AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);

		// 3. 校验信息
		// if (verify_result && trade_status.equals("TRADE_SUCCESS") &&
		// seller_id.equals(AlipayConfig.seller_id)
		// && app_id.equals(AlipayConfig.APPID))
		// {
		// 订单不存在或者已完成的订单并且:false
		boolean orderIsExcuting = alipayService.completedTransaction(out_trade_no);
		RechargePutforwardOrderVO vo = alipayService.findByOrderno(out_trade_no);

		// 获取到该订单下的所有推广记录，校验金额是否正确
		List<PromotionBonusVO> promotionInfoInOrder = promotionDAO.findByCustomerId(vo.getCustomerId());
		boolean totalAccountEnough = isTotalAccountEnough(promotionInfoInOrder, total_amount);

		if (orderIsExcuting && totalAccountEnough && total_amount == vo.getPayApplyTotal())
		{
			// 接收的消息是支付宝发送的 商户是APP本身，申请金额和实际充值金额相等，此次订单是之前预支付时创建的
			// 1. 更新订单信息
			updatePreOrderInfo(trade_no, total_amount, vo);

			// 2. 插入充值记录,且三代返利
			insertRechargeLogAndAchieve(total_amount, vo);

			// 3. 更新推广奖金记录数据
			updatePromotionLog(vo.getCustomerId());

			// 4. 插入推广奖金激活数据到日志表
			insertRemotionRechargLog(vo, new BigDecimal(total_amount)
					.multiply(new BigDecimal(DictionariesService.dictionaries.get(new Integer(1001)))));

			// 5. 更新用户的待分红钱包
			updateCustomerWaittingDivideWallet(vo.getCustomerId(),
					(total_amount / Double.parseDouble(DictionariesService.dictionaries.get(new Integer(1501)))));
		}
		// }
	}

	private void insertRemotionRechargLog(RechargePutforwardOrderVO vo, BigDecimal rechargeAccount)
	{
		// 1. 增加充值记录
		RechargeLogVO revo = new RechargeLogVO();
		revo.setAccount(rechargeAccount);
		revo.setCustomerId(vo.getCustomerId());
		revo.setCreateTime(DateUtils.getCurrentTimeYMDHMS());
		revo.setRemark("推广奖金激活至待分红");
		revo.setType(7);
		rechargeLogService.insert(revo);// 添加激活体验金记录
	}

	private void updatePromotionLog(Integer customerId) throws Exception
	{
		promotionDAO.updateUnfreezeInfoByCustomerId(customerId);
	}

	public boolean isTotalAccountEnough(List<PromotionBonusVO> promotionInfoInOrder, double totalAmount)
	{
		double sumAccount = 0d;
		for (PromotionBonusVO promotionBonusVO : promotionInfoInOrder)
		{
			if (promotionBonusVO.getState() == 0)
			{
				sumAccount += promotionBonusVO.getAccount().doubleValue();
			}
		}
		sumAccount = sumAccount * Double.parseDouble(DictionariesService.dictionaries.get(new Integer(1501)));
		return sumAccount > totalAmount ? false : true;
	}

	public boolean isTotalAccountEnough(PromotionBonusVO promotionInfoInOrder, double totalAmount)
	{
		double sumAccount = promotionInfoInOrder.getAccount().doubleValue()
				* Double.parseDouble(DictionariesService.dictionaries.get(new Integer(1501)));
		return sumAccount > totalAmount ? false : true;
	}

}
