package com.capgemini.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	
	@Column(name = "TOTAL_PRICE")
	private double totalPrice;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private UserDetails roleDetails;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "cupcakeDetails_orders", joinColumns = { @JoinColumn(name = "order_id") }, inverseJoinColumns = { @JoinColumn(name = "cupcake_id") })
	private CupcakeDetails cupcakeDetails;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "status")
	private Payment payment;
	
	
	public Order() {
		super();
	}
	
	public Order(int orderId, String orderDate, int quantity, String orderStatus, UserDetails roleDetails,
			Payment payment) {
		super();
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.quantity = quantity;
		this.orderStatus = orderStatus;
		this.roleDetails = roleDetails;
		this.payment = payment;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public CupcakeDetails getCupcakeDetails() {
		return cupcakeDetails;
	}

	public void setCupcakeDetails(CupcakeDetails cupcakeDetails) {
		this.cupcakeDetails = cupcakeDetails;
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

	public UserDetails getRoleDetails() {
		return roleDetails;
	}

	public void setRoleDetails(UserDetails roleDetails) {
		this.roleDetails = roleDetails;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	
}
