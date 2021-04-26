package com.capgemini.dao;

import java.util.List;

import com.capgemini.model.CupcakeDetails;
import com.capgemini.model.Orders;

public interface SweetCherryDao {

	public CupcakeDetails readCakeDetailsById(int cupcakeId);
	public List<Orders> readAllOrders();
	public Orders readOrderById(int orderId);
	
	public List<CupcakeDetails> readAllCupcakeDetails(); 
	
}
