package com.rab3tech.dao.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "remittance_tbl")
public class RemittanceEntity {
	@Id
	@GeneratedValue
	private int id;
	@ManyToOne(cascade = CascadeType.ALL)
	private CustomerAccountInfo sentFrom;
	private String recName;
	private String recPhone;
	private String location;
	private float amount;
	private String remarks;
	private String confirmCode;

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

	public String getRecName() {
		return recName;
	}

	public void setRecName(String recName) {
		this.recName = recName;
	}

	public String getRecPhone() {
		return recPhone;
	}

	public void setRecPhone(String recPhone) {
		this.recPhone = recPhone;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public String getConfirmCode() {
		return confirmCode;
	}

	public void setConfirmCode(String confirmCode) {
		this.confirmCode = confirmCode;
	}

	@Override
	public String toString() {
		return "RemittanceEntity [id=" + id + ", sentFrom=" + sentFrom + ", recName=" + recName + ", recPhone="
				+ recPhone + ", location=" + location + ", amount=" + amount + ", remarks=" + remarks + ", confirmCode="
				+ confirmCode + "]";
	}

	public RemittanceEntity(int id, CustomerAccountInfo sentFrom, String recName, String recPhone, String location,
			float amount, String remarks, String confirmCode) {
		super();
		this.id = id;
		this.sentFrom = sentFrom;
		this.recName = recName;
		this.recPhone = recPhone;
		this.location = location;
		this.amount = amount;
		this.remarks = remarks;
		this.confirmCode = confirmCode;
	}

	public RemittanceEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

}
