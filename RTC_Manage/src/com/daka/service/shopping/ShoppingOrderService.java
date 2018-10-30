package com.daka.service.shopping;

import com.daka.dao.customer.CustomerDAO;
import com.daka.dao.goods.GoodsDAO;
import com.daka.dao.shopping.ShoppingCartDAO;
import com.daka.dao.shopping.ShoppingCoinLogDAO;
import com.daka.dao.shopping.ShoppingOrderDAO;
import com.daka.entry.*;
import com.daka.entry.dto.ShoppingOrderDTO;
import com.daka.service.goods.GoodsService;
import com.daka.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ShoppingOrderService {

    @Autowired
    ShoppingCoinLogDAO shoppingCoinLogDAO;

    @Autowired
    private ShoppingOrderDAO shoppingOrderDAO;

    @Autowired
    CustomerDAO customerDAO;

    @Autowired
    GoodsDAO goodsDAO;

    @Autowired
    ShoppingCartDAO shoppingCartDAO;

    public List<PageData> queryShoppingOrderlistPage(Page page) {
        return shoppingOrderDAO.queryShoppingOrderlistPage(page);
    }

    public Map<String,Object> queryByOrderId(Integer id) {
        return shoppingOrderDAO.queryByOrderId(id);
    }

    public void toUpdate(ShoppingOrderVO shoppingOrderVO) {
        shoppingOrderDAO.toUpdate(shoppingOrderVO);
    }

    public void updateState(ShoppingOrderVO shoppingOrderVO) {
        shoppingOrderDAO.updateState(shoppingOrderVO);
    }

    /**
     * 生成订单
     *
     * @param param list map:goodsId:111,count:2
     */
    @Transactional(rollbackFor =
            {Exception.class, RuntimeException.class, Throwable.class}, readOnly = false)
    public int createOrderWithGoods(List<Map<String, String>> param, int addressId, int customerId,String cart) throws Exception {
        // 检查参数
        if (null == Optional.ofNullable(param).orElse(null)
                || Optional.ofNullable(customerId).orElse(-1) == -1) {
            return 0;
        }

        // 1. 创建订单
        int orderId = createOrder(addressId, customerId);

        // 2.关联订单的下属商品
        createOrderGoodsRelation(param, orderId, customerId,cart);
        return orderId;
    }

    @Transactional(rollbackFor =
            {Exception.class, RuntimeException.class, Throwable.class}, readOnly = false)
    public void createOrderGoodsRelation(List<Map<String, String>> param, int orderId, int customerId,String cart) throws Exception{
        List<String> idList = new ArrayList<>();

        for (Map<String, String> map : param) {
            idList.add(map.get("goodsId"));
        }

        String inSql = Tools.buildInSQL(idList, "id", false);
        if (inSql != null){
            String goodsId = inSql.replaceAll("id","goods_id");
            if ("cart".equals(cart)){
                shoppingCartDAO.updateCartState(goodsId);
            }
        }
        // 根据IDs查询商品信息
        List<GoodsVO> goods = shoppingOrderDAO.queryGoodsByIds(inSql);

        // 构建商品ID与商品VO的map
        Map<String, Object> id2Goods = Tools.buildField2VOMap(goods,"id");

        List<ShoppingOrderGoodsVO> saveParams = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        for (Map<String, String> obj : param) {
            Object tempObj = id2Goods.get(obj.get("goodsId"));
            GoodsVO temp = null == tempObj ? null : (GoodsVO) tempObj;
            if (null == temp) {
                continue;
            }
            int count = Integer.parseInt(obj.get("count"));
            if(((GoodsVO) tempObj).getGoodsRemainCount() < count){
                throw new Exception("商品数量超出库存");
            }
            Double price =((GoodsVO) tempObj).getGoodsDiscountPrice().doubleValue();
            ShoppingOrderGoodsVO saveParam = new ShoppingOrderGoodsVO();
            saveParam.setGoodsId(temp.getId());
            saveParam.setOrderId(orderId);
            saveParam.setCount(count);
            saveParam.setPrice(BigDecimal.valueOf(price));
            BigDecimal allPrice = BigDecimal.valueOf(price).multiply(BigDecimal.valueOf(Long.valueOf(count)));
            saveParam.setShoppingCoin(allPrice);
            saveParam.setCreateTime(time);
            saveParams.add(saveParam);
        }
        //生成订单商品
        int row = shoppingOrderDAO.batchSaveOrderGoods(saveParams);
        if (row > 0){
             goodsDAO.updateGoodsCountReduce(param);
        }
    }

    /**
     * 创建订单
     *
     * @param addressId    --地址
     * @param customerId --用户ID
     * @return 订单编号
     */
    private int createOrder(int addressId, int customerId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String orderNo = String.valueOf(System.currentTimeMillis());
        ShoppingOrderVO saveParam = new ShoppingOrderVO();
        saveParam.setCustomerId(customerId);
        saveParam.setOrderNo(orderNo);
        saveParam.setReceiovingAddressId(addressId);
        saveParam.setState(0);
        saveParam.setCreateTime(sdf.format(new Date()));
        System.out.println(saveParam);
        shoppingOrderDAO.saveOrder(saveParam);
        ShoppingOrderVO shoppingOrderVO = shoppingOrderDAO.queryOrderNo(orderNo);
        return shoppingOrderVO.getId();
    }

    /**
     * 修改订单状态
     * @param shoppingOrderVO
     */
    public void updateOrderState(ShoppingOrderVO shoppingOrderVO){
        shoppingOrderDAO.updateOrderState(shoppingOrderVO);
    }

    /**
     * 根据订单id查询
     * @param id
     * @return
     */
    public ShoppingOrderVO queryOrderId(int id){
        return shoppingOrderDAO.queryOrderId(id);
    }

    //查询我的订单
    public List<ShoppingOrderDTO> queryMyOrder(ShoppingOrderVO shoppingOrderVO){
        return shoppingOrderDAO.queryMyOrder(shoppingOrderVO);
    }

    //退款、退单
    public int updateRefund(int states, int orderId, int customerId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
        ShoppingOrderVO shoppingOrderVO = new ShoppingOrderVO();
        List<ShoppingOrderDTO> list = null;
        shoppingOrderVO.setCustomerId(customerId);
        shoppingOrderVO.setId(orderId);
        if (states == 1) {
            shoppingOrderVO.setState(0);
            list = queryRefundOrder(shoppingOrderVO);

            ShoppingOrderVO orderVO = new ShoppingOrderVO();
            orderVO.setId(orderId);
            orderVO.setState(1);
            shoppingOrderDAO.updateOrderState(orderVO);
        } else if (states == 6) {
            shoppingOrderVO.setState(6);
            list = queryRefundOrder(shoppingOrderVO);
            //查询消费记录
            ShoppingCoinLogVO coinVO = shoppingCoinLogDAO.queryCoin(Integer.valueOf(orderId));
            CustomerVO vo = new CustomerVO();
            vo.setId(customerId);
            vo.setShoppingCoin(coinVO.getShoppingCoin());
            customerDAO.updateShoppingCoin(vo);
            //生成消费记录
            ShoppingCoinLogVO shoppingCoinLogVO = new ShoppingCoinLogVO();
            shoppingCoinLogVO.setShoppingCoin(coinVO.getShoppingCoin());
            shoppingCoinLogVO.setCustomerId(customerId);
            shoppingCoinLogVO.setCreateTime(sdf.format(new Date()));
            shoppingCoinLogVO.setRemark("退款");
            shoppingCoinLogVO.setOrderId(Integer.valueOf(orderId));
            shoppingCoinLogDAO.saveCoin(shoppingCoinLogVO);
        } else {
            return -1;
        }
        Map<String, String> map = new HashMap<String, String>();

        for (int i = 0; i < list.size(); i++) {
            ShoppingOrderDTO dto = list.get(i);
            map.put("goodsId", dto.getGoodsId() + "");
            map.put("count", dto.getCount() + "");
            listMap.add(map);
        }
        if (listMap.size() > 0) {
            goodsDAO.updateGoodsCountPlus(listMap);
            return 1;
        }
        return -1;
    }

    public List<ShoppingOrderDTO> queryRefundOrder(ShoppingOrderVO shoppingOrderVO) {
        return shoppingOrderDAO.queryRefundOrder(shoppingOrderVO);
    }

    public void updateOrder(ShoppingOrderVO shoppingOrderVO){
        shoppingOrderDAO.updateOrder(shoppingOrderVO);
    }

    public ShoppingOrderVO queryByAddressIdState(Integer addressId){
        return shoppingOrderDAO.queryByAddressIdState(addressId);
    }
}
