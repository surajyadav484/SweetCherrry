package com.capgemini.controller;


import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.exceptions.InvalidIdException;
import com.capgemini.exceptions.NoSuchUserExists;
import com.capgemini.exceptions.UserNameAndPasswordDoNotMatchRegularExpression;
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
	static final String DESCRIPTION = " method is called";
	

	// CUSTOMER SERVICES-------------------------------------------------------------------------------------CUSTOMER SERVICES\\

	//LINK TO ACCESS THIS SERVICE- //http://localhost:9090/sweetcherry-api/user/login/
	@GetMapping(path = "/login/{username}/{password}")
	public String login(@PathVariable("username") String userName, @PathVariable("password") String password) throws NoSuchUserExists{
		
		methodName ="login(String userName, String password)";
		logger.info(methodName, DESCRIPTION);
		
		return service.login(userName, password); 
	}

	//LINK TO ACCESS THIS SERVICE- //http://localhost:9090/sweetcherry-api/user/userdetails/viewById/
	@GetMapping(path = "/userdetails/viewById/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDetails AllUserDetailsById(@PathVariable("userId") int userId) throws InvalidIdException{
		
		methodName ="AllUserDetailsById(int userId)";
		logger.info(methodName, DESCRIPTION);
		
			return service.allUserDetailsById(userId);
	}

	//LINK TO ACCESS THIS SERVICE- // http://localhost:9090/sweetcherry-api/user/logout
	@GetMapping(path = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
	public String logOut(){
		
		methodName ="logout()";
		logger.info(methodName, DESCRIPTION );
		
		return service.logout();
	}

	//LINK TO ACCESS THIS SERVICE- //http://localhost:9090/sweetcherry-api/user/updateUserDetails
	@PutMapping(path = "/updateUserDetails", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public UserDetails updateCustomerProfile(@RequestBody UserDetails customer) throws UserNameAndPasswordDoNotMatchRegularExpression {
		
		methodName ="updateCustomerProfile(UserDetails customer)";
		logger.info(methodName, DESCRIPTION);
		
		
		return service.updateCustomerProfile(customer);
	}

	//LINK TO ACCESS THIS SERVICE- // http://localhost:9090/sweetcherry-api/user/registerCustomer
	@PostMapping(path = "/registerCustomer", consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserDetails registerCustomer(@RequestBody UserDetails registerCustomer) throws UserNameAndPasswordDoNotMatchRegularExpression {
		
		methodName ="registerCustomer(UserDetails registerCustomer)";
		logger.info(methodName,DESCRIPTION);
		
		return service.registerCustomer(registerCustomer);
	}

	
	//LINK TO ACCESS THIS SERVICE- //http://localhost:9090/sweetcherry-api/user/UpdatePassword 
	  @GetMapping(path="/UpdatePassword/{userId}/{oldPassword}/{newPassword}")
	  public String modifyPassword(@PathVariable("userId") int userId ,@PathVariable("oldPassword") String oldPassword,@PathVariable("newPassword") String newPassword) throws UserNameAndPasswordDoNotMatchRegularExpression {
		 
		  methodName ="modifyPassword(int userId, String oldPassword, String newPassword)";
			logger.info(methodName, DESCRIPTION);
		  
		  return service.modifyPassword(userId, oldPassword, newPassword);
		  
	  }
}
