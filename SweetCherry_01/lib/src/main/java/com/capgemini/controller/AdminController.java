package com.capgemini.controller;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.exceptions.NoSuchAddressExists;
import com.capgemini.exceptions.NoSuchOrderExists;
import com.capgemini.logger.LoggerController;
import com.capgemini.model.Address;
import com.capgemini.model.Orders;
import com.capgemini.service.SweetCherryService;
import com.capgemini.service.SweetCherryServiceImpl;

//@RestController is combination of two annotations
// @Controller And @ResponseBody. Therefore methods return pure text when called from browser or postman.
@RestController
@RequestMapping(path = "admin") // @RequestMapping is used to map '/' to index() method.

//This is RestController class. Classes marked with @ResetController states that it is ready for use by Spring mvc to handle web requests
public class AdminController {

	@Autowired // it is used for object injection
	private SweetCherryService service;

	/*
	 * Logger class provides the methods for logging. logger object is used to log
	 * messages for specific system or application component
	 */
	private Logger logger = LoggerController.getLogger(AdminController.class);
	String methodName = null; // initialization of String variable
	static final  String  DESCRIPTION = " controller is called from AdminController";

	// http://localhost:9090/sweetcherry-api/admin/viewOrderDetails
	@GetMapping(path = "/viewOrderDetails", produces = "application/json") // @GetMapping is used for mapping http GET
																			// requests
	public List<Orders> showAllOrderDetails() throws NoSuchOrderExists { // showAllOrderDetails() method will return the
																			// list of order details
		methodName = "showAllOrderDetails()";
		
		logger.info(methodName, DESCRIPTION); // use of info level logger
		return service.getAllOrderDetails(); // getAllOrderDetails() method is called from SweetCherryServiceImpl Class
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
		return service.getDeliveryAddress(addressId); // getDeliveryAddress(addressId) method is called from
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
		return service.getOrderDetailsById(orderId); // getOrderDetailsById(orderId) is called from
														// SweetCherryServiceImpl Class

	}

}