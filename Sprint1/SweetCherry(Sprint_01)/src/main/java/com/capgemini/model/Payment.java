package com.capgemini.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "prototype")
@Entity
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PAYMENT_ID")
	private int paymentId;

	@Column(name = "CARD_NUMBER", nullable = false)
	@Length(min = 12, max = 12)
	private long cardNo;

	@Column(name = "CVV", nullable = false)
	@Length(min = 3, max = 3)
	private int cvv;

	@Column(name = "CARD_HOLDER_NAME")
	private String cardHolderName;

	@Column(name = "EXPIRY_DATE", nullable = false)
	private String expiryDate;

	@Column(name = "PAYMENT_STATUS", nullable = false)
	private String paymentStatus;

	@Autowired
	@OneToOne
	private Orders order;

	public String getStatus() {
		return paymentStatus;
	}

	public void setStatus(String status) {
		this.paymentStatus = status;
	}

	public Payment() {
	}

	public Payment(int paymentId, long cardNo, int cvv, String cardHolderName, String expiryDate, String status,
			Orders order) {
		super();
		this.paymentId = paymentId;
		this.cardNo = cardNo;
		this.cvv = cvv;
		this.cardHolderName = cardHolderName;
		this.expiryDate = expiryDate;
		this.paymentStatus = status;
		this.order = order;
		// this.userDetails = roleDetails;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public long getCardNo() {
		return cardNo;
	}

	public void setCardNo(long cardNo) {
		this.cardNo = cardNo;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", cardNo=" + cardNo + ", cvv=" + cvv + ", cardHolderName="
				+ cardHolderName + ", expiryDate=" + expiryDate + ", paymentStatus=" + paymentStatus + ", order="
				+ order + "]";
	}

}