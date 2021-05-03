package com.capgemini.service;

import java.util.List;

import com.capgemini.exceptions.NoSuchAddressExists;
import com.capgemini.exceptions.NoSuchCupcakeExists;
import com.capgemini.exceptions.NoSuchOrderExists;
import com.capgemini.exceptions.NoSuchUserExists;
import com.capgemini.exceptions.PaymentFailedException;
import com.capgemini.model.Address;
import com.capgemini.model.CupcakeDetails;
import com.capgemini.model.Orders;
import com.capgemini.model.Payment;
import com.capgemini.model.UserDetails;

// Creating Interface named SweetCherryService with multiple methods
public interface SweetCherryService {

	
	// ORDER MODULE ------------------------------------------------------------------------------------
	
	public Payment makeOnlinePayment(Payment payment) throws PaymentFailedException;
	public Orders makeOnlineOrder(int orderId) throws NoSuchOrderExists;
	
	public Orders cancelOnlineOrder(int orderId) throws NoSuchOrderExists;
	
	public List<Orders> showOrderDetailsByUserId(int userId) throws NoSuchOrderExists;
	public UserDetails addDeliveryAddress(UserDetails userDetails) throws NoSuchUserExists;
	public UserDetails modifyDeliveryAddress(UserDetails userDetails) throws NoSuchUserExists;
	public boolean deleteDeliveryAddress(int addressId) throws NoSuchAddressExists;
	public List<Orders> getAllOrderDetails() throws NoSuchOrderExists;
	public Address getDeliveryAddress(int addressId) throws NoSuchAddressExists;
	public Orders getOrderDetailsById(int orderId) throws NoSuchOrderExists;
	
}