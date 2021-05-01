package com.capgemini.dao;

import java.util.List;

import com.capgemini.model.UserDetails;

public interface SweetCherryDao {
	
	//Accessible to Both Administrator and Customer
	public String Login(String userName, String password); // TO LOGIN 
	public String Logout();
	
	//Administrator Services
	public List<UserDetails> AllAdminDetails();
	
	//Customer Services
	public UserDetails AllUserDetailsById(int userId);
	public UserDetails UpdateCustomerProfile(UserDetails customer);
	public UserDetails RegisterCustomer(UserDetails registerCustomer);
	public String UpdatePassword(int userId, String oldPassword, String newPassword); //user id old pass verify database  new pass database update 
}
