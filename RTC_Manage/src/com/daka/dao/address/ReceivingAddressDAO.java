package com.daka.dao.address;

import com.daka.entry.ReceivingAddressVO;

import java.util.List;
import java.util.Map;

public interface ReceivingAddressDAO {

    int insert(ReceivingAddressVO vo);//新增收货地址

    List<ReceivingAddressVO> queryAllAddress(int customerId);//查询所有地址

    int updateAllState(int customerId); //修改所有状态

    int updateAddress(ReceivingAddressVO receivingAddressVO); //修改地址

    int delAddress(ReceivingAddressVO receivingAddressVO); //删除地址

    ReceivingAddressVO queryDefaultAddress(int customerId);//查询默认地址

    Map<String,Object> queryAddress(Integer id);

    ReceivingAddressVO queryAddressById(Integer id);
}
