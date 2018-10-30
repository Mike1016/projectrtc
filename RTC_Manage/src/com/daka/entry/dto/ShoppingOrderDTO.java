package com.daka.entry.dto;

import com.daka.entry.ShoppingOrderVO;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ShoppingOrderDTO extends ShoppingOrderVO {
    private String goodsAttachmentContent;
    private String goodsName;
    private Integer goodsId;
    private Integer count;
    private java.math.BigDecimal price;
    private java.math.BigDecimal freight;
    private java.math.BigDecimal myShoppingCoin;

    private List<ShoppingOrderDTO> goodsList = new ArrayList<>();

	public List<ShoppingOrderDTO> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<ShoppingOrderDTO> goodsList) {
		this.goodsList = goodsList;
	}

	public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getMyShoppingCoin() {
        return myShoppingCoin;
    }

    public void setMyShoppingCoin(BigDecimal myShoppingCoin) {
        this.myShoppingCoin = myShoppingCoin;
    }

    public String getGoodsAttachmentContent() {
        return goodsAttachmentContent;
    }

    public void setGoodsAttachmentContent(String goodsAttachmentContent) {
        this.goodsAttachmentContent = goodsAttachmentContent;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }
}
