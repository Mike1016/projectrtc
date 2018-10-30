package com.daka.api.order;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daka.dao.shopping.ShoppingCartDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daka.api.base.Result;
import com.daka.constants.SystemConstant;
import com.daka.entry.CustomerVO;
import com.daka.entry.ReceivingAddressVO;
import com.daka.entry.ShoppingCoinLogVO;
import com.daka.entry.ShoppingOrderVO;
import com.daka.entry.dto.ShoppingOrderDTO;
import com.daka.interceptor.appsession.SessionContext;
import com.daka.service.address.ReceivingAddressService;
import com.daka.service.customer.CustomerService;
import com.daka.service.shopping.ShoppingCoinLogService;
import com.daka.service.shopping.ShoppingOrderService;

@RestController
@RequestMapping("/app/order")
public class OrderProvider
{
	@Autowired
	private ShoppingOrderService shoppingOrderService;

	@Autowired
	ReceivingAddressService receivingAddressService;

	@Autowired
	ShoppingCoinLogService shoppingCoinLogService;

	@Autowired
	CustomerService customerService;

	@RequestMapping("/createOrder")
	public Object createOrder(HttpServletRequest request, HttpServletResponse response, String sessionId,
			String goodsIdStr, String goodsCountStr,String cart)
	{
		response.setHeader("Access-Control-Allow-Origin", "*");
		try
		{
			CustomerVO customer = SessionContext.getConstomerInfoBySessionId(request, sessionId);
			if (customer == null)
				return Result.newSuccess("请登录", 5000);
			ReceivingAddressVO addressVO = receivingAddressService.queryDefaultAddress(customer.getId());
			int addressId = 0;
			if (addressVO != null)
			{
				addressId = addressVO.getId();
			}
			if (Optional.ofNullable(goodsIdStr).orElse("").isEmpty()
					|| Optional.ofNullable(goodsCountStr).orElse("").isEmpty())
			{
				return Result.newFailure("参数有误", SystemConstant.FAIL_CODE);
			}
			String[] goodsIds = goodsIdStr.split(",");
			String[] goodsCount = goodsCountStr.split(",");
			if (goodsIds.length != goodsCount.length)
			{
				return Result.newFailure("参数有误", SystemConstant.FAIL_CODE);
			}
			List<Map<String, String>> param = new ArrayList<>();
			for (int i = 0; i < goodsIds.length; i++)
			{
				Map<String, String> temp = new HashMap<>();
				if (goodsIds[i].isEmpty() || goodsCount[i].isEmpty())
				{
					continue;
				}
				temp.put("goodsId", goodsIds[i]);
				temp.put("count", goodsCount[i]);
				param.add(temp);
			}

			int id = shoppingOrderService.createOrderWithGoods(param, addressId, customer.getId(),cart);
			if (id == 0)
				return Result.newSuccess("操作失败", 0);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", id);
			return Result.newSuccess(data, "成功", 1);
		} catch (Exception e)
		{
			e.printStackTrace();
			return Result.newFailure(""+e.getMessage());
		}
	}

	// 取消订单
	@RequestMapping("/orderPeration")
	public Result orderPeration(HttpServletRequest request, String sessionId)
	{
		String orderId = request.getParameter("id");
		CustomerVO customer = SessionContext.getConstomerInfoBySessionId(request, sessionId);
		if (customer == null)
			return Result.newSuccess("请登录", 0);
		if (orderId == null || orderId == "")
			return Result.newSuccess("操作失败", 0);
		int result = shoppingOrderService.updateRefund(1, Integer.valueOf(orderId), customer.getId());
		if (result > 0)
			return Result.newSuccess("取消成功", 1);
		return Result.newSuccess("数据异常", 0);
	}

	// 付款
	@RequestMapping("/toPay")
	public Result toPay(HttpServletRequest request, String sessionId)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String needToPay = request.getParameter("needToPay");
		String myshoppingCoin = request.getParameter("myshoppingCoin");
		String orderId = request.getParameter("id");
		CustomerVO customer = SessionContext.getConstomerInfoBySessionId(request, sessionId);
		if (needToPay == null || needToPay == "" || myshoppingCoin == null || myshoppingCoin == "" || orderId == null
				|| orderId == "" || customer == null)
			return Result.newSuccess("操作失败", 0);
		if (BigDecimal.valueOf(Double.valueOf(myshoppingCoin))
				.compareTo(BigDecimal.valueOf(Double.valueOf(needToPay))) < 0)
			return Result.newSuccess("商城币余额不足", 0);
		// 生成消费记录
		ShoppingCoinLogVO shoppingCoinLogVO = new ShoppingCoinLogVO();
		shoppingCoinLogVO.setShoppingCoin(BigDecimal.valueOf(Double.valueOf(needToPay)));
		shoppingCoinLogVO.setCustomerId(customer.getId());
		shoppingCoinLogVO.setCreateTime(sdf.format(new Date()));
		shoppingCoinLogVO.setRemark("支付");
		shoppingCoinLogVO.setOrderId(Integer.valueOf(orderId));
		shoppingCoinLogService.saveCoin(shoppingCoinLogVO);

		ShoppingOrderVO orderVO = new ShoppingOrderVO();
		orderVO.setId(Integer.valueOf(orderId));
		orderVO.setState(2);

		CustomerVO vo = new CustomerVO();
		vo.setId(customer.getId());
		vo.setShoppingCoin(BigDecimal.valueOf(Double.valueOf(needToPay)));
		customerService.updateShoppingCoinToPay(vo);
		shoppingOrderService.updateOrderState(orderVO);
		return Result.newSuccess("支付成功", 1);
	}

	// 查询所有订单
	@RequestMapping("/queryAllOrder")
	public Result queryAllOrder(HttpServletRequest request, String sessionId)
	{
		CustomerVO customer = SessionContext.getConstomerInfoBySessionId(request, sessionId);
		if (customer == null)
			return Result.newSuccess("请登录", 5000);
		ShoppingOrderVO vo = new ShoppingOrderVO();
		vo.setCustomerId(customer.getId());
		List<ShoppingOrderDTO> list = shoppingOrderService.queryMyOrder(vo);
		if (list.size() <= 0)
			return Result.newSuccess("您还没有订单", 0);
		List<ShoppingOrderDTO> orderGoods = new ArrayList<>();
		ShoppingOrderDTO orderDTO = null;
		for (ShoppingOrderDTO order : list)
		{
			if (!order.getOrderNo().equals(orderDTO == null ? null : orderDTO.getOrderNo()))
			{
				orderDTO = new ShoppingOrderDTO();
				orderDTO.setId(order.getId());
				orderDTO.setState(order.getState());
				orderDTO.setOrderNo(order.getOrderNo());
				orderDTO.setCount(0);
				orderDTO.setPrice(BigDecimal.ZERO);
				for (ShoppingOrderDTO dto : list)
				{
					if (dto.getOrderNo().equals(order.getOrderNo()))
					{
						orderDTO.setCount(dto.getCount() + orderDTO.getCount());
						orderDTO.setPrice(
								dto.getPrice().multiply(new BigDecimal(orderDTO.getCount())).add(orderDTO.getPrice()));
						orderDTO.getGoodsList().add(dto);
					}
				}
				orderGoods.add(orderDTO);
			}
		}
		return Result.newSuccess(orderGoods, "成功", 1);
	}

	// 确认收货
	@RequestMapping("/collectGoods")
	public Result collectGoods(HttpServletRequest request, String sessionId)
	{
		String orderId = request.getParameter("orderId");
		CustomerVO customer = SessionContext.getConstomerInfoBySessionId(request, sessionId);
		if (customer == null)
			return Result.newSuccess("请登录", 5000);
		ShoppingOrderVO vo = new ShoppingOrderVO();
		vo.setId(Integer.valueOf(orderId));
		vo.setState(4);
		shoppingOrderService.updateOrderState(vo);
		return Result.newSuccess("成功收货", 1);
	}

	// 退款
	@RequestMapping("/refund")
	public Result refund(HttpServletRequest request, String sessionId)
	{
		String id = request.getParameter("id");
		CustomerVO customer = SessionContext.getConstomerInfoBySessionId(request, sessionId);
		if (customer == null)
			return Result.newSuccess("请登录", 5000);
		ShoppingOrderVO vo = new ShoppingOrderVO();
		vo.setId(Integer.valueOf(id));
		vo.setState(5);
		shoppingOrderService.updateOrderState(vo);
		return Result.newSuccess("正在审核，请等待...", 1);
	}

	/// 结算、立即支付后的订单显示
	@RequestMapping("/toPayOrderShow")
	public Result toPayOrederShow(HttpServletRequest request, String sessionId)
	{
		String id = request.getParameter("id");
		CustomerVO customer = SessionContext.getConstomerInfoBySessionId(request, sessionId);
		if (customer == null)
			return Result.newSuccess("请登录", 5000);
		if (id == null || id == "")
			return Result.newSuccess("参数错误", 0);
		ReceivingAddressVO addressVO = receivingAddressService.queryDefaultAddress(customer.getId());
		ShoppingOrderVO vo = new ShoppingOrderVO();
		vo.setId(Integer.valueOf(id));
		vo.setCustomerId(customer.getId());
		List<ShoppingOrderDTO> orderGoods = shoppingOrderService.queryMyOrder(vo);
		if (orderGoods.size() <= 0)
			return Result.newSuccess("商品不足", 0);
		int allCount = 0;
		double allPrice = 0;
		double allfreight = 0;
		for (ShoppingOrderDTO dto : orderGoods)
		{
			allCount += dto.getCount();
			double price = dto.getCount().doubleValue() * dto.getPrice().doubleValue();
			allPrice += price;
			allfreight += dto.getFreight().doubleValue();
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("defaultAddress", addressVO);
		data.put("orderGoods", orderGoods);
		data.put("allCount", allCount);
		data.put("allPrice", allPrice);
		data.put("allfreight", allfreight);
		data.put("orderId", orderGoods.get(0).getId());
		data.put("orderNo", orderGoods.get(0).getOrderNo());
		data.put("createTime", orderGoods.get(0).getCreateTime());
		data.put("needToPay", allPrice + allfreight);
		BigDecimal myShoppingCoin = orderGoods.get(0).getMyShoppingCoin();
		if (myShoppingCoin == null)
		{
			myShoppingCoin = new BigDecimal(0);
		}
		data.put("myShoppingCoin", myShoppingCoin);
		return Result.newSuccess(data, "", 1);
	}

	// 支付订单
	@RequestMapping("/toPayOrder")
	public Result toPayOrder(HttpServletRequest request, String sessionId)
	{
		String id = request.getParameter("id");
		String remark = request.getParameter("remark");
		CustomerVO customer = SessionContext.getConstomerInfoBySessionId(request, sessionId);
		if (customer == null)
			return Result.newSuccess("请登录", 5000);
		if (id == null || id == "")
			return Result.newSuccess("参数错误", 1);
		ReceivingAddressVO addressVO = receivingAddressService.queryDefaultAddress(customer.getId());
		if (addressVO == null)
		{
			return Result.newSuccess("请输入默认地址", 0);
		}
		ShoppingOrderVO orderVO = new ShoppingOrderVO();
		orderVO.setId(Integer.valueOf(id));
		orderVO.setRemark(remark);
		orderVO.setReceiovingAddressId(addressVO.getId());
		shoppingOrderService.updateOrder(orderVO);
		return Result.newSuccess("", 1);
	}
}
