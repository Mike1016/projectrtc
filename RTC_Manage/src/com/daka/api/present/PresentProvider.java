package com.daka.api.present;

import com.daka.api.base.Result;
import com.daka.entry.CustomerVO;
import com.daka.entry.PresentVO;
import com.daka.entry.ReceivingAddressVO;
import com.daka.interceptor.appsession.SessionContext;
import com.daka.service.address.ReceivingAddressService;
import com.daka.service.present.PresentService;
import com.daka.service.recharge.RechargeLogService;
import com.daka.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

@RequestMapping("/app/present")
@RestController
public class PresentProvider {

    @Autowired
    private RechargeLogService rechargeLogService;

    @Autowired
    private PresentService presentService;

    @Autowired
    private ReceivingAddressService receivingAddressService;

    /**
     * 领取中秋节礼物
     * @param request
     * @param sessionId
     * @param id 用户的默认地址id
     * @return
     */
    @RequestMapping("/takePresent")
    public Result takePresent(HttpServletRequest request,String sessionId,Integer id) {
        CustomerVO customerVO = SessionContext.getConstomerInfoBySessionId(request, sessionId);
        if (customerVO == null) return Result.newSuccess("获取信息失败", 5000);
        BigDecimal b = rechargeLogService.queryAccountByType(customerVO.getId());
        if (b == null) return Result.newSuccess("暂时没有领取资格!", 0);
        if (b.compareTo(new BigDecimal(2000)) < 0) return Result.newSuccess("暂时没有领取资格!", 0);
        if (presentService.queryStateByCustomerId(customerVO.getId()) != null) return Result.newFailure("你已经领取过了!", 0);
        ReceivingAddressVO receivingAddressVO = receivingAddressService.queryAddressById(id);
        PresentVO presentVO = new PresentVO();
        presentVO.setCustomerId(customerVO.getId());
        presentVO.setCreateTime(DateUtil.getTime());
        presentVO.setReceivingAddress(receivingAddressVO.getReceivingAddress()+receivingAddressVO.getWhichLocated());
        presentVO.setState(0);
        presentVO.setOrderNo(String.valueOf(System.currentTimeMillis()));
        presentService.savePresent(presentVO);
        return Result.newSuccess("领取成功!", 1);
    }

    /**
     * 领取中秋节活动先去查询是否有默认地址
     * @param request
     * @param sessionId
     * @return
     */
    @RequestMapping("/queryAddress")
    public Result queryAddress(HttpServletRequest request,String sessionId) {
        CustomerVO customerVO = SessionContext.getConstomerInfoBySessionId(request, sessionId);
        if (customerVO == null) return Result.newSuccess("获取信息失败", 5000);
        if (presentService.queryStateByCustomerId(customerVO.getId()) != null) return Result.newFailure("你已经领取过了!", 0);
        Map<String,Object> data = receivingAddressService.queryAddress(customerVO.getId());
        if (data == null) return Result.newFailure("暂时没有默认地址，去设置！",0);
        return Result.newSuccess(data,"成功", 1);
    }
}
