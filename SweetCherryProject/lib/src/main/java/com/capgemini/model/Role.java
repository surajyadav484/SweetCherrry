package com.capgemini.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "prototype")
@Entity
public class Role {

	@Id
	@Column(name = "ROLE_ID",nullable = false)
	private int roleId;

	@Column(name = "ROLE_NAME",nullable = false)
	private String roleName;

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