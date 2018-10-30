package com.daka.entry.dto;

import com.daka.entry.GoodsDetailVO;
import com.daka.entry.GoodsVO;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GoodsDTO extends GoodsVO {
    private String typeName;
    private String goodsAttachmentContent;
    private int salesVolume;
    private List<GoodsDetailVO> goodsImgs;

    public List<GoodsDetailVO> getGoodsImgs() {
        return goodsImgs;
    }

    public void setGoodsImgs(List<GoodsDetailVO> goodsImgs) {
        this.goodsImgs = goodsImgs;
    }

    public int getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(int salesVolume) {
        this.salesVolume = salesVolume;
    }

    public String getGoodsAttachmentContent() {
        return goodsAttachmentContent;
    }

    public void setGoodsAttachmentContent(String goodsAttachmentContent) {
        this.goodsAttachmentContent = goodsAttachmentContent;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
