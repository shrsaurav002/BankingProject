package com.rab3tech.vo;

import java.util.Date;

import com.rab3tech.dao.entity.Customer;

public class CreditCardVO {
	private long cardNumber;
	private String name;
	private Date expDate;
	private int secCode;
	private String email;
	private Double currentBalance;
	private Double statementBalance;
	private Double apr;
	private Double minPayment;
	private Double cashbackBonus;
	private int creditScore;
	private CustomerVO customer;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getSecCode() {
		return secCode;
	}

	public void setSecCode(int secCode) {
		this.secCode = secCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(long cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	public Double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public Double getStatementBalance() {
		return statementBalance;
	}

	public void setStatementBalance(Double statementBalance) {
		this.statementBalance = statementBalance;
	}

	public Double getApr() {
		return apr;
	}

	public void setApr(Double apr) {
		this.apr = apr;
	}

	public Double getMinPayment() {
		return minPayment;
	}

	public void setMinPayment(Double minPayment) {
		this.minPayment = minPayment;
	}

	public Double getCashbackBonus() {
		return cashbackBonus;
	}

	public void setCashbackBonus(Double cashbackBonus) {
		this.cashbackBonus = cashbackBonus;
	}

	public int getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(int creditScore) {
		this.creditScore = creditScore;
	}

	public CustomerVO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerVO customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "CreditCardVO [cardNumber=" + cardNumber + ", name=" + name + ", expDate=" + expDate + ", secCode="
				+ secCode + ", email=" + email + ", currentBalance=" + currentBalance + ", statementBalance="
				+ statementBalance + ", apr=" + apr + ", minPayment=" + minPayment + ", cashbackBonus=" + cashbackBonus
				+ ", creditScore=" + creditScore + ", customer=" + customer + "]";
	}

	public CreditCardVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CreditCardVO(long cardNumber, Date expDate, Double currentBalance, Double statementBalance, Double apr,
			Double minPayment, Double cashbackBonus, int creditScore, CustomerVO customer) {
		super();
		this.cardNumber = cardNumber;
		this.expDate = expDate;
		this.currentBalance = currentBalance;
		this.statementBalance = statementBalance;
		this.apr = apr;
		this.minPayment = minPayment;
		this.cashbackBonus = cashbackBonus;
		this.creditScore = creditScore;
		this.customer = customer;
	}

}
