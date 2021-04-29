package com.capgemini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.exceptions.NoSuchAddressExists;
import com.capgemini.exceptions.NoSuchOrderExists;
import com.capgemini.model.Address;
import com.capgemini.model.Orders;
import com.capgemini.service.SweetCherryService;

@RestController
@RequestMapping(path = "admin")
public class AdminController {

	@Autowired
	private SweetCherryService service;
	
	// http://localhost:9090/sweetcherry-api/admin/viewOrderDetails
	@GetMapping(path="/viewOrderDetails",produces = "application/json")
	public List<Orders> showAllOrderDetails() throws NoSuchOrderExists{
		return service.geAllOrderdetails();
	}
	
	// http://localhost:9090/sweetcherry-api/admin/viewDeliveryAddress/1
	@GetMapping(path="/viewDeliveryAddress/{addressId}",produces = "application/json")
	public Address showDeliveryAddress(@PathVariable("addressId") int addressId) throws NoSuchAddressExists {
		return service.getDeliveryAddress(addressId);
	}
	
	
	
	
}