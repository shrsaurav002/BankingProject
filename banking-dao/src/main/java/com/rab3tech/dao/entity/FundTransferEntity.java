
package com.rab3tech.dao.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class FundTransferEntity {

	@Id
	@GeneratedValue
	private int id;

	@OneToOne
	private CustomerAccountInfo sentFrom;

	@OneToOne
	private CustomerAccountInfo sentTo;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CustomerAccountInfo getSentFrom() {
		return sentFrom;
	}

	public void setSentFrom(CustomerAccountInfo sentFrom) {
		this.sentFrom = sentFrom;
	}

	public CustomerAccountInfo getSentTo() {
		return sentTo;
	}

	public void setSentTo(CustomerAccountInfo sentTo) {
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

	@Override
	public String toString() {
		return "FundTransferEntity [id=" + id + ", sentFrom=" + sentFrom + ", sentTo=" + sentTo + ", amount=" + amount
				+ ", remarks=" + remarks + ", otp=" + otp + ", transactionDate=" + transactionDate + "]";
	}

	public FundTransferEntity(int id, CustomerAccountInfo sentFrom, CustomerAccountInfo sentTo, float amount,
			String remarks, int otp) {
		super();
		this.id = id;
		this.sentFrom = sentFrom;
		this.sentTo = sentTo;
		this.amount = amount;
		this.remarks = remarks;
		this.otp = otp;
	}

	public FundTransferEntity() {
		super();
	}

}
