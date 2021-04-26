package com.capgemini.service;

import java.util.List;

import com.capgemini.exceptions.OrderException;
import com.capgemini.model.CupcakeDetails;
import com.capgemini.model.Orders;

public interface SweetCherryService {

	public CupcakeDetails findCupcakeById(int cupcakeId);
	public List<Orders> findAllOrders() throws OrderException;
	public Orders findOrderById(int orderId)  throws OrderException;
	
	public List<CupcakeDetails> findAllCupcakeDetails();
}
