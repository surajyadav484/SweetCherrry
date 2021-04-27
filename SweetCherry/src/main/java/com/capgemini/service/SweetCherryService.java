package com.capgemini.service;

import java.util.List;

import com.capgemini.model.CupcakeDetails;
import com.capgemini.model.Orders;
import com.capgemini.model.Payment;
import com.capgemini.model.UserDetails;

public interface SweetCherryService {

	public Payment makeOnlinePayment(Payment payment);
//	public Orders makeOnlineOrder(Orders order, int cupCakeId);
	public UserDetails addUserDetails(UserDetails userDetails);
	//public CupcakeDetails addCupcakeDetails(CupcakeDetails cupCakeDetails);
	
	public Orders cancelonlineOrder(int orderId);
	
	public List<Orders> getAllOrders();
	public UserDetails modifyUserAddress(UserDetails userDetail);
}
