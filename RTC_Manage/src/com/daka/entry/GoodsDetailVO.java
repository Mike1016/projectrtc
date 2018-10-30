package com.daka.entry;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GoodsDetailVO
{

	private Integer id;
	private Integer goodsId;
	private Integer type;
	private String goodsAttachmentContent;
	private Integer state;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getGoodsId()
	{
		return goodsId;
	}

	public void setGoodsId(Integer goodsId)
	{
		this.goodsId = goodsId;
	}

	public Integer getType()
	{
		return type;
	}

	public void setType(Integer type)
	{
		this.type = type;
	}

	public String getGoodsAttachmentContent()
	{
		return goodsAttachmentContent;
	}

	public void setGoodsAttachmentContent(String goodsAttachmentContent)
	{
		this.goodsAttachmentContent = goodsAttachmentContent;
	}

	public Integer getState()
	{
		return state;
	}

	public void setState(Integer state)
	{
		this.state = state;
	}
}
