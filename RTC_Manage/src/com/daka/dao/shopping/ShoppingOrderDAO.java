package com.daka.dao.shopping;

import com.daka.entry.*;
import com.daka.entry.dto.ShoppingOrderDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ShoppingOrderDAO {

    List<PageData> queryShoppingOrderlistPage(Page page); //分页查询

    /**
     * 创建订单
     *
     * @param param
     */
    void saveOrder(ShoppingOrderVO param);

    /**
     * 根据商品IDs获取商品信息
     *
     * @param ids
     * @return
     */
    List<GoodsVO> queryGoodsByIds(@Param("ids") String ids);

    /**
     * 保存订单关联的商品信息
     * <p>
     * * @param param
     */
    int batchSaveOrderGoods(List<ShoppingOrderGoodsVO> param);

    /**
     * 根据订单编号查询
     *
     * @param orderNo
     * @return
     */
    ShoppingOrderVO queryOrderNo(String orderNo);

    ShoppingOrderVO queryOrderId(int id);//根据订单id查询

    void updateOrderState(ShoppingOrderVO shoppingOrderVO);//修改订单状态

    List<ShoppingOrderDTO> queryMyOrder(ShoppingOrderVO shoppingOrderVO); //查询我的订单

    List<ShoppingOrderDTO> queryRefundOrder(ShoppingOrderVO shoppingOrderVO);

    void toUpdate(ShoppingOrderVO shoppingOrderVO); //申请退款

    void updateState(ShoppingOrderVO shoppingOrderVO); //待发货到待收货

    Map<String,Object> queryByOrderId(Integer id);

    void updateOrder(ShoppingOrderVO shoppingOrderVO);

    ShoppingOrderVO queryByAddressIdState(Integer addressId);
}
