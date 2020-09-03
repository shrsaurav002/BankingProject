
package com.rab3tech.vo;

import java.util.Date;

import lombok.Data;

@Data
public class FundTransferVO {

	private int id;
	private String sentFrom;
	private String sentTo;
	private float amount;
	private String remarks;
	private int otp;
	private Date transactionDate;
	
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public FundTransferVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FundTransferVO(int id, String sentFrom, String sentTo, float amount, String remarks, int otp) {
		super();
		this.id = id;
		this.sentFrom = sentFrom;
		this.sentTo = sentTo;
		this.amount = amount;
		this.remarks = remarks;
		this.otp = otp;
	}
	@Override
	public String toString() {
		return "FundTransferVO [id=" + id + ", sentFrom=" + sentFrom + ", sentTo=" + sentTo + ", amount=" + amount
				+ ", remarks=" + remarks + ", otp=" + otp + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSentFrom() {
		return sentFrom;
	}
	public void setSentFrom(String sentFrom) {
		this.sentFrom = sentFrom;
	}
	public String getSentTo() {
		return sentTo;
	}
	public void setSentTo(String sentTo) {
		this.sentTo = sentTo;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getOtp() {
		return otp;
	}
	public void setOtp(int otp) {
		this.otp = otp;
	}
	
	
}
