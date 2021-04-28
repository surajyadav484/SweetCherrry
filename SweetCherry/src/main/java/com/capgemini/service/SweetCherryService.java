package com.capgemini.service;

import java.util.List;

import com.capgemini.model.Address;
import com.capgemini.model.CupcakeDetails;
import com.capgemini.model.Orders;
import com.capgemini.model.Payment;
import com.capgemini.model.UserDetails;

public interface SweetCherryService {

	public Payment makeOnlinePayment(Payment payment);
	public Orders makeOnlineOrder(int orderId);
	public UserDetails addUserDetails(UserDetails userDetails);
	//public CupcakeDetails addCupcakeDetails(CupcakeDetails cupCakeDetails);
	
	public Orders cancelOnlineOrder(int orderId);
	
	public List<Orders> showOrderDetailsByUserId(int userId);
	public Address addDeliveryAddress(Address address);
	public Address modifyDeliveryAddress(Address address);
	public boolean deleteDeliveryAddress(int addressId);
}
