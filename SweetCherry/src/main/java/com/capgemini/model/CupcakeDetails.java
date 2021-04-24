package com.capgemini.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

@Entity
public class CupcakeDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CUPCAKE_ID")
	private int cupcakeId;
	
	@Column(name = "CUPCAKE_NAME" , nullable = false)
	private String cupcakeName;
	
	@Column(name = "CUPCAKE_DESCRIPTION" ,nullable = false)
	private String cupcakeDescription;
	
	@Column(name = "PRICE" )
	@Min(value = 0)
	private double price;
	
	@Column(name = "STOCK")
	@Min(value = 0)
	private int stock;
	
	@ManyToOne
	@JoinColumn(name = "categoryId")
	private CupcakeCategory cupcakeCategory;
	
	public CupcakeCategory getCupcakeCategory() {
		return cupcakeCategory;
	}

	public void setCupcakeCategory(CupcakeCategory cupcakeCategory) {
		this.cupcakeCategory = cupcakeCategory;
	}

	public CupcakeDetails() {
		super();
	}
	
	public CupcakeDetails(int cupcakeId, String cupcakeName, String cupcakeDescription, double price, int stock) {
		super();
		this.cupcakeId = cupcakeId;
		this.cupcakeName = cupcakeName;
		this.cupcakeDescription = cupcakeDescription;
		this.price = price;
		this.stock = stock;
	}
	
	public int getCupcakeId() {
		return cupcakeId;
	}
	public void setCupcakeId(int cupcakeId) {
		this.cupcakeId = cupcakeId;
	}
	public String getCupcakeName() {
		return cupcakeName;
	}
	public void setCupcakeName(String cupcakeName) {
		this.cupcakeName = cupcakeName;
	}
	public String getCupcakeDescription() {
		return cupcakeDescription;
	}
	public void setCupcakeDescription(String cupcakeDescription) {
		this.cupcakeDescription = cupcakeDescription;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	
}
