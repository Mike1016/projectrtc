package com.daka.service.alipay;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.daka.api.base.Result;
import com.daka.constants.SystemConstant;
import com.daka.dao.bonus.BonusLogDAO;
import com.daka.dao.promotionbonus.IPromotionBonusDAO;
import com.daka.dao.recharge.RechargeLogDAO;
import com.daka.dao.recharge.RechargePutforwardOrderDao;
import com.daka.entry.BonusLogVO;
import com.daka.entry.CustomerVO;
import com.daka.entry.PromotionBonusVO;
import com.daka.entry.PutforwardLogVO;
import com.daka.entry.RechargeLogVO;
import com.daka.entry.RechargePutforwardOrderVO;
import com.daka.service.bonus.BonusLogService;
import com.daka.service.customer.CustomerService;
import com.daka.service.dictionaries.DictionariesService;
import com.daka.service.putforward.PutforwardService;
import com.daka.service.recharge.RechargeLogService;
import com.daka.service.recharge.RechargePutforwardOrderService;
import com.daka.util.DateUtils;
import com.daka.util.alipay.AlipayConfig;
import com.daka.util.alipay.UUID;

/**
 * @author Administrator
 *
 */
@Service
public class AlipayService
{

	@Autowired
	private RechargePutforwardOrderDao rpfod;
	@Autowired
	private CustomerService customerservice;
	@Autowired
	private BonusLogService bonusService;
	@Autowired
	private RechargeLogService rechargeService;
	@Autowired
	private RechargePutforwardOrderService orderservice;
	@Autowired
	private PutforwardService pfws;
	@Autowired
	private RechargeLogDAO rechargeLogDAO;
	@Autowired
	private RechargePutforwardOrderService rpos;
	@Autowired
	private IPromotionBonusDAO promotionBonusDAO;
	@Autowired
	private BonusLogDAO bonusLogDAO;

	public void insertAlipayOrder(RechargePutforwardOrderVO vo)
	{
		// RechargePutforwardOrderVO vo = new RechargePutforwardOrderVO();
		// vo.setCustomerId(customerId);
		// vo.setPayOrderNo(payOrderNo);// 商户订单号
		// vo.setPayApplyTotal(Double.valueOf(totalAmount));// 请求金额
		// vo.setPayState(1);// 状态：创建订单
		// vo.setPayApplyTime(DateUtils.getCurrentTimeYMDHMS());// 订单创建时间
		// vo.setPayType(1);// 支付宝快捷支付充值//创建订单
		rpfod.insert(vo);
	}

	public void updateorder(RechargePutforwardOrderVO vo)
	{// 更新订单状态
		rpfod.update(vo);
	}

	public RechargePutforwardOrderVO findByOrderno(String payOrderNo)
	{// 根据商户订单号查询订单
		return rpfod.findByOrderno(payOrderNo);
	}

	public boolean completedTransaction(String payOrderNo)
	{// 回调时判断订单状态是否更新，支付金额是否等于请求金额
		RechargePutforwardOrderVO order = rpfod.findByOrderno(payOrderNo);
		if (order == null)
		{
			return false;
		}
		if (order.getPayState() == 1)
		{
			return true;
		}
		return false;
	}

	public String alipay(Integer customerId, String totalAmount) throws AlipayApiException
	{// 预订单
		String payOrderNo = String.valueOf(UUID.next());
		// 实例化客户端（参数：网关地址、商户appid、商户私钥、格式、编码、支付宝公钥、加密类型），为了取得预付订单信息
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID,
				AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,
				AlipayConfig.SIGNTYPE);
		// 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();
		// 设置中途退出返回按钮
		String quit_url = "https://docs.open.alipay.com/203/107090/";

		alipay_request.setBizContent("{\"out_trade_no\":" + payOrderNo + "," + "\"total_amount\":" + totalAmount + ","
				+ "\"subject\":" + AlipayConfig.subject + "," + "\"quit_url\":\"" + quit_url + "\","
				+ "\"product_code\":" + AlipayConfig.product_code + "}");

		alipay_request.setNotifyUrl(AlipayConfig.notify_url);// 异步回调地址
		// alipay_request.setReturnUrl(AlipayConfig.return_url);//同步回调地址

		AlipayTradeWapPayResponse alipay_response = alipayClient.pageExecute(alipay_request);// 支付宝返回的预订单信息
		String orderString = alipay_response.getBody();// 可以直接返回给前台APP请求

		RechargePutforwardOrderVO vo = new RechargePutforwardOrderVO();
		vo.setCustomerId(customerId);
		vo.setPayOrderNo(payOrderNo);// 商户订单号
		vo.setPayApplyTotal(Double.valueOf(totalAmount));// 请求金额
		vo.setPayState(1);// 状态：创建订单
		vo.setPayApplyTime(DateUtils.getCurrentTimeYMDHMS());// 订单创建时间
		vo.setPayType(1);// 支付宝快捷支付充值

		this.insertAlipayOrder(vo);// 创建订单信息
		return orderString;
	}

	@Transactional(rollbackFor =
	{ Exception.class, RuntimeException.class, Throwable.class })
	public String callbackProcessing(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		Set<String> keyset = requestParams.keySet();
		String out_trade_no = "";
		String trade_status = "";
		double total_amount = 0;
		for (String key : keyset)
		{
			StringBuffer buffer = new StringBuffer();
			for (String string : requestParams.get(key))
			{
				buffer.append(string);
			}
			params.put(key, buffer.toString());
			if (key.equals("out_trade_no"))
			{
				out_trade_no = buffer.toString();// 商户订单号
				System.out.println(key + " : " + buffer.toString());
			} else if (key.equals("trade_status"))
			{
				trade_status = buffer.toString();// 交易状态
				System.out.println(key + " : " + buffer.toString());
			} else if (key.equals("total_amount"))
			{// 实际付款数
				total_amount = Double.valueOf(buffer.toString());// 充值金额
				System.out.println(key + " : " + total_amount);
			}
		}
		boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET,
				AlipayConfig.SIGNTYPE);// 验证支付宝回调的签名
		String seller_id = params.get("seller_id");// 商户ID
		String app_id = params.get("app_id");// appID
		if (verify_result && trade_status.equals("TRADE_SUCCESS") && seller_id.equals(AlipayConfig.seller_id)
				&& app_id.equals(AlipayConfig.APPID))
		{// 签名正确,支付成功,商户是APP的商户
			boolean callbacksBeenProcessed = false;// 已完成充值业务操作
			callbacksBeenProcessed = this.completedTransaction(out_trade_no);// 订单不存在或者已完成的订单并且:false
			RechargePutforwardOrderVO vo = this.findByOrderno(out_trade_no);
			if (callbacksBeenProcessed && total_amount == vo.getPayApplyTotal())
			{// 接收的消息是支付宝发送的 商户是APP本身，申请金额和实际充值金额相等，此次订单是之前预支付时创建的

				// 更新订单信息
				vo.setPayOutOrderNo(params.get("trade_no"));// 支付宝交易号
				vo.setPayRealityTotal(Double.valueOf(total_amount));// 实际付款金额
				vo.setPayState(2);// 订单状态改为已完成
				vo.setPayCompleteTime(DateUtils.getCurrentTimeYMDHMS());// 订单完成时间
				this.updateorder(vo);// 更新订单信息

				// 充值记录
				RechargeLogVO logvo = new RechargeLogVO();
				logvo.setAccount(new BigDecimal(total_amount));// 充值金额
				logvo.setCustomerId(vo.getCustomerId());// 充值用户
				logvo.setType(1);// 支付宝充值
				logvo.setCreateTime(vo.getPayCompleteTime());// 充值时间
				logvo.setRemark("支付宝充值到待分红");// 备注
				logvo.setOrderId(vo.getId());// 支付宝订单id
				customerservice.saveOrUpdateAchievement(logvo); // 产生进入待分红的充值记录、添加充值记录

				CustomerVO cusvo = customerservice.queryCustomerById(vo.getCustomerId());

				// 如果用户还有体验金，则说明体验金还没有被激活，就需要激活体验金
				if (cusvo.getTastesMoney().longValue() != 0)
				{

					// 1. 增加充值记录
					RechargeLogVO revo = new RechargeLogVO();
					revo.setAccount(new BigDecimal(88));
					revo.setCustomerId(cusvo.getId());
					revo.setCreateTime(DateUtils.getCurrentTimeYMDHMS());
					revo.setRemark("激活体验金到待分红");
					revo.setType(3);
					rechargeService.insert(revo);// 添加激活体验金记录

					// 2. 当前用户待分红钱包需要增加（88+充值金额*2）
					cusvo.setWaitingDividendsWallet(cusvo.getWaitingDividendsWallet()
							.add(new BigDecimal(DictionariesService.dictionaries.get(new Integer(1503)))));

					// 3. 减去用户的体验金
					cusvo.setTastesMoney(new BigDecimal(0));

					// 4. 获取上级用户，返回推广奖金
					CustomerVO parentVo = customerservice.selectParentCustomerVo(cusvo.getId());
					if (null != parentVo && parentVo.getState() == 1)
					{

						// 上级获得冻结的推广奖金
						PromotionBonusVO parentRecommendRecord = new PromotionBonusVO();
						parentRecommendRecord
								.setAccount(new BigDecimal(DictionariesService.dictionaries.get(new Integer(1502))));
						parentRecommendRecord.setUnfreezeMoney(
								new BigDecimal(DictionariesService.dictionaries.get(new Integer(1502))).multiply(
										new BigDecimal(DictionariesService.dictionaries.get(new Integer(1501)))));
						parentRecommendRecord.setChildCustomerId(cusvo.getId());
						parentRecommendRecord.setCreateTime(SystemConstant.DATE_FORMAT_2.format(new Date()));
						parentRecommendRecord.setCustomerId(parentVo.getId());
						parentRecommendRecord.setState(0);
						promotionBonusDAO.insert(parentRecommendRecord);
					}

				}

				if (cusvo.getStatus() == 0)// 没有提现权限
				{
					Map<String, Object> map = new HashMap<>();
					map.put("customerId", cusvo.getId());
					map.put("type", 1);
					BigDecimal accumulativeRecharge = rechargeLogDAO.findAllByType(map);
					map.put("type", "2,5");
					BigDecimal cumulativeMultiple = rechargeLogDAO.findAllByType(map);
					int x = SystemConstant.LIMITATION_OF_CASH
							.compareTo(accumulativeRecharge.add(new BigDecimal(total_amount)));// 累计充值金额是否小于提现限制金额
					int y = SystemConstant.LIMITATION_OF_CASH
							.compareTo(cumulativeMultiple.add(new BigDecimal(total_amount)));// 累计复投金额是否小于提现限制金额
					if (x <= 0 || y <= 0)
					{
						cusvo.setStatus(1);// 开通提现权限
					}
				}
				customerservice.updateByCustomer(cusvo);
				// response.getWriter().write("success");// 操作成功终止支付宝回调
				// response.getWriter().flush();
				return "success";
			}
		} else
		{
			response.getWriter().println("fail");
			return "fail";
		}
		return "fail";

	}

	public void insertRecord(int customerId, String payOrderNo)
	{
		RechargePutforwardOrderVO rvo = new RechargePutforwardOrderVO();
		rvo.setCustomerId(customerId);
		rvo.setPayOrderNo(payOrderNo);

	}

	/**
	 * 提现后插入的记录 更改提现流水号，减少分红钱包余额的记录，提现记录
	 * 
	 * @param vo
	 * @param amount
	 *            提现金额
	 * @param out_pay_no
	 *            商户订单号
	 * @param map
	 *            orderId支付宝交易订单号
	 */
	public void Transfer(CustomerVO vo, String amount, String out_pay_no, Map<String, String> map)
	{
		RechargePutforwardOrderVO rvo = new RechargePutforwardOrderVO();
		rvo.setCustomerId(vo.getId());
		rvo.setPayOutOrderNo(map.get("orderId"));// 支付宝交易订单号
		rvo.setPayOrderNo(out_pay_no);// 商户订单号
		rvo.setPayApplyTotal(Double.valueOf(amount));// 请求金额
		rvo.setPayRealityTotal(Double.valueOf(amount));// 提现金额
		rvo.setPayState(2);// 交易完成
		rvo.setPayApplyTime(DateUtils.getCurrentTimeYMDHMS());
		rvo.setPayCompleteTime(DateUtils.getCurrentTimeYMDHMS());
		rvo.setPayType(2);// 单笔转账到支付宝账户
		orderservice.insertTransfer(rvo);// 添加提现订单记录

		BonusLogVO bvo = new BonusLogVO();
		bvo.setCustomerId(vo.getId());
		bvo.setAccount(new BigDecimal(0).subtract(new BigDecimal(amount)));
		bvo.setCreateTime(DateUtils.getCurrentTimeYMDHMS());
		bvo.setType(4);// 提现
		bvo.setRemark("余额提现");
		bonusService.insert(bvo);// 添加金额变化记录

		PutforwardLogVO pvo = new PutforwardLogVO();
		pvo.setCustomerId(vo.getId());
		pvo.setAccount(new BigDecimal(0).subtract(new BigDecimal(amount)));
		pvo.setType(1);// 余额提现
		pvo.setCreateTime(DateUtils.getCurrentTimeYMDHMS());
		pvo.setUpdateTime(DateUtils.getCurrentTimeYMDHMS());
		pvo.setState(1);
		pvo.setRemark("余额提现");
		pfws.insert(pvo);// 添加提现记录

	}

	/**
	 * 提现业务
	 * 
	 * @param vo
	 * @param totalAmount
	 * @param type
	 *            提现类型 0：余额钱包、分红钱包1：佣金钱包
	 * @return
	 * @throws AlipayApiException
	 */
	public Result updateputForward(CustomerVO vo, String totalAmount, int type) throws AlipayApiException
	{
		vo = customerservice.queryCustomerById(vo.getId());
		boolean recetlyMentionedToday = rpos.recetlyMentionedToday(vo.getId());// 今日是否提现过
		if (recetlyMentionedToday == false)
		{
			return Result.newFailure("今日提现次数已用尽，请明日再提现", 0);
		}
		if (vo.getAlipay().isEmpty())
		{
			return Result.newFailure("暂无支付宝账户，无法提现,请去完善个人信息");
		}
		if (vo.getStatus() == 0)
		{
			return Result.newFailure("暂无提现权限，请充值或复投88元开通提现权限");
		}
		if (type == 1)
		{
			return Result.newFailure("返利钱包的提现功能暂未开放");
		}
		if (type == 0 && vo.getStatus() == 1)// 分红钱包
		{// 有提现权限
			BigDecimal balanceRecord = bonusService.findAllBycusid(vo.getId());// 可提现的钱币记录记录
			if (null == balanceRecord)
			{
				balanceRecord = BigDecimal.ZERO;
			}
			// 以往记录与原始余额的和 是否大于分红钱包余额
			int realBalanceVersusBalance = (balanceRecord.add(vo.getOriginalWallet()))
					.compareTo(vo.getDividendsWallet());
			if (realBalanceVersusBalance < 0)
			{
				return Result.newFailure("分红钱包金额异常");
			}
			// 真实余额与提现金额比较
			int comparisonBetweenBalanceAndAmount = (balanceRecord.add(vo.getOriginalWallet()))
					.compareTo(new BigDecimal(totalAmount));
			if (comparisonBetweenBalanceAndAmount < 0)
			{
				return Result.newFailure("余额不足，请重新选择金额");
			}
			String out_pay_no = UUID.next() + "";
			BigDecimal balan = vo.getDividendsWallet().subtract(new BigDecimal(totalAmount));
			BigDecimal poundage = new BigDecimal(DictionariesService.dictionaries.get(1401)); // 5%的手续费
			BigDecimal dividendsWallet = new BigDecimal(totalAmount).multiply(new BigDecimal(1).subtract(poundage));
			CustomerVO temp = new CustomerVO();
			temp.setId(vo.getId());
			temp.setDividendsWallet(balan);
			customerservice.updateByCustomer(temp);// 修改用户余额钱包钱币
			Map<String, String> map = AlipayConfig.TransferAlipay(vo.getAlipay(),
					String.valueOf(dividendsWallet.doubleValue()), out_pay_no, vo.getNickName());
			if (map.get("code").equals("10000"))// 提现业务状态码=10000 转账成功 进行业务操作
			{
				Transfer(vo, totalAmount, out_pay_no, map);
				return Result.newSuccess("提现成功", 1);
			} else
			{
				temp.setDividendsWallet(vo.getDividendsWallet());
				customerservice.updateByCustomer(temp);// 修改用户余额钱包钱币
				return Result.newFailure("请求异常，请稍后重试");
			}
		}
		return Result.newFailure("请求异常，请稍后重试");
	}

}
