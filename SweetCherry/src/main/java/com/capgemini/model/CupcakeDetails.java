package com.capgemini.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
	
	@Column(name = "RATING")
	private int rating;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "categoryId")
	private CupcakeCategory cupcakeCategory;
	
	@ManyToMany(mappedBy = "cupcakeDetails" )
	private Set<Order> order;
	
	public CupcakeCategory getCupcakeCategory() {
		return cupcakeCategory;
	}

	public void setCupcakeCategory(CupcakeCategory cupcakeCategory) {
		this.cupcakeCategory = cupcakeCategory;
	}

	public CupcakeDetails() {
		super();
	}
	
	
	
	

	public CupcakeDetails(int cupcakeId, String cupcakeName, String cupcakeDescription, @Min(0) double price,
			@Min(0) int stock, int rating, CupcakeCategory cupcakeCategory, Set<Order> order) {
		super();
		this.cupcakeId = cupcakeId;
		this.cupcakeName = cupcakeName;
		this.cupcakeDescription = cupcakeDescription;
		this.price = price;
		this.stock = stock;
		this.rating = rating;
		this.cupcakeCategory = cupcakeCategory;
		this.order = order;
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

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Set<Order> getOrder() {
		return order;
	}

	public void setOrder(Set<Order> order) {
		this.order = order;
	}

	
	
}
