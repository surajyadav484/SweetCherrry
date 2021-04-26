package com.capgemini.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class CupcakeCategory {

	@Id
	@Column(name = "CATEGORY_ID")
	private int categoryId;
	
	@Column(name = "CATEGORY_NAME")
	private String categoryName;
	
	@OneToMany(mappedBy = "CupcakeCategory")
	private CupcakeDetails cupcakeDetails;
	
	public CupcakeDetails getCupcakeDetails() {
		return cupcakeDetails;
	}

	public void setCupcakeDetails(CupcakeDetails cupcakeDetails) {
		this.cupcakeDetails = cupcakeDetails;
	}

	public CupcakeCategory() {
		super();
	}
	
	public CupcakeCategory(int categorId, String categoryName) {
		super();
		this.categoryId = categorId;
		this.categoryName = categoryName;
	}
	
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categorId) {
		this.categoryId = categorId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
	
}
