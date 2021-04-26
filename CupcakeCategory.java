package com.capgemini.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "prototype")
@Entity
public class CupcakeCategory {

	@Id
	@Column(name = "CATEGORY_ID")
	private int categoryId;
	
	@Column(name = "CATEGORY_NAME")
	private String categoryName;
	
	public CupcakeCategory() {
		super();
	}
	
	public CupcakeCategory(int categoryId, String categoryName) {
		super();
		this.categoryId = categoryId;
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
