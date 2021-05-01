package com.capgemini.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.exceptions.InvalidIdException;
import com.capgemini.exceptions.NoMatchingRegex;
import com.capgemini.exceptions.NoSuchUserExists;
import com.capgemini.logger.LoggerController;
import com.capgemini.model.UserDetails;
import com.capgemini.service.SweetCherryServiceImpl;

@RestController
@RequestMapping(path = "user")                           
public class UserController {

	@Autowired
	private SweetCherryServiceImpl service;
	
	private Logger logger = LoggerController.getLogger(UserController.class);
	String methodName = null;
	

	// CUSTOMER SERVICES-------------------------------------------------------------------------------------CUSTOMER SERVICES\\

	//LINK TO ACCESS THIS SERVICE- //http://localhost:9090/sweetcherry-api/user/Login/
	@GetMapping(path = "/Login/{username}/{password}")
	public String Login(@PathVariable("username") String userName, @PathVariable("password") String password) throws NoSuchUserExists{
		
		methodName ="Login(String userName, String password)";
		logger.info(methodName + " method is called");
		
		return service.Login(userName, password); 
	}

	//LINK TO ACCESS THIS SERVICE- //http://localhost:9090/sweetcherry-api/user/userdetails/viewById/
	@GetMapping(path = "/userdetails/viewById/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDetails AllUserDetailsById(@PathVariable("userId") int userId) throws InvalidIdException{
		
		methodName ="AllUserDetailsById(int userId)";
		logger.info(methodName + " method is called");
		
			return service.AllUserDetailsById(userId);
	}

	//LINK TO ACCESS THIS SERVICE- // http://localhost:9090/sweetcherry-api/user/logout
	@GetMapping(path = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
	public String LogOut() {
		
		methodName ="Logout()";
		logger.info(methodName + " method is called");
		
		return service.Logout();
	}

	//LINK TO ACCESS THIS SERVICE- //http://localhost:9090/sweetcherry-api/user/updateUserDetails
	@PutMapping(path = "/updateUserDetails", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public UserDetails UpdateCustomerProfile(@RequestBody UserDetails customer) throws NoMatchingRegex {
		
		methodName ="UpdateCustomerProfile(UserDetails customer)";
		logger.info(methodName + " method is called");
		
		
		return service.UpdateCustomerProfile(customer);
	}

	//LINK TO ACCESS THIS SERVICE- // http://localhost:9090/sweetcherry-api/user/RegisterCustomer
	@PostMapping(path = "/RegisterCustomer", consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserDetails RegisterCustomer(@RequestBody UserDetails registerCustomer) throws NoMatchingRegex {
		
		methodName ="RegisterCustomer(UserDetails registerCustomer)";
		logger.info(methodName + " method is called");
		
		return service.RegisterCustomer(registerCustomer);
	}

	
	//LINK TO ACCESS THIS SERVICE- //http://localhost:9090/sweetcherry-api/user/UpdatePassword 
	  @GetMapping(path="/UpdatePassword/{userId}/{oldPassword}/{newPassword}")
	  public String modifyPassword(@PathVariable("userId") int userId ,@PathVariable("oldPassword") String oldPassword,@PathVariable("newPassword") String newPassword) throws NoMatchingRegex, InvalidIdException {
		 
		  methodName ="modifyPassword(int userId, String oldPassword, String newPassword)";
			logger.info(methodName + " method is called");
		  
		  return service.modifyPassword(userId, oldPassword, newPassword);
		  
	  }
//	@ExceptionHandler(NoSuchUserExists.class)
//	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Details not found")
//	public void GlobalExceptionHandler() {
//	}
}
