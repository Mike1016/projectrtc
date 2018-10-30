package com.daka.entry.dto;

import com.daka.entry.GoodsVO;
import com.daka.entry.ShoppingCartVO;

public class ShoppingCartDTO extends ShoppingCartVO {
    private String goodsAttachmentContent ;
    private String goodsName ;
    private String goodsOriginalPrice ;
    private String goodsDiscountPrice ;
    private boolean chosen ;

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

    public String getGoodsOriginalPrice() {
        return goodsOriginalPrice;
    }

    public void setGoodsOriginalPrice(String goodsOriginalPrice) {
        this.goodsOriginalPrice = goodsOriginalPrice;
    }

    public String getGoodsDiscountPrice() {
        return goodsDiscountPrice;
    }

    public void setGoodsDiscountPrice(String goodsDiscountPrice) {
        this.goodsDiscountPrice = goodsDiscountPrice;
    }

    public boolean isChosen() {
        return true;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }
}
