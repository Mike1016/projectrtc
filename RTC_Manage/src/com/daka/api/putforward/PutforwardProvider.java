package com.daka.api.putforward;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daka.api.base.Result;
import com.daka.entry.BonusLogVO;
import com.daka.entry.CustomerVO;
import com.daka.entry.PutforwardLogVO;
import com.daka.interceptor.appsession.SessionContext;
import com.daka.service.bonus.BonusLogService;
import com.daka.service.customer.CustomerService;
import com.daka.service.putforward.PutforwardService;
import com.daka.service.recharge.RechargeLogService;
import com.daka.service.recharge.RechargePutforwardOrderService;
import com.daka.util.DateUtils;

@RestController
@RequestMapping("/app/putforward")
public class PutforwardProvider {
	@Autowired
	private CustomerService cms;
	@Autowired
	private PutforwardService pfws;
	@Autowired
	private BonusLogService bls;
	@Autowired
	private RechargeLogService rgls;
	@Autowired
	private RechargePutforwardOrderService rpos;
	
	/*
	 * type:钱包类型，0：分红钱包；1：佣金钱包
	 * ***/
	@RequestMapping("/cash")
	public Result putforwardCash(HttpServletRequest request,String sessionId,String money,Integer type){
		 CustomerVO cus = SessionContext.getConstomerInfoBySessionId(request,sessionId);
         if (cus == null){
        	 return Result.newFailure("请重新登录", 5000);
    	 }
         boolean recetlyMentionedToday=rpos.recetlyMentionedToday(cus.getId());//今日是否提现过
         if(recetlyMentionedToday==false){
        	 return Result.newFailure("今日提现次数已用尽，请明日再提现", 0);
         }
		 cus=cms.findByPhone(cus.getPhone());
		 boolean f=false;//有无提现权限标记
		 if(cus.getStatus()==1){
			 f=true;
		 }
		 if(cus.getStatus()==0){
			 f=rgls.checkstatus(cus.getId());
		 }
		 if(f){//有提现权限
			  if(type==1){//从余额钱包提现
				  BigDecimal totalbonus=bls.findAllBycusid(cus.getId());
				  BigDecimal realbalance=totalbonus.add(cus.getOriginalWallet());//真实分红余额
				  BigDecimal balance=realbalance.subtract(new BigDecimal(money));
				  if(balance.compareTo(new BigDecimal("0"))<0){
					  cus.setDividendsWallet(realbalance);
					  cms.updateByCustomer(cus);
					  return Result.newFailure("余额不足");
				  }else{
					  cus.setDividendsWallet(balance);
					  cms.updateByCustomer(cus);//重新修订分红余额
					  
					  PutforwardLogVO vo=new PutforwardLogVO();
					  vo.setCustomerId(cus.getId());
					  vo.setAccount(new BigDecimal("0").subtract(new BigDecimal(money)));//扣除分红钱包的钱
					  vo.setType(type);
					  vo.setCreateTime(DateUtils.getCurrentTimeYMDHMS());
					  vo.setState(0);
					  vo.setRemark("余额钱包转入待分红");
					  pfws.insert(vo);//生成提现请求
					  
					  BonusLogVO bvo=new BonusLogVO();
					  bvo.setCustomerId(cus.getId());
					  bvo.setAccount(new BigDecimal("0").subtract(new BigDecimal(money)));
					  bvo.setCreateTime(DateUtils.getCurrentTimeYMDHMS());
					  bvo.setType(4);
					  bvo.setRemark("余额钱包转入待分红");
					  bls.insert(bvo);////增加提现请求记录
				  }
			  }
			  else if(type==2){//佣金钱包，暂未开放
				  return Result.newFailure("暂未开放");
			  }      
		 }
		  return Result.newFailure("暂无提现资格");
	}

}
