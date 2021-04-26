package com.capgemini.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Role {

	@Id
	@Column(name = "ROLE_ID")
	private int roleId;
	
	@Column(name = "ROLE_NAME")
	private String roleName;
	
	@OneToMany(mappedBy = "userId")
	private Set<UserDetails> userdetails;
	
	

	public Set<UserDetails> getUserdetails() {
		return userdetails;
	}

	public void setUserdetails(Set<UserDetails> userdetails) {
		this.userdetails = userdetails;
	}

	public Role() {
		super();
	}
	

	public Role(int roleId, String roleName, Set<UserDetails> userdetails) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.userdetails = userdetails;
	}

	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}
