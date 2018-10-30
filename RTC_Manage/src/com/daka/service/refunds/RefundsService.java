package com.daka.service.refunds;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daka.api.base.Result;
import com.daka.dao.customer.CustomerDAO;
import com.daka.dao.refunds.IRefundsDAO;
import com.daka.entry.RefundsVO;
import com.daka.entry.dto.CustomerDTO;
import com.daka.service.dictionaries.DictionariesService;

@Service
public class RefundsService {
	@Autowired
	private IRefundsDAO refunddao;
	@Autowired
	private CustomerDAO customerdao;
	
	private final Lock RefundRequestLock=new ReentrantLock();

	/**查询用户的累计充值、提现金额，可退款金额
	 * @param customerId
	 * @return
	 */
	public Result cumulativeRecord(int customerId) throws Exception{
		//查询用户累计充值、提现金额
		CustomerDTO customer = customerdao.cumulativeRecord(customerId);
		
		//用户可退款金额
		BigDecimal refundable = customer
				.getRechargeAccount()
				.multiply(
						new BigDecimal(DictionariesService.dictionaries
								.get(1601)))
				.subtract(customer.getPresentationAccount());
		if(refundable.compareTo(new BigDecimal(0))<0){//用户可退款金额小于零，可退款金额为零
			refundable=	new BigDecimal(0);
			}
		customer.setRefundable(refundable);
		return Result.newSuccess(customer, 1);
	}
	
	/**用户提交退款申请
	 * @param customerId
	 * @return
	 */
	public Result saveRefundRequest(int customerId) throws Exception{
		try {
			RefundRequestLock.lock();
			//判断是否已经提交过退款申请
			List<RefundsVO> list=refunddao.findByCustomer(customerId);
			if(!list.isEmpty()){
				return Result.newFailure("已有待申请的退款申请，请勿重复提交", 0);
			}
			
			//查询用户的累计充值、提现金额，可退款金额
			Result<CustomerDTO> result=cumulativeRecord(customerId);
			CustomerDTO customer=result.getData();
			
			BigDecimal refundableAmount=customer.getRefundable();//用户的可退款金额
			//如果用户的可退款金额小于等于零，不可退款
			if(refundableAmount.compareTo(new BigDecimal(0))<=0){
				return Result.newFailure("可退款金额不足，不可退款", 0);
			}
			
			//添加退款申请记录
			RefundsVO vo=new RefundsVO();
			vo.setCustomerId(customerId);
			vo.setRefundableAmount(refundableAmount);//可退款金额
			vo.setTotalPutforwardAmount(customer.getPresentationAccount());//累计提现金额
			vo.setTotalRechargeAmount(customer.getRechargeAccount());//累计充值金额
			refunddao.insert(vo);
			
			return Result.newSuccess("申请成功，客服人员正在审核中", 1);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.newFailure("系统异常，请联系统管理员", 0);
		}finally{
			RefundRequestLock.unlock();
		}
	}

}
