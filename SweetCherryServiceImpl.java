package com.capgemini.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.capgemini.dao.SweetCherryDao;
import com.capgemini.exceptions.OrderException;
import com.capgemini.model.CupcakeDetails;
import com.capgemini.model.Orders;

@Service
@Scope(scopeName = "singleton")
public class SweetCherryServiceImpl implements SweetCherryService {

	@Autowired
	private SweetCherryDao dao;
	
	@Override
	public List<Orders> findAllOrders() throws OrderException {
		List<Orders> ordersList = dao.readAllOrders();
		if(ordersList!=null) {
			return ordersList;
		}
		else
			throw new OrderException("No Orders Found");
	}

	@Override
	public CupcakeDetails findCupcakeById(int cupcakeId) {
		return dao.readCakeDetailsById(cupcakeId);
	}

	@Override
	public Orders findOrderById(int orderId) throws OrderException {
		Orders order1 = dao.readOrderById(orderId);
		if(order1 != null) {
			return order1;
		}
		else
			throw new OrderException("Order with order id = " + orderId + " not found");
	}

	
	@Override
	public List<CupcakeDetails> findAllCupcakeDetails() {
		return dao.readAllCupcakeDetails();
	}

	

}
