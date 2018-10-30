package com.daka.service.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daka.dao.address.ReceivingAddressDAO;
import com.daka.dao.shopping.ShoppingOrderDAO;
import com.daka.entry.ReceivingAddressVO;
import com.daka.entry.ShoppingOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReceivingAddressService
{
	@Autowired
	ReceivingAddressDAO receivingAddressDAO;

	@Autowired
	ShoppingOrderDAO shoppingOrderDAO;

	public int insert(ReceivingAddressVO vo, String judge, int customerId)
	{
		ReceivingAddressVO addressVO = receivingAddressDAO.queryDefaultAddress(customerId);
		if (judge.equals("true"))
		{
			vo.setState(1);
			int row = updateAllState(customerId);
			if (row >= 0)
			{
				// ShoppingOrderVO orderVO =
				// shoppingOrderDAO.queryByAddressIdState(addressVO.getId());

				int num = receivingAddressDAO.insert(vo);
				if (num > 0)
				{
					ReceivingAddressVO address = receivingAddressDAO.queryDefaultAddress(customerId);
					// ShoppingOrderVO order = new ShoppingOrderVO();
					// order.setId(orderVO.getId());
					// order.setReceiovingAddressId(address.getId());
					// shoppingOrderDAO.updateOrder(order);
					return num;
				}
			}
			return -1;
		} else if (judge.equals("false"))
		{
			vo.setState(0);
			return receivingAddressDAO.insert(vo);
		} else
		{

		}
		return -1;
	}

	public List<ReceivingAddressVO> queryAllAddress(int customerId)
	{
		return receivingAddressDAO.queryAllAddress(customerId);
	}

	public int updateAllState(int customerId)
	{
		return receivingAddressDAO.updateAllState(customerId);
	}

	public int updateAddress(ReceivingAddressVO receivingAddressVO, String judge, int customerId)
	{
		receivingAddressVO.setCustomerId(customerId);
		if (judge.equals("true"))
		{
			int row = updateAllState(customerId);
			receivingAddressVO.setState(1);
			if (row >= 0)
			{
				int num = receivingAddressDAO.updateAddress(receivingAddressVO);
				if (num > 0)
				{
					return num;
				}
			}
			return -1;
		} else if (judge.equals("false"))
		{
			receivingAddressVO.setState(0);
			return receivingAddressDAO.updateAddress(receivingAddressVO);
		} else
		{

		}
		return -1;
	}

	public int delAddress(ReceivingAddressVO receivingAddressVO)
	{
		return receivingAddressDAO.delAddress(receivingAddressVO);
	}

    public ReceivingAddressVO queryDefaultAddress(int customerId){
        return receivingAddressDAO.queryDefaultAddress(customerId);
    }

    public Map<String,Object> queryAddress(Integer id) {
        return receivingAddressDAO.queryAddress(id);
    }

    public ReceivingAddressVO queryAddressById(Integer id) {
        return receivingAddressDAO.queryAddressById(id);
    }

}
