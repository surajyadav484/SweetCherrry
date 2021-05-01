package com.capgemini.controller;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.exceptions.NoSuchUserExists;
import com.capgemini.logger.LoggerController;
import com.capgemini.model.UserDetails;
import com.capgemini.service.SweetCherryServiceImpl;

@RestController
@RequestMapping(path = "admin")
public class AdminController {
	
	@Autowired
	private SweetCherryServiceImpl service ;
	

	private Logger logger = LoggerController.getLogger(AdminController.class);
	String methodName = null;
	
	
	//ADMIN SERVICES ----------------------------------------------------------------------------------------ADMIN SERVICES\\

	//LOGIN ADMIN SERVICE --
	
	//LINK TO ACCESS THIS SERVICE- http://localhost:9090/sweetcherry-api/admin/Login/
		@GetMapping(path = "/Login/{username}/{password}")
		public String Login(@PathVariable("username") String userName, @PathVariable("password") String password) throws NoSuchUserExists {
			
			methodName ="Login(String userName, String password)";
			logger.info(methodName + " method is called");
			
			return service.Login(userName, password); 
		}
		
		//LINK TO ACCESS THIS SERVICE- //http://localhost:9090/sweetcherry-api/admin/Logout
		@GetMapping(path = "/Logout", produces = MediaType.APPLICATION_JSON_VALUE)
		public String LogOut() {
			
			methodName ="Logout()";
			logger.info(methodName + " method is called");
			
		return service.Logout();
		}
		
	//GET ALL DETAILS ADMIN SERVICE --
	
	//LINK TO ACCESS THIS SERVICE-//http://localhost:9090/sweetcherry-api/admin/findall
		@GetMapping(path = "/findall",produces = MediaType.APPLICATION_JSON_VALUE)
		public List<UserDetails> AllAdminDetails(){
			
			methodName ="AllAdminDetails()";
			logger.info(methodName + " method is called");
			
			return service.AllAdminDetails();
}	
}
