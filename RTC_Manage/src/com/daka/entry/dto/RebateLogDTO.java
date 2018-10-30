package com.daka.entry.dto;

import com.daka.entry.RebateLogVO;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RebateLogDTO extends RebateLogVO {
	private String phone;
	private List<RebateLogDTO> rebates;

	public List<RebateLogDTO> getRebates() {
		return rebates;
	}

	public void setRebates(List<RebateLogDTO> rebates) {
		this.rebates = rebates;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
