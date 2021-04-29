package com.capgemini.service;

import java.util.List;

import com.capgemini.exceptions.NoSuchCupcakeExists;
import com.capgemini.exceptions.NoSuchOrderExists;
import com.capgemini.exceptions.NoSuchUserExists;
import com.capgemini.model.CupcakeDetails;
import com.capgemini.model.Orders;
import com.capgemini.model.Payment;
import com.capgemini.model.UserDetails;

public interface SweetCherryService {

	// CUPCAKE MODULE-------------------------------------------------------------------------------
	
	public CupcakeDetails addCupcakeDetails(CupcakeDetails cupcakedetails) throws NoSuchCupcakeExists;
	public List<CupcakeDetails> showCupcakeDetails() throws NoSuchCupcakeExists;
	public CupcakeDetails findCupcakeDetailsById(int cupcakeId) throws NoSuchCupcakeExists;
	public CupcakeDetails modifyCupcakeRating(int cupcakeId,int rating) throws NoSuchCupcakeExists;
	public Orders addCupcakeToCart(Orders order) throws NoSuchOrderExists;
	public Payment addPaymentDetails(Payment payment) throws NoSuchOrderExists;


	// ORDER MODULE ------------------------------------------------------------------------------------
	
	public Payment makeOnlinePayment(Payment payment);
	public Orders makeOnlineOrder(int orderId) throws NoSuchOrderExists;
	public UserDetails addUserDetails(UserDetails userDetails) throws NoSuchOrderExists;
	//public CupcakeDetails addCupcakeDetails(CupcakeDetails cupCakeDetails);
	
	public Orders cancelOnlineOrder(int orderId) throws NoSuchOrderExists;
	
	public List<Orders> showOrderDetailsByUserId(int userId) throws NoSuchOrderExists;
	public UserDetails addDeliveryAddress(UserDetails userDetails);
	public UserDetails modifyDeliveryAddress(UserDetails userDetails) throws NoSuchUserExists;
	public boolean deleteDeliveryAddress(int addressId);
}
