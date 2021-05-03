package com.capgemini.dao;

import java.util.List;

import com.capgemini.model.Address;
import com.capgemini.model.Orders;
import com.capgemini.model.Payment;
import com.capgemini.model.UserDetails;

public interface SweetCherryDao {

	public Payment payOnline(Payment payment);
	public Orders placeOrder(int orderId);
	public UserDetails createUserDetails(UserDetails userDetail);
	//public CupcakeDetails createCupcakedetails(CupcakeDetails cupcakeDetails);
	public Orders cancelOrder(int orderId);
	public  List<Orders> readOrderDetailsByUserId(int userId);
	public UserDetails createDeliveryAddress(UserDetails userDetails);
	public UserDetails updateDeliveryAddress(UserDetails userDetails);
	public boolean removeDeliveryAddress(int addressId);
}
