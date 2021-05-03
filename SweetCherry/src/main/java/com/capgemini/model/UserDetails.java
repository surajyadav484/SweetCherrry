package com.capgemini.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "prototype")
@Entity
public class UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;

	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;

	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;

	@Column(name = "USER_NAME", nullable = false)
	private String email;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	
	  @Autowired
	  
	  @ManyToOne(cascade = CascadeType.ALL)
	  
	  @JoinColumn(name = "roleId")
	  private Role role;
	 
	  @Autowired
	  @OneToMany(cascade = CascadeType.ALL)
	  @JoinColumn(name="USER_ID")
	  private Set<Address> address;
	
		/*
		 * @Autowired
		 * 
		 * @OneToMany(mappedBy = "userDetails") private Set<Orders> order;
		 * 
		 * 
		 * public Set<Orders> getOrder() { return order; }
		 * 
		 * 
		 * 
		 * public void setOrder(Set<Orders> order) { this.order = order; }
		 */
	  public Role getRole() { return role; }
	 

	
	  public void setRole(Role role) { this.role = role; }
	 

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public UserDetails() {
		super();
	}



	public Set<Address> getAddress() {
		return address;
	}



	public void setAddress(Set<Address> address) {
		this.address = address;
	}

	
	
	
	
	/*
	 * public void addOrders(Orders order) { this.getOrder().add(order); }
	 */

}