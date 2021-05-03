package com.capgemini.service;

import java.util.List;

import com.capgemini.exceptions.InvalidIdException;
import com.capgemini.exceptions.NoSuchUserExists;
import com.capgemini.exceptions.UserNameAndPasswordDoNotMatchRegularExpression;
import com.capgemini.model.UserDetails;

public interface SweetCherryService {

	//Accessible to Both Administrator and Customer
	public String login(String userName, String password) throws NoSuchUserExists, UserNameAndPasswordDoNotMatchRegularExpression ; 
	public String logout() ;
	
	
	//Administrator services
	public List<UserDetails> allDetailsOfAdminAndUser() throws NoSuchUserExists; 
	

	//Customer Services
	public UserDetails allUserDetailsById(int userId) throws InvalidIdException;
	public UserDetails updateCustomerProfile(UserDetails customer) throws UserNameAndPasswordDoNotMatchRegularExpression;
	public UserDetails registerCustomer(UserDetails registerCustomer) throws UserNameAndPasswordDoNotMatchRegularExpression;
	public String modifyPassword(int userId, String oldPassword, String newPassword) throws UserNameAndPasswordDoNotMatchRegularExpression;
}