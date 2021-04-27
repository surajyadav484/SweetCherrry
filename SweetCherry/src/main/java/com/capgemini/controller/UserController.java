package com.capgemini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.model.Orders;
import com.capgemini.model.Payment;
import com.capgemini.model.UserDetails;
import com.capgemini.service.SweetCherryService;

@RestController
@RequestMapping(path = "user")
public class UserController {

	@Autowired
	private SweetCherryService service;
	
	// http://localhost:9090/sweetcherry-api/user/onlinePayment
	@PostMapping(path="/onlinePayment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public Payment onlinePayment(@RequestBody Payment payment) {
		return service.makeOnlinePayment(payment);
	}
	
	
	  // http://localhost:9090/sweetcherry-api/user/placeOrder 
	  @GetMapping(path="/placeOrder/{orderId}") 
	  public Orders placeOnlineOrder(@PathVariable("orderId") int orderId) {
		  return service.makeOnlineOrder(orderId);
	  }
	 
	
	
	  // http://localhost:9090/sweetcherry-api/user/userDetails
	  
	  @PostMapping(path="/userDetails", consumes =
	  MediaType.APPLICATION_JSON_VALUE, produces = "application/json") public
	  UserDetails addUser(UserDetails userDetails) { return
	  service.addUserDetails(userDetails); }
	  
	  // http://localhost:9090/sweetcherry-api/user/cancelOrder/1
	  @GetMapping(path="/cancelOrder/{orderId}")
	  public Orders cancelOrder(@PathVariable("orderId") int orderid) {
		 return service.cancelonlineOrder(orderid);
	  }
	  
	  // http://localhost:9090/sweetcherry-api/user/viewAllOrderDetails
	  @GetMapping(path="/viewAllOrderDetails")
	  public List<Orders> viewAllOrderDetails(){
		  return service.getAllOrders();
	  }
	  
	  // http://localhost:9090/sweetcherry-api/user/updateUserAddress
	  @PutMapping(path="/updateUserAddress",consumes = "application/json",produces = "application/json")
	  public UserDetails modifyAddress(@RequestBody UserDetails userDetail) {
		  return service.modifyUserAddress(userDetail);
	  }
	 
}
