package com.capgemini.service;

import java.util.List;
import java.util.Optional;

import com.capgemini.exceptions.InvalidIdException;
import com.capgemini.exceptions.NoMatchingRegex;
import com.capgemini.exceptions.NoSuchUserExists;
import com.capgemini.model.UserDetails;

public interface SweetCherryService {

	//Accessible to Both Administrator and Customer
	public String Login(String userName, String password) throws NoSuchUserExists, NoMatchingRegex ; 
	public String Logout();
	
	
	//Administrator services
	public List<UserDetails> AllAdminDetails(); 
	

	//Customer Services
	public UserDetails AllUserDetailsById(int userId) throws InvalidIdException;
	public UserDetails UpdateCustomerProfile(UserDetails customer) throws NoMatchingRegex;
	public UserDetails RegisterCustomer(UserDetails registerCustomer) throws NoMatchingRegex;
	public String modifyPassword(int userId, String oldPassword, String newPassword) throws NoMatchingRegex;
}