package com.capgemini.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Payment {

			
		 @Id
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
		 
		 @Column(name = "CASH_ON_DELIVERY")
		 private boolean cod;
		 
		 @OneToOne
		 @JoinColumn(name="userId")
		 private RoleDetails roleDetails;
		 
		 public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public boolean isCod() {
			return cod;
		}

		public void setCod(boolean cod) {
			this.cod = cod;
		}

		public RoleDetails getRoleDetails() {
			return roleDetails;
		}

		public void setRoleDetails(RoleDetails roleDetails) {
			this.roleDetails = roleDetails;
		}

		public Payment() {
		}

		public Payment(long cardNo, int cvv, String cardHolderName, String expiryDate, String paymentStatus) {
			super();
			this.cardNo = cardNo;
			this.cvv = cvv;
			this.cardHolderName = cardHolderName;
			this.expiryDate = expiryDate;
			this.status = paymentStatus;
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
