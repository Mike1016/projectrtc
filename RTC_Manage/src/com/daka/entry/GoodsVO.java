package com.daka.entry;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.daka.constants.SystemConstant;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)

public class GoodsVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Integer id = 0;
	private Integer goodsTypeId;
	private String goodsName;
	private java.math.BigDecimal goodsOriginalPrice;
	private java.math.BigDecimal goodsDiscountPrice;
	private java.math.BigDecimal goodsDiscountRate;
	private Integer goodsCount;
	private Integer goodsRemainCount;
	private java.math.BigDecimal freight;
	private String createUser;
	private String createTime;
	private String reviewUser;
	private String reviewTime;
	private Integer state;
	private String remarks;

	/* ==============非数据库使用================ */
	private String attrN;

	private String attrV;

	private String coverImgs;

	private String deitalImgs;

	private List<GoodsAttrVO> goodsAttrs;

	private List<GoodsDetailVO> goodsDetailVOs;

	private String typeName;

	public BigDecimal getFreight()
	{
		return freight;
	}

	public void setFreight(BigDecimal freight)
	{
		this.freight = freight;
	}

	public String getTypeName()
	{
		return typeName;
	}

	public void setTypeName(String typeName)
	{
		this.typeName = typeName;
	}

	public List<GoodsAttrVO> getGoodsAttrs()
	{
		return goodsAttrs;
	}

	public void setGoodsAttrs(List<GoodsAttrVO> goodsAttrs)
	{
		this.goodsAttrs = goodsAttrs;
	}

	public List<GoodsDetailVO> getGoodsDetailVOs()
	{
		return goodsDetailVOs;
	}

	public void setGoodsDetailVOs(List<GoodsDetailVO> goodsDetailVOs)
	{
		this.goodsDetailVOs = goodsDetailVOs;
	}

	public String getAttrN()
	{
		return attrN;
	}

	public void setAttrN(String attrN)
	{
		this.attrN = attrN;
	}

	public String getAttrV()
	{
		return attrV;
	}

	public void setAttrV(String attrV)
	{
		this.attrV = attrV;
	}

	public String getCoverImgs()
	{
		return coverImgs;
	}

	public void setCoverImgs(String coverImgs)
	{
		this.coverImgs = coverImgs;
	}

	public String getDeitalImgs()
	{
		return deitalImgs;
	}

	public void setDeitalImgs(String deitalImgs)
	{
		this.deitalImgs = deitalImgs;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getGoodsTypeId()
	{
		return goodsTypeId;
	}

	public void setGoodsTypeId(Integer goodsTypeId)
	{
		this.goodsTypeId = goodsTypeId;
	}

	public String getGoodsName()
	{
		return goodsName;
	}

	public void setGoodsName(String goodsName)
	{
		this.goodsName = goodsName;
	}

	public java.math.BigDecimal getGoodsOriginalPrice()
	{
		return goodsOriginalPrice;
	}

	public void setGoodsOriginalPrice(java.math.BigDecimal goodsOriginalPrice)
	{
		this.goodsOriginalPrice = goodsOriginalPrice;
	}

	public java.math.BigDecimal getGoodsDiscountPrice()
	{
		return new BigDecimal(goodsOriginalPrice.floatValue() * goodsDiscountRate.floatValue() / 10);
	}

	public void setGoodsDiscountPrice(java.math.BigDecimal goodsDiscountPrice)
	{
		this.goodsDiscountPrice = goodsDiscountPrice;
	}

	public java.math.BigDecimal getGoodsDiscountRate()
	{
		return goodsDiscountRate;
	}

	public void setGoodsDiscountRate(java.math.BigDecimal goodsDiscountRate)
	{
		this.goodsDiscountRate = goodsDiscountRate;
	}

	public Integer getGoodsCount()
	{
		return goodsCount;
	}

	public void setGoodsCount(Integer goodsCount)
	{
		this.goodsCount = goodsCount;
	}

	public Integer getGoodsRemainCount()
	{
		return goodsRemainCount;
	}

	public void setGoodsRemainCount(Integer goodsRemainCount)
	{
		this.goodsRemainCount = goodsRemainCount;
	}

	public String getCreateUser()
	{
		return createUser;
	}

	public void setCreateUser(String createUser)
	{
		this.createUser = createUser;
	}

	public String getCreateTime()
	{
		return (null == createTime || createTime.isEmpty()) ? SystemConstant.DATE_FORMAT_2.format(new Date())
				: createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public String getReviewUser()
	{
		return reviewUser;
	}

	public void setReviewUser(String reviewUser)
	{
		this.reviewUser = reviewUser;
	}

	public String getReviewTime()
	{
		return reviewTime;
	}

	public void setReviewTime(String reviewTime)
	{
		this.reviewTime = reviewTime;
	}

	public Integer getState()
	{
		return state;
	}

	public void setState(Integer state)
	{
		this.state = state;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
