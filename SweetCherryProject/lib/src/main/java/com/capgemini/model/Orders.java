package com.capgemini.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "prototype")
@Entity
//@NamedQuery(name = "showOrderDetailByUserId", query = "SELECT o FROM Orders o WHERE o.userDetails.userId=:userId") 
//@NamedQuery(name = "showAllOrderDetails", query = "FROM Orders") 
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ID")
	private int orderId;

	@Column(name = "ORDER_DATE", nullable = false)
	private String orderDate;

	@Column(name = "QUANTITY", nullable = false)
	private int quantity;

	@Column(name = "ORDER_STATUS")
	private String orderStatus;

	@Column(name = "TOTAL_PRICE")
	private double totalPrice;

	@Autowired
	@ManyToOne
	@JoinColumn(name = "userId")
	private UserDetails userDetails;

	@Autowired
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "cupcakeDetails_orders", joinColumns = { @JoinColumn(name = "order_id") }, inverseJoinColumns = {
			@JoinColumn(name = "cupcake_id") })
	private Set<CupcakeDetails> cupcakeDetails=null;


	public Orders() {
		super();
	}


	public Orders(int orderId, String orderDate, int quantity, String orderStatus, double totalPrice,
			UserDetails userDetails, Set<CupcakeDetails> cupcakeDetails) {
		super();
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.quantity = quantity;
		this.orderStatus = orderStatus;
		this.totalPrice = totalPrice;
		this.userDetails = userDetails;
		this.cupcakeDetails = cupcakeDetails;
	}


	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Set<CupcakeDetails> getCupcakeDetails() {
		return cupcakeDetails;
	}

	public void setCupcakeDetails(Set<CupcakeDetails> cupcakeDetails) {
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

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}


	

	
	

}