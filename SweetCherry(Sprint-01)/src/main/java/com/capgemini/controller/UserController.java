package com.capgemini.controller;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.exceptions.InvalidIdException;
import com.capgemini.exceptions.NoSuchAddressExists;
import com.capgemini.exceptions.NoSuchCupcakeExists;
import com.capgemini.exceptions.NoSuchOrderExists;
import com.capgemini.exceptions.NoSuchUserExists;
import com.capgemini.exceptions.PaymentFailedException;
import com.capgemini.exceptions.UserNameAndPasswordDoNotMatchRegularExpression;
import com.capgemini.logger.LoggerController;
import com.capgemini.model.CupcakeDetails;
import com.capgemini.model.Orders;
import com.capgemini.model.Payment;
import com.capgemini.model.UserDetails;
import com.capgemini.service.SweetCherryService;

@RestController
@RequestMapping(path = "user")
public class UserController {

	@Autowired
	private SweetCherryService cupcakeservice;

	private Logger logger = LoggerController.getLogger(UserController.class);
	String methodName = null;
	static final  String  DESCRIPTION = " controller is called from AdminController";

	
	// 1. LOGIN MODULE(USER SIDE)---------------------------------------------------------------------------------------------------------------------------
	
	
	
	// CUSTOMER SERVICES-------------------------------------------------------------------------------------CUSTOMER SERVICES\\

		//LINK TO ACCESS THIS SERVICE- //http://localhost:9090/sweetcherry-api/user/login/
		@GetMapping(path = "/login/{username}/{password}")
		public String login(@PathVariable("username") String userName, @PathVariable("password") String password) throws NoSuchUserExists, UserNameAndPasswordDoNotMatchRegularExpression{
			
			methodName ="login(String userName, String password)";
			logger.info(methodName, DESCRIPTION);
			
			return cupcakeservice.login(userName, password); 
		}

		//LINK TO ACCESS THIS SERVICE- //http://localhost:9090/sweetcherry-api/user/userdetails/viewById/
		@GetMapping(path = "/userdetails/viewById/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
		public UserDetails AllUserDetailsById(@PathVariable("userId") int userId) throws InvalidIdException{
			
			methodName ="AllUserDetailsById(int userId)";
			logger.info(methodName, DESCRIPTION);
			
				return cupcakeservice.allUserDetailsById(userId);
		}

		//LINK TO ACCESS THIS SERVICE- // http://localhost:9090/sweetcherry-api/user/logout
		@GetMapping(path = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
		public String logOut(){
			
			methodName ="logout()";
			logger.info(methodName, DESCRIPTION );
			
			return cupcakeservice.logout();
		}

		//LINK TO ACCESS THIS SERVICE- //http://localhost:9090/sweetcherry-api/user/updateUserDetails
		@PutMapping(path = "/updateUserDetails", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
		public UserDetails updateCustomerProfile(@RequestBody UserDetails customer) throws UserNameAndPasswordDoNotMatchRegularExpression {
			
			methodName ="updateCustomerProfile(UserDetails customer)";
			logger.info(methodName, DESCRIPTION);
			
			
			return cupcakeservice.updateCustomerProfile(customer);
		}

		//LINK TO ACCESS THIS SERVICE- // http://localhost:9090/sweetcherry-api/user/registerCustomer
		@PostMapping(path = "/registerCustomer", consumes = MediaType.APPLICATION_JSON_VALUE)
		public UserDetails registerCustomer(@RequestBody UserDetails registerCustomer) throws UserNameAndPasswordDoNotMatchRegularExpression {
			
			methodName ="registerCustomer(UserDetails registerCustomer)";
			logger.info(methodName,DESCRIPTION);
			
			return cupcakeservice.registerCustomer(registerCustomer);
		}

		
		//LINK TO ACCESS THIS SERVICE- //http://localhost:9090/sweetcherry-api/user/UpdatePassword 
		  @GetMapping(path="/UpdatePassword/{userId}/{oldPassword}/{newPassword}")
		  public String modifyPassword(@PathVariable("userId") int userId ,@PathVariable("oldPassword") String oldPassword,@PathVariable("newPassword") String newPassword) throws UserNameAndPasswordDoNotMatchRegularExpression {
			 
			  methodName ="modifyPassword(int userId, String oldPassword, String newPassword)";
				logger.info(methodName, DESCRIPTION);
			  
			  return cupcakeservice.modifyPassword(userId, oldPassword, newPassword);
			  
		  }
		  
		  
		  

	// 2. CUPCAKE  MODULE(USER SIDE)------------------------------------------------------------------------------------------------------------------------

	
	// VIEW LIST OF CUPCAKES AVAILABLE IN SHOP
	// http://localhost:9090/sweetcherry-api/user/viewAllCupcakes
	@GetMapping(path = "viewAllCupcakes")
	public List<CupcakeDetails> viewAllCupcakeDetails() throws NoSuchCupcakeExists {
		methodName = "viewAllCupcakeDetails()";
		logger.info(methodName , DESCRIPTION);
		return cupcakeservice.showCupcakeDetails();
	}

	
	// VIEW SPECIFIC CUPCAKE USING CUPCAKE ID
	// http://localhost:9090/sweetcherry-api/user/viewCupcakeById/
	@GetMapping(path = "viewCupcakeById/{cupcakeId}")
	public CupcakeDetails viewCupcakeDetailsById(@PathVariable("cupcakeId") int cupcakeId) throws NoSuchCupcakeExists {
		methodName = "viewCupcakeDetailsById( int cupcakeId)";
		logger.info(methodName , DESCRIPTION);
		return cupcakeservice.findCupcakeDetailsById(cupcakeId);
	}

	
	// RATE A CUPCAKE USING CUPCAKE ID
	// http://localhost:9090/sweetcherry-api/user/rate/
	@GetMapping(path = "rate/{cupcakeId}/{rating}", produces = "application/json")
	public CupcakeDetails rateMyCupcake(@PathVariable("cupcakeId") int cupcakeId, @PathVariable("rating") int rating)
			throws NoSuchCupcakeExists {
		methodName = " rateMyCupcake(int cupcakeId, int rating)";
		logger.info(methodName , DESCRIPTION);
		return cupcakeservice.modifyCupcakeRating(cupcakeId, rating);
	}

	
	
	
	// ADD A CUPCAKE TO CART
	// http://localhost:9090/sweetcherry-api/user/addToCart
	@PostMapping(path = "addToCart",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Orders addCupcakeToCart(@RequestBody Orders order) throws NoSuchOrderExists {
		methodName = "addCupcakeToCart(Orders order)";
		logger.info(methodName , DESCRIPTION);
		return cupcakeservice.addCupcakeToCart(order);
	}
	
	
	

	// PAY FOR YOUR ORDER
	// http://localhost:9090/sweetcherry-api/user/buyNow
	@PostMapping(path = "buyNow", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public Payment buyCupcake(@RequestBody Payment payment) throws NoSuchOrderExists {
		methodName = "buyCupcake( Payment payment)";
		logger.info(methodName , DESCRIPTION);
		return cupcakeservice.addPaymentDetails(payment);
	}

	
	
	
	
	// 3. ORDER MODULE (USER SIDE)-----------------------------------------------------------------------------------------------------
	
	// http://localhost:9090/sweetcherry-api/user/onlinePayment - URL
		// @PostMapping is used to map the http POST request.
		@PostMapping(path = "/onlinePayment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")

		// onlinePayment( Payment payment) method will return Payment Object
		public Payment onlinePayment(@RequestBody Payment payment) throws PaymentFailedException {
			methodName = "onlinePayment(Payment payment)";
			logger.info(methodName , DESCRIPTION);
			return cupcakeservice.makeOnlinePayment(payment); // makeOnlinePayment(payment) method is called from
																// SweetCherryServiceImpl Class
		}

		// http://localhost:9090/sweetcherry-api/user/placeOrder - URL
		// @GetMapping is used to map the http GET request.
		@GetMapping(path = "/placeOrder/{orderId}")

		// placeOnlineOrder( int orderId) will return the Orders Object
		public Orders placeOnlineOrder(@PathVariable("orderId") int orderId) throws NoSuchOrderExists {
			methodName = "placeOnlineOrder( int orderId)";
			logger.info(methodName , DESCRIPTION); // info lever logger is used
			return cupcakeservice.makeOnlineOrder(orderId); // makeOnlineOrder(orderId) is called from
															// SweetCherryServiceImpl Class
		}

		// http://localhost:9090/sweetcherry-api/user/cancelOrder/1

		// @GetMapping is used to map the http GET request.
		@GetMapping(path = "/cancelOrder/{orderId}")

		// This method will return specific Orders Object
		public Orders cancelOrder(@PathVariable("orderId") int orderid) throws NoSuchOrderExists {
			methodName = "cancelOrder(int orderId)";
			logger.info(methodName ,DESCRIPTION); // info lever logger is used
			return cupcakeservice.cancelOnlineOrder(orderid); // cancelOnlineOrder(orderId) is called from
																// SweetCherryServiceImpl Class
		}

		// http://localhost:9090/sweetcherry-api/user/viewAllOrderDetails/1
		// @GetMapping is used to map the http GET request.
		@GetMapping(path = "/viewAllOrderDetails/{userId}")

		// This method will return the list of Orders Object based on the userId
		public List<Orders> viewAllOrderDetailsByUserId(@PathVariable("userId") int userId) throws NoSuchOrderExists {
			methodName = "viewAllOrderDetailsByUserId( int userId)";
			logger.info(methodName , DESCRIPTION); // info lever logger is used
			return cupcakeservice.showOrderDetailsByUserId(userId); // showOrderDetailsByUserId(userId) is called from
																	// SweetCherryServiceImpl Class
		}

		// http://localhost:9090/sweetcherry-api/user/updateDeliveryAddress
		// @PutMapping is used to map the http PUT request.
		@PutMapping(path = "/updateDeliveryAddress", consumes = "application/json", produces = "application/json")
		// modifyAddress(UserDetails userDetails) will return UserDetails Object
		public UserDetails modifyAddress(@RequestBody UserDetails userDetails) throws NoSuchUserExists {
			methodName = "modifyAddress(UserDetails userDetails)";
			logger.info(methodName , DESCRIPTION); // info level logger is used
			return cupcakeservice.modifyDeliveryAddress(userDetails); // modifyDeliveryAddress(userDetails) is called from
																		// SweetCherryServiceImpl Class
		}

		// http://localhost:9090/sweetcherry-api/user/addDeliveryAddress
		// @PostMapping is used to map the http POST request.
		@PostMapping(path = "/addDeliveryAddress", consumes = "application/json", produces = "application/json")
		// This method will return the Address Object
		public UserDetails addDeliveryAddress(@RequestBody UserDetails userDetails) throws NoSuchUserExists {
			methodName = "addDeliveryAddress(UserDetails userDetails)";
			logger.info(methodName , DESCRIPTION); // info level logger is used
			return cupcakeservice.addDeliveryAddress(userDetails); // addDeliveryAddress(userDetails) is called from
																	// SweetCherryServiceImpl Class
		}

		// http://localhost:9090/sweetcherry-api/user/deleteDeliveryAddress/3
		// @DeleteMapping is used to map the http DELETE request
		@DeleteMapping(path = "/deleteDeliveryAddress/{addressId}")
		// This method will return boolean values depending on the Operation
		public boolean removeCustomerDeliveryAddress(@PathVariable("addressId") int addressId) throws NoSuchAddressExists {
			methodName = "removeCustomerDeliveryAddress( int addressId)";
			logger.info(methodName ,  DESCRIPTION); // info level logger is used
			return cupcakeservice.deleteDeliveryAddress(addressId); // deleteDeliveryAddress(addressId) is called from
																	// SweetCherryServiceImpl Class
		}


}
