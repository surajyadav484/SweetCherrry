package com.capgemini.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.dao.SweetCherryDao;
import com.capgemini.model.Address;
import com.capgemini.model.CupcakeDetails;
import com.capgemini.model.Orders;
import com.capgemini.model.Payment;
import com.capgemini.model.UserDetails;

@Service
public class SweetCherryServiceImpl implements SweetCherryService {

	@Autowired
	SweetCherryDao dao;
	
	@Override
	@Transactional
	public Payment makeOnlinePayment(Payment payment) {
		return dao.payOnline(payment);
		
	}

	
	  @Override
	  @Transactional 
	  public Orders makeOnlineOrder(int orderId) { 
		  return dao.placeOrder(orderId); 
	}
	 
	@Override
	@Transactional
	public UserDetails addUserDetails(UserDetails userDetails) {
		dao.createUserDetails(userDetails);
		return userDetails;
	}

	
	/*
	 * @Override public CupcakeDetails addCupcakeDetails(CupcakeDetails
	 * cupCakeDetails) {
	 * 
	 * }
	 */
	 
	
	
	@Override
	public Orders cancelOnlineOrder(int orderId) {
		return dao.cancelOrder(orderId);
	}
	@Override
	public List<Orders> showOrderDetailsByUserId(int userId) {
		return dao.readOrderDetailsByUserId(userId);
	}
	@Override
	@Transactional
	public UserDetails modifyDeliveryAddress(UserDetails address) {
		return dao.updateDeliveryAddress(address);
	}


	@Override
	@Transactional
	public UserDetails addDeliveryAddress(UserDetails userDetails) {
		return dao.createDeliveryAddress(userDetails);
	}


	@Override
	@Transactional
	public boolean deleteDeliveryAddress(int addressId) {
		return dao.removeDeliveryAddress(addressId);
	}	

}
