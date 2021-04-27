package com.capgemini.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

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

	/*
	 * @Override public Orders placeOrder(int ordereId) { Orders order =
	 * em.find(Orders.class, ordereId); return null; }
	 */
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
	public List<Orders> readAllOrders() {
		
		String jpql = "FROM Orders";
		TypedQuery<Orders> orderQuery = em.createQuery(jpql,Orders.class);
		
		return orderQuery.getResultList();
	}
	@Override
	public UserDetails updateAddress(UserDetails userDetail) {
		return em.merge(userDetail);
		
	}
	@Override
	public UserDetails deleteDeliveryAddress(UserDetails userDetail) {
		return null;
	}

	

}
