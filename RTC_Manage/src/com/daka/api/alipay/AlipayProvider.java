
package com.daka.api.alipay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.daka.api.base.Result;
import com.daka.entry.CustomerVO;
import com.daka.entry.RechargePutforwardOrderVO;
import com.daka.interceptor.appsession.SessionContext;
import com.daka.service.alipay.AlipayService;
import com.daka.service.bonus.BonusLogService;
import com.daka.service.customer.CustomerService;
import com.daka.service.promotion.PromotionService;
import com.daka.service.recharge.RechargePutforwardOrderService;
import com.daka.util.DateUtils;
import com.daka.util.alipay.AlipayConfig;
import com.daka.util.alipay.UUID;

@RestController
@RequestMapping("/app/alipay")
public class AlipayProvider
{

	@Autowired
	private CustomerService customerservice;
	@Autowired
	private AlipayService alipayservice;
	@Autowired
	private BonusLogService bonusService;
	@Autowired
	private RechargePutforwardOrderService rpos;

	@Autowired
	private PromotionService promotionService;

	/**
	 * 支付接口
	 * 
	 */
	@RequestMapping(value = "/pay", produces = "application/json")
	public Result pay(HttpServletRequest request, String totalAmount, String sessionId, String notifyUrl,
			String returnUrl, String id)
	{
		CustomerVO cusvo = SessionContext.getConstomerInfoBySessionId(request, sessionId);
		try
		{
			// 获得初始化的AlipayClient
			AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID,
					AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET,
					AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
			AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();

			// 校验金额是否正确
			boolean canGoonPay = true;

			// 如果id不为空，则说明是激活推广奖金
			if (null != id && !id.isEmpty())
			{
				String[] idArr = id.split(",");

				// 如果使用逗号分隔之后的长度为1，则表示只剩一个未激活或者激活1个
				if (idArr.length == 1)
				{

					canGoonPay = promotionService.isTotalAccountEnough(promotionService.queryById(idArr[0]),
							Double.parseDouble(totalAmount));
				} else
				{
					canGoonPay = promotionService.isTotalAccountEnough(
							promotionService.queryByCustomerId(cusvo.getId()), Double.parseDouble(totalAmount));
				}

			}

			if (!canGoonPay)
			{
				return Result.newFailure("充值金额不正确", 0);

			}
			// 设置同步回调地址
			alipayRequest.setReturnUrl(AlipayConfig.return_url);
			// 设置异步回调地址
			alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

			// 如果传入回调地址，则设置，否则按照默认的来
			if (null != notifyUrl && !notifyUrl.isEmpty())
			{
				alipayRequest.setNotifyUrl(notifyUrl);
			}
			if (null != returnUrl && !returnUrl.isEmpty())
			{
				alipayRequest.setReturnUrl(returnUrl);
			}
			// 设置中途退出返回按钮
			String quit_url = "https://docs.open.alipay.com/203/107090/";
			String payOrderNo = UUID.next() + "";
			alipayRequest.setBizContent("{\"out_trade_no\":" + payOrderNo + "," + "\"total_amount\":" + totalAmount
					+ "," + "\"subject\":\"Bonus_store_recharge\"," + "\"quit_url\":\"" + quit_url + "\","
					+ "\"product_code\":\"QUICK_WAP_WAY\"}");
			AlipayTradeWapPayResponse response = alipayClient.pageExecute(alipayRequest);

			if (response.isSuccess())
			{
				RechargePutforwardOrderVO vo = new RechargePutforwardOrderVO();
				vo.setCustomerId(cusvo.getId());
				vo.setPayOrderNo(payOrderNo);// 商户订单号
				vo.setPayApplyTotal(Double.valueOf(totalAmount));// 请求金额
				vo.setPayState(1);// 状态：创建订单
				vo.setPayApplyTime(DateUtils.getCurrentTimeYMDHMS());// 订单创建时间
				vo.setPayType(1);// 支付宝快捷支付充值
				vo.setPhone(id);
				alipayservice.insertAlipayOrder(vo);// 创建订单信息
				return Result.newSuccess(response.getBody(), 1);
			} else
			{
				System.out.println("调用失败");
				return Result.newFailure("选择失败，请稍后再试", 0);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return Result.newFailure("选择失败，请稍后再试", 0);
		}

	}

	/**
	 * 支付回调接口
	 * 
	 * 将异步通知中收到的待验证所有参数都存放到map中
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/payNotify")
	@ResponseBody
	public String payNotify(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String result = alipayservice.callbackProcessing(request, response);
		response.getWriter().write(result);// 操作成功终止支付宝回调
		response.getWriter().flush();
		return result;
	}

	/**
	 * 提现
	 * 
	 * @param request
	 * @param sessionId
	 * @param totalAmount
	 * @param type
	 *            0：余额钱包 1：返利钱包
	 * @return
	 * @throws AlipayApiException
	 */
	@RequestMapping("/transfer")
	public Result Transfer(HttpServletRequest request, String sessionId, String totalAmount, int type)
	{
		CustomerVO vo = SessionContext.getConstomerInfoBySessionId(request, sessionId);
		if (vo == null)
		{
			return Result.newFailure("请重新登录", 5000);
		}
		try
		{
			return alipayservice.updateputForward(vo, totalAmount, type);
		} catch (Exception e)
		{
			e.printStackTrace();
			return Result.newFailure("请求异常，请稍后重试");
		}
	}

}
