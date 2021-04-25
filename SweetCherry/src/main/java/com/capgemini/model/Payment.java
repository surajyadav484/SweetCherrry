package com.capgemini.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Payment {

			
		 @Id
		 @Column(name = "PAYMENT_ID", length = 12 ,nullable = false)
		 private int paymentId; 
		 
		 @Column(name = "CARD_NUMBER", length = 12 ,nullable = false)
		 private long cardNo;
		 
		 @Column(name = "CVV",nullable = false)
		 private int cvv;
		 
		 @Column(name = "CARD_HOLDER_NAME" , nullable = false)
		 private String cardHolderName;
		 
		 @Column(name = "EXPIRY_DATE" ,nullable = false)
		 private String expiryDate;
		 
		 @Column(name = "STATUS", nullable = false)
		 private String status;
		 
		 @OneToOne(mappedBy = "Payments" )
		 private Order order;
		 
		 
		 @OneToMany
		 @JoinColumn(name="userId")
		 private UserDetails roleDetails;
		 
		 public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}


		public UserDetails getRoleDetails() {
			return roleDetails;
		}

		public void setRoleDetails(UserDetails roleDetails) {
			this.roleDetails = roleDetails;
		}

		public Payment() {
		}
		

		public Payment(int paymentId, long cardNo, int cvv, String cardHolderName, String expiryDate, String status,
				Order order, UserDetails roleDetails) {
			super();
			this.paymentId = paymentId;
			this.cardNo = cardNo;
			this.cvv = cvv;
			this.cardHolderName = cardHolderName;
			this.expiryDate = expiryDate;
			this.status = status;
			this.order = order;
			this.roleDetails = roleDetails;
		}

		public int getPaymentId() {
			return paymentId;
		}

		public void setPaymentId(int paymentId) {
			this.paymentId = paymentId;
		}

		public Order getOrder() {
			return order;
		}

		public void setOrder(Order order) {
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
