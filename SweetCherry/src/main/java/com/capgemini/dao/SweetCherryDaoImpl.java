package com.capgemini.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.capgemini.model.Address;
import com.capgemini.model.CupcakeDetails;
import com.capgemini.model.Orders;
import com.capgemini.model.Payment;
import com.capgemini.model.UserDetails;

@Repository
public class SweetCherryDaoImpl implements SweetCherryDao{

	@PersistenceContext
	private EntityManager em;
	@Override
	public Payment payOnline(Payment payment) {
		 em.persist(payment);
		 return payment;
	}

	
	  @Override
	  public Orders placeOrder(int ordereId) { 
		Orders order =  em.find(Orders.class, ordereId);
		order.setOrderStatus("ordered");
		return em.merge(order);
		}
	 
	@Override
	public UserDetails createUserDetails(UserDetails userDetail) {
		em.persist(userDetail);
		return userDetail;
	}

	
	/*
	 * @Override public CupcakeDetails createCupcakedetails(CupcakeDetails
	 * cupcakeDetails) { em.persist(cupcakeDetails); return cupcakeDetails; }
	 */
	 
	
	
	@Override
	public Orders cancelOrder(int orderId) {
		Orders order = em.find(Orders.class, orderId);
		order.setOrderStatus("cancelled");
		return order;
	}
	@Override
	public List<Orders> readOrderDetailsByUserId(int userId) {
		
		TypedQuery<Orders> orderQuery = em.createNamedQuery("showOrderDetailByUserId",Orders.class);
		orderQuery.setParameter("userId",userId);
		return orderQuery.getResultList();
	}
	@Override
	public Address updateDeliveryAddress(Address address) {
		return em.merge(address);
		
	}
	@Override
	public boolean removeDeliveryAddress(int addressId) {
		boolean result = false;
		Address address = em.find(Address.class, addressId);
		if(address != null)
			 em.remove(address);
			 result = true;
		return result;
	}


	@Override
	public Address createDeliveryAddress(Address address) {
		em.persist(address);
		return address;
	}

	

}
