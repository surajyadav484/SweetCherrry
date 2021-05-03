package com.capgemini.controller;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.exceptions.NoSuchAddressExists;
import com.capgemini.exceptions.NoSuchCupcakeExists;
import com.capgemini.exceptions.NoSuchOrderExists;
import com.capgemini.exceptions.NoSuchUserExists;
import com.capgemini.exceptions.UserNameAndPasswordDoNotMatchRegularExpression;
import com.capgemini.logger.LoggerController;
import com.capgemini.model.Address;
import com.capgemini.model.CupcakeCategory;
import com.capgemini.model.CupcakeDetails;
import com.capgemini.model.Orders;
import com.capgemini.model.UserDetails;
import com.capgemini.service.SweetCherryService;


@RestController
@RequestMapping(path = "admin")
public class AdminController {

	@Autowired
	private SweetCherryService cupcakeservice;
	
	private Logger logger = LoggerController.getLogger(AdminController.class);
	String methodName = null;
	static final  String  DESCRIPTION = " controller is called from AdminController";

	// 1. LOGIN MODULE (ADMIN SIDE)--------------------------------------------------------------------------------------------------------
	
	
	//ADMIN SERVICES ----------------------------------------------------------------------------------------ADMIN SERVICES\\

		//LOGIN ADMIN SERVICE --
		
		//LINK TO ACCESS THIS SERVICE- http://localhost:9090/sweetcherry-api/admin/login/
			@GetMapping(path = "/login/{username}/{password}")
			public String login(@PathVariable("username") String userName, @PathVariable("password") String password) throws NoSuchUserExists, UserNameAndPasswordDoNotMatchRegularExpression {
				
				methodName ="login(String userName, String password)";
				logger.info(methodName, DESCRIPTION);
				
				return cupcakeservice.login(userName, password); 
			}
			
			//LINK TO ACCESS THIS SERVICE- //http://localhost:9090/sweetcherry-api/admin/logout
			@GetMapping(path = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
			public String logOut() {
				
				methodName ="logout()";
				logger.info(methodName, DESCRIPTION);
				
			return cupcakeservice.logout();
			}
			
		//GET ALL DETAILS ADMIN SERVICE --
		
		//LINK TO ACCESS THIS SERVICE-//http://localhost:9090/sweetcherry-api/admin/findall
			@GetMapping(path = "/findall",produces = MediaType.APPLICATION_JSON_VALUE)
			public List<UserDetails> allDetailsOfAdminAndUser() throws NoSuchUserExists {
				
				methodName ="allDetailsOfAdminAndUser()";
				logger.info(methodName, DESCRIPTION);
				
				return cupcakeservice.allDetailsOfAdminAndUser();
	}	
	
	
	
	
	
	
	// 2. CUPCAKE  MODULE(ADMIN SIDE)------------------------------------------------------------------------------------------------------------------------

	
	
	// ADD CUPCAKE CATEGORY
	// http://localhost:9090/sweetcherry-api/admin/addCupcakeCategory
	@PostMapping(path = "addCupcakeCategory")
	public CupcakeCategory addCupcakeCategory(@RequestBody CupcakeCategory cupcakeCategory) {
		return cupcakeservice.addCupcakeCategory(cupcakeCategory);
	}
	
	
	
	// ADD CUPCAKE TO DATABASE
	// http://localhost:9090/sweetcherry-api/admin/addCupcake
	@PostMapping(path = "addCupcake")
	public CupcakeDetails addCupcakeDetails(@RequestBody CupcakeDetails cupcakeDetails) throws NoSuchCupcakeExists {
		methodName = "addCupcakeDetails(CupcakeDetails cupcakeDetails)";
		logger.info(methodName , DESCRIPTION);
		return cupcakeservice.addCupcakeDetails(cupcakeDetails);
	}
	
	
	// UPDATE CUPCAKE PRICE USING CUPCAKE ID
	// http://localhost:9090/sweetcherry-api/admin/modifyCupcakePrice/
	@GetMapping(produces = "application/json", path = "modifyCupcakePrice/{cupcakeId}/{cupcakePrice}")
	public CupcakeDetails modifyCupcakePrice(@PathVariable("cupcakeId") int cupcakeId,@PathVariable("cupcakePrice") double cupcakePrice ) throws NoSuchCupcakeExists {
		methodName = "modifyCupcakePrice(int cupcakeId, double cupcakePrice )";
		logger.info(methodName , DESCRIPTION);
		return cupcakeservice.updateCupcakePriceByCupcakeId(cupcakeId, cupcakePrice);
	}

	
	// UPDATE CUPCAKE NAME USING CUPCAKE ID
	// http://localhost:9090/sweetcherry-api/admin/modifyCupcakeName
	@GetMapping( produces ="application/json", path = "modifyCupcakeName/{cupcakeId}/{cupcakeName}") 
	public CupcakeDetails modifyCupcakeName(@PathVariable("cupcakeId") int cupcakeId,@PathVariable("cupcakeName") String cupcakeName) throws NoSuchCupcakeExists { 
		methodName = "modifyCupcakeName(int cupcakeId,String cupcakeName)";
		logger.info(methodName , DESCRIPTION);
		return cupcakeservice.modifyCupcakeName(cupcakeId,cupcakeName); 
	}

	
	// DELETE SPECIFIC CUPCAKE USING CUPCAKE ID
	// http://localhost:9090/sweetcherry-api/admin/removeCupcakeDeatils/
	@DeleteMapping(path = "/removeCupcakeDeatils/{cupcakeId}") 
	 public String removeCupcakeDetailsById(@PathVariable("cupcakeId") int cupcakeId) throws NoSuchCupcakeExists { 
		 methodName = "removeCupcakeDetailsById( int cupcakeId)";
		logger.info(methodName , DESCRIPTION);
		 return cupcakeservice.removeCupcakeDetails(cupcakeId); 
	}
	
	
	// 3. ORDER MODULE(ADMIN SIDE)-----------------------------------------------------------------------------------------------------------------
	 
	
	// http://localhost:9090/sweetcherry-api/admin/viewOrderDetails
		@GetMapping(path = "/viewOrderDetails", produces = "application/json") // @GetMapping is used for mapping http GET
																				// requests
		public List<Orders> showAllOrderDetails() throws NoSuchOrderExists { // showAllOrderDetails() method will return the
																				// list of order details
			methodName = "showAllOrderDetails()";
			
			logger.info(methodName, DESCRIPTION); // use of info level logger
			return cupcakeservice.getAllOrderDetails(); // getAllOrderDetails() method is called from SweetCherryServiceImpl Class
		}

		// http://localhost:9090/sweetcherry-api/admin/viewDeliveryAddress/1
		@GetMapping(path = "/viewDeliveryAddress/{addressId}", produces = "application/json") // @GetMapping is used for
																								// mapping http GET requests

		public Address showDeliveryAddress(@PathVariable("addressId") int addressId) throws NoSuchAddressExists { // showDeliveryAddress(int
																													// addressId)
																													// will
																													// return
																													// theAddrress
																													// object
			methodName = " showDeliveryAddress(int addressId) ";
			logger.info(methodName , DESCRIPTION); // use of info level logger
			return cupcakeservice.getDeliveryAddress(addressId); // getDeliveryAddress(addressId) method is called from
															// SweetCherryServiceImpl Class
		}

		// http://localhost:9090/sweetcherry-api/admin/printOrderInvoice/1
		@GetMapping(path = "/printOrderInvoice/{orderId}", produces = "application/json") // @GetMapping is used for mapping
																							// http GET requests
		public Orders getOrderDetailsByOrderId(@PathVariable("orderId") int orderId) throws NoSuchOrderExists { // getOrderDetailsByOrderId(int
																												// orderId)
																												// will
																												// return
																												// specific
																												// Order
																												// Details
			methodName = " getOrderDetailsByOrderId(int orderId)";
			logger.info(methodName , DESCRIPTION); // use of info level logger
			return cupcakeservice.getOrderDetailsById(orderId); // getOrderDetailsById(orderId) is called from
															// SweetCherryServiceImpl Class

		}

	
	
}
