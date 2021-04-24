package com.capgemini.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ID" ,nullable = false)
	private int orderId;
	
	@Column(name = "ORDER_DATE" ,nullable = false)
	private String orderDate;
	
	@Column(name = "QUANTITY" ,nullable = false)
	private int quantity;
	
	@Column(name = "ORDER_STATUS" )
	private String orderStatus;
	
	@OneToOne
	@JoinColumn(name = "userId")
	private RoleDetails roleDetails;

	@OneToOne
	@JoinColumn(name = "status")
	private Payment payment;
	
	
	public Order() {
		super();
	}
	
	public Order(int orderId, String orderDate, int quantity, String orderStatus, RoleDetails roleDetails,
			Payment payment) {
		super();
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.quantity = quantity;
		this.orderStatus = orderStatus;
		this.roleDetails = roleDetails;
		this.payment = payment;
	}

	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public RoleDetails getRoleDetails() {
		return roleDetails;
	}

	public void setRoleDetails(RoleDetails roleDetails) {
		this.roleDetails = roleDetails;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	
}
