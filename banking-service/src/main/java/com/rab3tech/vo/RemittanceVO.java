package com.rab3tech.vo;

public class RemittanceVO {
	private int id;
	private String sentFrom;
	private String recName;
	private String recPhone;
	private String location;
	private float amount;
	private String remarks;

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

	public RemittanceVO(int id, String sentFrom, String recName, String recPhone, String location, float amount,
			String remarks) {
		super();
		this.id = id;
		this.sentFrom = sentFrom;
		this.recName = recName;
		this.recPhone = recPhone;
		this.location = location;
		this.amount = amount;
		this.remarks = remarks;
	}

	public RemittanceVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "RemittanceVO [id=" + id + ", sentFrom=" + sentFrom + ", recName=" + recName + ", recPhone=" + recPhone
				+ ", location=" + location + ", amount=" + amount + ", remarks=" + remarks + "]";
	}

}