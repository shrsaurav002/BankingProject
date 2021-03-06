package com.rab3tech.vo;

import java.util.Arrays;
import java.util.Date;

public class WireTransferVO {
	private int id;
	private String sender;
	private String senderId;
	private byte[] senderIdVerify;
	private String senderEmail;
	private String senderNumber;
	private String receiver;
	private String receiverId;
	private String receiverAddress;
	private Date transferDate;
	private float transferAmount;
	private String confirmCode;

	public WireTransferVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WireTransferVO(int id, String sender, String senderId, byte[] senderIdVerify, String senderEmail,
			String senderNumber, String receiver, String receiverId, String receiverAddress, Date transferDate,
			float transferAmount, String confirmCode) {
		super();
		this.id = id;
		this.sender = sender;
		this.senderId = senderId;
		this.senderIdVerify = senderIdVerify;
		this.senderEmail = senderEmail;
		this.senderNumber = senderNumber;
		this.receiver = receiver;
		this.receiverId = receiverId;
		this.receiverAddress = receiverAddress;
		this.transferDate = transferDate;
		this.transferAmount = transferAmount;
		this.confirmCode = confirmCode;
	}

	@Override
	public String toString() {
		return "WireTransferVO [id=" + id + ", sender=" + sender + ", senderId=" + senderId + ", senderIdVerify="
				+ Arrays.toString(senderIdVerify) + ", senderEmail=" + senderEmail + ", senderNumber=" + senderNumber
				+ ", receiver=" + receiver + ", receiverId=" + receiverId + ", receiverAddress=" + receiverAddress
				+ ", transferDate=" + transferDate + ", transferAmount=" + transferAmount + ", confirmCode="
				+ confirmCode + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public byte[] getSenderIdVerify() {
		return senderIdVerify;
	}

	public void setSenderIdVerify(byte[] senderIdVerify) {
		this.senderIdVerify = senderIdVerify;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public Date getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	public float getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(float transferAmount) {
		this.transferAmount = transferAmount;
	}

	public String getConfirmCode() {
		return confirmCode;
	}

	public void setConfirmCode(String confirmCode) {
		this.confirmCode = confirmCode;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getSenderNumber() {
		return senderNumber;
	}

	public void setSenderNumber(String senderNumber) {
		this.senderNumber = senderNumber;
	}

}
