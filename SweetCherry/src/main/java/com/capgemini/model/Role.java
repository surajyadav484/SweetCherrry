package com.capgemini.model;

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
	
	@OneToMany(mappedBy = "role")
	private RoleDetails roledetails;
	
	public RoleDetails getRoledetails() {
		return roledetails;
	}

	public void setRoledetails(RoleDetails roledetails) {
		this.roledetails = roledetails;
	}

	public Role() {
		super();
	}
	
	public Role(int roleId, String roleName) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
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
