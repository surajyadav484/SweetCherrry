package com.capgemini.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.capgemini.model.CupcakeDetails;
import com.capgemini.model.Orders;

@Repository
@Scope(scopeName = "singleton")
public class SweetCherryDaoImpl implements SweetCherryDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Orders> readAllOrders() {
		String jpql ="FROM Orders";
		TypedQuery<Orders> query = entityManager.createQuery(jpql,Orders.class);
		List<Orders> ordersList = query.getResultList();
		return ordersList;
	}

	@Override
	public CupcakeDetails readCakeDetailsById(int cupcakeId) {
		return entityManager.find(CupcakeDetails.class, cupcakeId);
	}

	@Override
	public Orders readOrderById(int orderId) {
		TypedQuery<Orders> query = entityManager.createNamedQuery("showOrderById", Orders.class);
		query.setParameter("id",orderId);
		return query.getSingleResult();
	}

	@Override
	public List<CupcakeDetails> readAllCupcakeDetails() {
		TypedQuery<CupcakeDetails> query = entityManager.createNamedQuery("getCupcakeDetails", CupcakeDetails.class);
		return query.getResultList();
	}

	

}
