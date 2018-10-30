package com.daka.api.refunds;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.daka.api.base.Result;
import com.daka.entry.CustomerVO;
import com.daka.interceptor.appsession.SessionContext;
import com.daka.service.refunds.RefundsService;

@RequestMapping("/app/refund")
@RestController
public class RefundProvider {
	@Autowired
	private RefundsService refundservice;
	
	
	/**查询用户的累计充值、提现金额，可退款金额
	 * @param request
	 * @param sessionId
	 * @return
	 */
	@RequestMapping("/record")
	public Result cumulativeRecord(HttpServletRequest request,String sessionId){
		CustomerVO vo=SessionContext.getConstomerInfoBySessionId(request, sessionId);
		try {
			return refundservice.cumulativeRecord(vo.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.newFailure("系统异常，请联系统管理员", 0);
		}
	}
	
	
	/**用户提交退款申请
	 * @param request
	 * @param sessionId
	 * @return
	 */
	@RequestMapping("/apply")
	public Result saveRefundRequest(HttpServletRequest request,String sessionId){
		CustomerVO vo=SessionContext.getConstomerInfoBySessionId(request, sessionId);
		try {
			return refundservice.saveRefundRequest(vo.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.newFailure("系统异常，请联系统管理员", 0);
		}
		
	}
	
	

}
