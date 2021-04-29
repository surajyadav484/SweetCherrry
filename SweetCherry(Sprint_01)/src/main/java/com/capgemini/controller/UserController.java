package com.capgemini.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.capgemini.exceptions.NoSuchCupcakeExists;
import com.capgemini.exceptions.NoSuchOrderExists;
import com.capgemini.exceptions.NoSuchUserExists;
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
	
	// CUPCAKE MODULE--------------------------------------------------------------------------------------------
	
	
	
	// http://localhost:9090/sweetcherry-api/user/viewAllCupcakes
	@GetMapping(path = "viewAllCupcakes")
	public List<CupcakeDetails> viewAllCupcakeDetails() throws NoSuchCupcakeExists {
		
		return cupcakeservice.showCupcakeDetails();
	}
	
	
	// http://localhost:9090/sweetcherry-api/user/viewCupcakeById/
	@GetMapping(path = "viewCupcakeById/{cupcakeId}")
	public CupcakeDetails viewCupcakeDetailsById(@PathVariable("cupcakeId") int cupcakeId) throws NoSuchCupcakeExists {
		System.out.println(cupcakeId);
		return cupcakeservice.findCupcakeDetailsById(cupcakeId);
	}
	
		
	// http://localhost:9090/sweetcherry-api/user/rate/
	@GetMapping(path = "rate/{cupcakeId}/{rating}", produces = "application/json")
	public CupcakeDetails rateMyCupcake(@PathVariable("cupcakeId") int cupcakeId, @PathVariable("rating") int rating) throws NoSuchCupcakeExists {
		return cupcakeservice.modifyCupcakeRating(cupcakeId, rating);
	}
	
	// http://localhost:9090/sweetcherry-api/user/addToCart
	@PostMapping(path = "addToCart", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public Orders addCupcakeToCart(@RequestBody Orders order) throws NoSuchOrderExists {
		return cupcakeservice.addCupcakeToCart(order);
	}
	
	
	// http://localhost:9090/sweetcherry-api/user/buyNow
	@PostMapping(path = "buyNow", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public Payment buyCupcake(@RequestBody Payment payment) throws NoSuchOrderExists {
		return cupcakeservice.addPaymentDetails(payment);
	}
	
	
	
	
	
	
	// ORDER MODULE------------------------------------------------------------------------------------------------
	
	// http://localhost:9090/sweetcherry-api/user/onlinePayment
	@PostMapping(path = "/onlinePayment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public Payment onlinePayment(@RequestBody Payment payment) {
		return cupcakeservice.makeOnlinePayment(payment);
	}

	// http://localhost:9090/sweetcherry-api/user/placeOrder
	@GetMapping(path = "/placeOrder/{orderId}")
	public Orders placeOnlineOrder(@PathVariable("orderId") int orderId) throws NoSuchOrderExists {
		return cupcakeservice.makeOnlineOrder(orderId);
	}

	// http://localhost:9090/sweetcherry-api/user/userDetails

	@PostMapping(path = "/userDetails", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public UserDetails addUser(UserDetails userDetails) throws NoSuchOrderExists {
		return cupcakeservice.addUserDetails(userDetails);
	}

	// http://localhost:9090/sweetcherry-api/user/cancelOrder/1
	@GetMapping(path = "/cancelOrder/{orderId}")
	public Orders cancelOrder(@PathVariable("orderId") int orderid) throws NoSuchOrderExists {
		return cupcakeservice.cancelOnlineOrder(orderid);
	}

	// http://localhost:9090/sweetcherry-api/user/viewAllOrderDetails/1
	@GetMapping(path = "/viewAllOrderDetails/{userId}")
	public List<Orders> viewAllOrderDetailsByUserId(@PathVariable int userId) throws NoSuchOrderExists {
		return cupcakeservice.showOrderDetailsByUserId(userId);
	}

	// http://localhost:9090/sweetcherry-api/user/updateDeliveryAddress
	@PutMapping(path = "/updateDeliveryAddress", consumes = "application/json", produces = "application/json")
	public UserDetails modifyAddress(@RequestBody UserDetails userDetails) throws NoSuchUserExists {
		return cupcakeservice.modifyDeliveryAddress(userDetails);
	}

	// http://localhost:9090/sweetcherry-api/user/addDeliveryAddress
	@PostMapping(path = "/addDeliveryAddress", consumes = "application/json", produces = "application/json")
	public UserDetails addDeliveryAddress(@RequestBody UserDetails userDetails) {
		return cupcakeservice.addDeliveryAddress(userDetails);
	}

	// http://localhost:9090/sweetcherry-api/user/deleteDeliveryAddress/3

	@DeleteMapping(path = "/deleteDeliveryAddress/{addressId}")
	public boolean removeCustomerDeliveryAddress(@PathVariable("addressId") int addressId) {
		return cupcakeservice.deleteDeliveryAddress(addressId);
	}

	/*
	 * @ExceptionHandler(NoSuchCupcakeExists.class)
	 * 
	 * @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason =
	 * "Cupcake Deails Not Found") public void handleException() {
	 * 
	 * }
	 */
	 
	
	
	
	

}
