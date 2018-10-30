package com.daka.entry;

import java.math.BigDecimal;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ShoppingOrderGoodsVO
{

	private Integer id;
	private Integer orderId;
	private Integer goodsId;
	private Integer count;
	private java.math.BigDecimal shoppingCoin;
	private java.math.BigDecimal price;
	private Integer state;
	private String createTime;

    public ShoppingOrderGoodsVO(int id, int orderId, int count) {
    }

    public Integer getId() {
	    return id;
	}
	public void setId(Integer id) {
	    this.id = id;
	}
	public Integer getOrderId() {
	    return orderId;
	}
	public void setOrderId(Integer orderId) {
	    this.orderId = orderId;
	}
	public Integer getGoodsId() {
	    return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
	    this.goodsId = goodsId;
	}
	public Integer getCount() {
	    return count;
	}
	public void setCount(Integer count) {
	    this.count = count;
	}
	public java.math.BigDecimal getShoppingCoin() {
	    return shoppingCoin;
	}
	public void setShoppingCoin(java.math.BigDecimal shoppingCoin) {
	    this.shoppingCoin = shoppingCoin;
	}
	public java.math.BigDecimal getPrice() {
	    return price;
	}
	public void setPrice(java.math.BigDecimal price) {
	    this.price = price;
	}
	public Integer getState() {
	    return state;
	}
	public void setState(Integer state) {
	    this.state = state;
	}
	public String getCreateTime() {
	    return createTime;
	}
	public void setCreateTime(String createTime) {
	    this.createTime = createTime;
	}
	public ShoppingOrderGoodsVO(Integer id, Integer orderId,
		Integer goodsId, Integer count, BigDecimal shoppingCoin,
		BigDecimal price, Integer state, String createTime) {
	    super();
	    this.id = id;
	    this.orderId = orderId;
	    this.goodsId = goodsId;
	    this.count = count;
	    this.shoppingCoin = shoppingCoin;
	    this.price = price;
	    this.state = state;
	    this.createTime = createTime;
	}
	public ShoppingOrderGoodsVO() {
	    super();
	}

	
}
