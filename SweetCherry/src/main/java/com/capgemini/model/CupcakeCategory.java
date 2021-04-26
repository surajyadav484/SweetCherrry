package com.capgemini.model;

import java.util.Set;

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
	
	@OneToMany(mappedBy = "cupcakeCategory")
	private Set<CupcakeDetails> cupcakeDetails;
	
	

	


	public Set<CupcakeDetails> getCupcakeDetails() {
		return cupcakeDetails;
	}





	public void setCupcakeDetails(Set<CupcakeDetails> cupcakeDetails) {
		this.cupcakeDetails = cupcakeDetails;
	}





	public CupcakeCategory() {
		super();
	}
	
	
	
	

	


	public CupcakeCategory(int categoryId, String categoryName, Set<CupcakeDetails> cupcakeDetails) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.cupcakeDetails = cupcakeDetails;
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
