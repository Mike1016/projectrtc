package com.daka.entry.dto;

import com.daka.entry.CustomerVO;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.math.BigDecimal;
import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CustomerDTO extends CustomerVO {
	private Integer pushCount;
	private Integer teamCount;
	private BigDecimal wallet;
	private List<CustomerDTO> childs;
	private Integer rank;

	private Integer ID1;
	private Integer ID2;
	private Integer ID3;

	private List<CustomerDTO> level1;
	private List<CustomerDTO> level2;
	private List<CustomerDTO> level3;
	
	private BigDecimal rechargeAccount;//累计充值
	private BigDecimal presentationAccount;//累积提现
	private BigDecimal refundable;//退款额度

	public BigDecimal getRefundable() {
		return refundable;
	}

	public void setRefundable(BigDecimal refundable) {
		this.refundable = refundable;
	}

	public BigDecimal getRechargeAccount() {
		return rechargeAccount;
	}

	public void setRechargeAccount(BigDecimal rechargeAccount) {
		this.rechargeAccount = rechargeAccount;
	}

	public BigDecimal getPresentationAccount() {
		return presentationAccount;
	}

	public void setPresentationAccount(BigDecimal presentationAccount) {
		this.presentationAccount = presentationAccount;
	}

	public List<CustomerDTO> getLevel1() {
		return level1;
	}

	public void setLevel1(List<CustomerDTO> level1) {
		this.level1 = level1;
	}

	public List<CustomerDTO> getLevel2() {
		return level2;
	}

	public void setLevel2(List<CustomerDTO> level2) {
		this.level2 = level2;
	}

	public List<CustomerDTO> getLevel3() {
		return level3;
	}

	public void setLevel3(List<CustomerDTO> level3) {
		this.level3 = level3;
	}

	public Integer getID1() {
		return ID1;
	}

	public void setID1(Integer ID1) {
		this.ID1 = ID1;
	}

	public Integer getID2() {
		return ID2;
	}

	public void setID2(Integer ID2) {
		this.ID2 = ID2;
	}

	public Integer getID3() {
		return ID3;
	}

	public void setID3(Integer ID3) {
		this.ID3 = ID3;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public List<CustomerDTO> getChilds() {
		return childs;
	}

	public void setChilds(List<CustomerDTO> childs) {
		this.childs = childs;
	}

	public BigDecimal getWallet() {
		return wallet;
	}

	public void setWallet(BigDecimal wallet) {
		this.wallet = wallet;
	}

	public Integer getPushCount() {
		return pushCount;
	}

	public void setPushCount(Integer pushCount) {
		this.pushCount = pushCount;
	}

	public Integer getTeamCount() {
		return teamCount;
	}

	public void setTeamCount(Integer teamCount) {
		this.teamCount = teamCount;
	}

}
