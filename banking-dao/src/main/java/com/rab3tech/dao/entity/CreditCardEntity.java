package com.rab3tech.dao.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CreditCardEntity {
	@Id
	private long cardNumber;
	private int secCode;
	private Date expDate;
	private String name;
	private String email;
	private Double currentBalance;
	private Double statementBalance;
	private Double apr;
	private Double minPayment;
	private Double cashbackBonus;
	private int creditScore;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_id")
	private Customer customer;

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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public CreditCardEntity(long cardNumber, Date expDate, Double currentBalance, Double statementBalance, Double apr,
			Double minPayment, Double cashbackBonus, int creditScore, Customer customer) {
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

	public CreditCardEntity() {
		super();
	}

	@Override
	public String toString() {
		return "CreditCardEntity [cardNumber=" + cardNumber + ", secCode=" + secCode + ", expDate=" + expDate
				+ ", name=" + name + ", email=" + email + ", currentBalance=" + currentBalance + ", statementBalance="
				+ statementBalance + ", apr=" + apr + ", minPayment=" + minPayment + ", cashbackBonus=" + cashbackBonus
				+ ", creditScore=" + creditScore + ", customer=" + customer + "]";
	}

}
