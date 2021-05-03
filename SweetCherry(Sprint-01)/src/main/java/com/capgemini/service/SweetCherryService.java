package com.capgemini.service;

import java.util.List;

import com.capgemini.exceptions.InvalidIdException;
import com.capgemini.exceptions.NoSuchAddressExists;
import com.capgemini.exceptions.NoSuchCupcakeExists;
import com.capgemini.exceptions.NoSuchOrderExists;
import com.capgemini.exceptions.NoSuchUserExists;
import com.capgemini.exceptions.PaymentFailedException;
import com.capgemini.exceptions.UserNameAndPasswordDoNotMatchRegularExpression;
import com.capgemini.model.Address;
import com.capgemini.model.CupcakeCategory;
import com.capgemini.model.CupcakeDetails;
import com.capgemini.model.Orders;
import com.capgemini.model.Payment;
import com.capgemini.model.UserDetails;

public interface SweetCherryService {

	
	// LOGIN MODULE -------------------------------------------------------------------------------
	
	//Accessible to Both Administrator and Customer
	public String login(String userName, String password) throws NoSuchUserExists, UserNameAndPasswordDoNotMatchRegularExpression ; 
	public String logout() ;
		
		
	//Administrator services
	public List<UserDetails> allDetailsOfAdminAndUser() throws NoSuchUserExists; 
		

	//Customer Services
	public UserDetails allUserDetailsById(int userId) throws InvalidIdException;
	public UserDetails updateCustomerProfile(UserDetails customer) throws UserNameAndPasswordDoNotMatchRegularExpression;
	public UserDetails registerCustomer(UserDetails registerCustomer) throws UserNameAndPasswordDoNotMatchRegularExpression;
	public String modifyPassword(int userId, String oldPassword, String newPassword) throws UserNameAndPasswordDoNotMatchRegularExpression;
	
	
	
	// CUPCAKE MODULE-------------------------------------------------------------------------------
	
	public CupcakeDetails addCupcakeDetails(CupcakeDetails cupcakedetails) throws NoSuchCupcakeExists;
	public List<CupcakeDetails> showCupcakeDetails() throws NoSuchCupcakeExists;
	public CupcakeDetails findCupcakeDetailsById(int cupcakeId) throws NoSuchCupcakeExists;
	public CupcakeDetails modifyCupcakeRating(int cupcakeId,int rating) throws NoSuchCupcakeExists;
	public Orders addCupcakeToCart(Orders order) throws NoSuchOrderExists;
	public Payment addPaymentDetails(Payment payment) throws NoSuchOrderExists;
	public CupcakeCategory addCupcakeCategory(CupcakeCategory cupcakeCategory);
	
	//
	public CupcakeDetails updateCupcakePriceByCupcakeId(int cupcakeId, double price) throws NoSuchCupcakeExists;
	public CupcakeDetails modifyCupcakeName(int cupcakeId, String cupcakeName) throws NoSuchCupcakeExists;
	public String removeCupcakeDetails(int cupcakeId) throws NoSuchCupcakeExists;
	
	//
	

	// ORDER MODULE ------------------------------------------------------------------------------------
	
		public Payment makeOnlinePayment(Payment payment) throws PaymentFailedException;
		
		public Orders cancelOnlineOrder(int orderId) throws NoSuchOrderExists;
		
		public List<Orders> showOrderDetailsByUserId(int userId) throws NoSuchOrderExists;
		public UserDetails addDeliveryAddress(UserDetails userDetails) throws NoSuchUserExists;
		public UserDetails modifyDeliveryAddress(UserDetails userDetails) throws NoSuchUserExists;
		public boolean deleteDeliveryAddress(int addressId) throws NoSuchAddressExists;
		public List<Orders> getAllOrderDetails() throws NoSuchOrderExists;
		public Address getDeliveryAddress(int addressId) throws NoSuchAddressExists;
	// ORDER MODULE ------------------------------------------------------------------------------------
	public Orders makeOnlineOrder(int orderId) throws NoSuchOrderExists;
	Orders getOrderDetailsById(int orderId) throws NoSuchOrderExists;
	
	
}
