package com.capgemini.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "prototype")
@Entity
public class Payment {

	@Id
	@Column(name = "PAYMENT_ID", length = 12, nullable = false)
	private int paymentId;

	@Column(name = "CARD_NUMBER", length = 12, nullable = false)
	private long cardNo;

	@Column(name = "CVV", nullable = false)
	private int cvv;

	@Column(name = "CARD_HOLDER_NAME", nullable = false)
	private String cardHolderName;

	@Column(name = "EXPIRY_DATE", nullable = false)
	private String expiryDate;

	@Column(name = "STATUS", nullable = false)
	private String status;

	@Autowired
	@OneToOne
	private Orders order;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		this.status = status;
		this.order = order;
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
		return status;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.status = paymentStatus;
	}

}