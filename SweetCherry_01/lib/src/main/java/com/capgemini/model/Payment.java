package com.capgemini.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	@Column(name = "PAYMENT_ID")//, nullable = false)
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

	/*
	 * @Autowired
	 * 
	 * @OneToMany
	 * 
	 * @JoinColumn(name="userId") private Set<UserDetails> userDetails;
	 */

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/*
	 * public Set<UserDetails> getUserDetails() { return userDetails; }
	 * 
	 * public void setUserDetails(Set<UserDetails> userDetails) { this.userDetails =
	 * userDetails; }
	 */

	public Payment() {
	}

	public Payment(int paymentId, String cardHolderName) {
		super();
		this.paymentId = paymentId;
		this.cardHolderName = cardHolderName;
	}
	
	

	public Payment(String cardHolderName) {
		super();
		this.cardHolderName = cardHolderName;
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
		return status;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.status = paymentStatus;
	}

	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", cardNo=" + cardNo + ", cvv=" + cvv + ", cardHolderName="
				+ cardHolderName + ", expiryDate=" + expiryDate + ", status=" + status + ", order=" + order + "]";
	}

}