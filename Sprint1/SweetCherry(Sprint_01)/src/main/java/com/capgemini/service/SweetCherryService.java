package com.capgemini.service;

import java.util.List;

import com.capgemini.exceptions.NoSuchCupcakeExists;
import com.capgemini.exceptions.NoSuchOrderExists;
import com.capgemini.exceptions.NoSuchUserExists;
import com.capgemini.model.CupcakeDetails;
import com.capgemini.model.Orders;
import com.capgemini.model.Payment;
import com.capgemini.model.UserDetails;

//Creating Interface named SweetCherryService with multiple methods
public interface SweetCherryService {

	// CUPCAKE ADMIN MODULE-------------------------------------------------------------------------------
	
	public CupcakeDetails addCupcakeDetails(CupcakeDetails cupcakedetails) throws NoSuchCupcakeExists;
	public List<CupcakeDetails> showCupcakeDetails() throws NoSuchCupcakeExists;
	public CupcakeDetails findCupcakeDetailsById(int cupcakeId) throws NoSuchCupcakeExists;
	public CupcakeDetails updateCupcakePriceByCupcakeId(int cupcakeId, double price) throws NoSuchCupcakeExists;
	public CupcakeDetails modifyCupcakeName(int cupcakeId, String cupcakeName) throws NoSuchCupcakeExists;
	public String removeCupcakeDetails(int cupcakeId) throws NoSuchCupcakeExists;
	
	
	/*
	 * //CUPCAKE CUSTOMER MODULE public void modifyCupcakeRating(int cupcakeId,int
	 * rating) throws NoSuchCupcakeExists; public Orders addCupcakeToCart(Orders
	 * order) throws NoSuchOrderExists; public Payment addPaymentDetails(Payment
	 * payment) throws NoSuchOrderExists;
	 * 
	 * 
	 * 
	 * 
	 * // ORDER MODULE
	 * -----------------------------------------------------------------------------
	 * -------
	 * 
	 * public Payment makeOnlinePayment(Payment payment); public Orders
	 * makeOnlineOrder(int orderId) throws NoSuchOrderExists; public UserDetails
	 * addUserDetails(UserDetails userDetails) throws NoSuchOrderExists;
	 * 
	 * 
	 * public Orders cancelOnlineOrder(int orderId) throws NoSuchOrderExists;
	 * 
	 * public List<Orders> showOrderDetailsByUserId(int userId) throws
	 * NoSuchOrderExists; public UserDetails addDeliveryAddress(UserDetails
	 * userDetails); public UserDetails modifyDeliveryAddress(UserDetails
	 * userDetails) throws NoSuchUserExists; public boolean
	 * deleteDeliveryAddress(int addressId);
	 */
}
