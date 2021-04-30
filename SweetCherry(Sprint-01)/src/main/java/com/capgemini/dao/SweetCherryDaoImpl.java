package com.capgemini.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import com.capgemini.logger.LoggerController;
import com.capgemini.model.Address;
import com.capgemini.model.CupcakeDetails;
import com.capgemini.model.Orders;
import com.capgemini.model.Payment;
import com.capgemini.model.UserDetails;

@Repository
public class SweetCherryDaoImpl implements SweetCherryDao {

	@PersistenceContext
	private EntityManager entityManager;

	private Logger logger = LoggerController.getLogger(SweetCherryDaoImpl.class);
	String methodName = null;

	// CUPCAKE MODULE----------------------------------------------------------------

	@Override
	public CupcakeDetails readCupcakeDetailsById(int cupcakeId) {
		methodName = "readCupcakeDetailsById(int cupcakeId)";
		logger.info(methodName + " method is called from SweetCherryDao Layer");
		return entityManager.find(CupcakeDetails.class, cupcakeId);
	}

	@Override
	public CupcakeDetails createCupcakeDetails(CupcakeDetails cupcakedetails) {
		methodName = "createCupcakeDetailsById(int cupcakeId)";
		logger.info(methodName + " method is called from SweetCherryDao Layer");
		entityManager.persist(cupcakedetails);
		return cupcakedetails;
	}

	@Override
	public List<CupcakeDetails> readAllCupcakeDetails() {
		methodName = "readAllCupcakeDetails()";
		logger.info(methodName + " method is called from SweetCherryDao Layer");
		TypedQuery<CupcakeDetails> query = entityManager.createNamedQuery("getCupcakeDetails", CupcakeDetails.class);
		return query.getResultList();
	}

	@Override
	public CupcakeDetails updateCupcakeRating(int cupcakeId, int rating) {
		CupcakeDetails cupcake = readCupcakeDetailsById(cupcakeId);
		cupcake.setRating(rating);
		return cupcake;
	}

	@Override
	public Orders addCupcakeToCart(Orders order) {
		methodName = "addCupcakeToCart(Orders order)";
		logger.info(methodName + " method is called from SweetCherryDao Layer");
		entityManager.persist(order);
		return order;
	}

	@Override
	public Payment createPaymentDetails(Payment payment) {
		methodName = "createPaymentDetails(Payment payment)";
		logger.info(methodName + " method is called from SweetCherryDao Layer");
		entityManager.persist(payment);
		return payment;
	}

	// ORDER MODULE-------------------------------------------------------------------------
	
	
	@Override
	public Payment payOnline(Payment payment) {
		entityManager.persist(payment);
		return payment;
	}

	@Override
	public Orders placeOrder(int ordereId) {
		Orders order = entityManager.find(Orders.class, ordereId);
		order.setOrderStatus("ordered");
		return entityManager.merge(order);
	}

	@Override
	public UserDetails createUserDetails(UserDetails userDetail) {
		entityManager.persist(userDetail);
		return userDetail;
	}

	/*
	 * @Override public CupcakeDetails createCupcakedetails(CupcakeDetails
	 * cupcakeDetails) { em.persist(cupcakeDetails); return cupcakeDetails; }
	 */

	@Override
	public Orders cancelOrder(int orderId) {
		Orders order = entityManager.find(Orders.class, orderId);
		order.setOrderStatus("cancelled");
		return order;
	}

	@Override
	public List<Orders> readOrderDetailsByUserId(int userId) {

		TypedQuery<Orders> orderQuery = entityManager.createNamedQuery("showOrderDetailByUserId", Orders.class);
		orderQuery.setParameter("userId", userId);
		return orderQuery.getResultList();
	}

	@Override
	public UserDetails updateDeliveryAddress(UserDetails userDetails) {
		return entityManager.merge(userDetails);

	}

	@Override
	public boolean removeDeliveryAddress(int addressId) {
		boolean result = false;
		Address address = entityManager.find(Address.class, addressId);
		if (address != null)
			entityManager.remove(address);
		result = true;
		return result;
	}

	@Override
	public UserDetails createDeliveryAddress(UserDetails userDetails) {
		entityManager.persist(userDetails);
		return userDetails;
	}


}
