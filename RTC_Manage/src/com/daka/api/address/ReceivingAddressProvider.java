package com.daka.api.address;

import com.daka.api.base.Result;
import com.daka.controller.CommonController;
import com.daka.entry.CustomerVO;
import com.daka.entry.ReceivingAddressVO;
import com.daka.interceptor.appsession.SessionContext;
import com.daka.service.address.ReceivingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/app/address")
@RestController
public class ReceivingAddressProvider extends CommonController {
    @Autowired
    ReceivingAddressService receivingAddressService;

    @RequestMapping("/saveAddress")
    public Result newAddress(HttpServletRequest request,String sessionId,String judge){
        ReceivingAddressVO addressVO = (ReceivingAddressVO) getParam(request, ReceivingAddressVO.class);
        CustomerVO customer = SessionContext.getConstomerInfoBySessionId(request, sessionId);
        if (addressVO == null) return Result.newSuccess( "参数错误",0);
        if (customer == null) return Result.newSuccess( "请登录",5000);
        int customerId = customer.getId();
        ReceivingAddressVO vo = new ReceivingAddressVO();
        vo.setCustomerId(customerId);
        vo.setReceivingAddress(addressVO.getReceivingAddress());
        vo.setAddressee(addressVO.getAddressee());
        vo.setContactNo(addressVO.getContactNo());
        vo.setWhichLocated(addressVO.getWhichLocated());
        if (addressVO.getId() == null){
            if (judge == null) return Result.newSuccess( "请选择是否设置默认地址",1);
            if (judge.equals("true")){
                int row = receivingAddressService.insert(vo,judge,customerId);
                if (row > 0) return Result.newSuccess( "添加成功",1);
            }
            else if (judge.equals("false")){
                int row = receivingAddressService.insert(vo, judge,customerId);
                if (row > 0) return Result.newSuccess( "添加成功",1);
            }
        }
        else {
            vo.setId(addressVO.getId());
            if (judge == null) return Result.newSuccess( "请选择是否设置默认地址",1);
            if (judge.equals("true")){
                int row = receivingAddressService.updateAddress(vo,judge,customerId);
                if (row > 0) return Result.newSuccess( "修改成功",1);
            }
            else if (judge.equals("false")){
                int row = receivingAddressService.updateAddress(vo,judge,customerId);
                if (row > 0) return Result.newSuccess( "修改成功",1);
            }
        }
        return Result.newSuccess( "失败",0);
    }

    @RequestMapping("/queryAllAddress")
    public Result queryAllAddress(HttpServletRequest request,String sessionId){
        ReceivingAddressVO addressVO = (ReceivingAddressVO) getParam(request, ReceivingAddressVO.class);
        CustomerVO customer = SessionContext.getConstomerInfoBySessionId(request, sessionId);
        if (customer == null) return Result.newSuccess( "请登录",5000);
        int customerId = customer.getId();
        List<ReceivingAddressVO> data = receivingAddressService.queryAllAddress(customerId);
        return Result.newSuccess( data,"成功",1);
    }

    @RequestMapping("/delAddress")
    public Result delAddress(HttpServletRequest request,String sessionId){
        ReceivingAddressVO addressVO = (ReceivingAddressVO) getParam(request, ReceivingAddressVO.class);
        CustomerVO customer = SessionContext.getConstomerInfoBySessionId(request, sessionId);
        if (customer == null) return Result.newSuccess( "请登录",5000);
        int customerId = customer.getId();
        ReceivingAddressVO vo = new ReceivingAddressVO();
        vo.setId(addressVO.getId());
        vo.setCustomerId(customerId);
        int row = receivingAddressService.delAddress(vo);
        if (row >0 ) return Result.newSuccess("成功",1);
        return Result.newSuccess("失败",0);
    }
}
