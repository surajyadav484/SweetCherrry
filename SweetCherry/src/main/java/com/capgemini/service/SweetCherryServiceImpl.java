package com.capgemini.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.dao.SweetCherryDao;
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

	/*
	 * @Override
	 * 
	 * @Transactional public Orders makeOnlineOrder(Orders order, int cupCakeId) {
	 * 
	 * return dao.placeOrder(order, cupCakeId); }
	 */
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
	public Orders cancelonlineOrder(int orderId) {
		return dao.cancelOrder(orderId);
	}
	@Override
	public List<Orders> getAllOrders() {
		return dao.readAllOrders();
	}
	@Override
	@Transactional
	public UserDetails modifyUserAddress(UserDetails userDetail) {
		return dao.updateAddress(userDetail);
	}	

}
