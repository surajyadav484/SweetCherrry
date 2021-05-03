package com.capgemini.controller;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	static final String DESCRIPTION = " method is called";
	
	
	//ADMIN SERVICES ----------------------------------------------------------------------------------------ADMIN SERVICES\\

	//LOGIN ADMIN SERVICE --
	
	//LINK TO ACCESS THIS SERVICE- http://localhost:9090/sweetcherry-api/admin/login/
		@GetMapping(path = "/login/{username}/{password}")
		public String login(@PathVariable("username") String userName, @PathVariable("password") String password) throws NoSuchUserExists {
			
			methodName ="login(String userName, String password)";
			logger.info(methodName, DESCRIPTION);
			
			return service.login(userName, password); 
		}
		
		//LINK TO ACCESS THIS SERVICE- //http://localhost:9090/sweetcherry-api/admin/logout
		@GetMapping(path = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
		public String logOut() {
			
			methodName ="logout()";
			logger.info(methodName, DESCRIPTION);
			
		return service.logout();
		}
		
	//GET ALL DETAILS ADMIN SERVICE --
	
	//LINK TO ACCESS THIS SERVICE-//http://localhost:9090/sweetcherry-api/admin/findall
		@GetMapping(path = "/findall",produces = MediaType.APPLICATION_JSON_VALUE)
		public List<UserDetails> allDetailsOfAdminAndUser() throws NoSuchUserExists {
			
			methodName ="allDetailsOfAdminAndUser()";
			logger.info(methodName, DESCRIPTION);
			
			return service.allDetailsOfAdminAndUser();
}	
}
