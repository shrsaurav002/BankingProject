package com.rab3tech.vo;

import java.util.List;

import lombok.Data;


@Data
public class LoadLocationAndAccountVO {

	private List<LocationVO> locationNames;
	private List<AccountTypeVO> accountNames;
	public List<LocationVO> getLocationNames() {
		return locationNames;
	}
	public void setLocationNames(List<LocationVO> locationNames) {
		this.locationNames = locationNames;
	}
	public List<AccountTypeVO> getAccountNames() {
		return accountNames;
	}
	public void setAccountNames(List<AccountTypeVO> accountNames) {
		this.accountNames = accountNames;
	}
	
	
	
}
