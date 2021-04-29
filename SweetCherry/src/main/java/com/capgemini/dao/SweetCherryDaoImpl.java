package com.capgemini.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import com.capgemini.logger.LoggerController;
import com.capgemini.model.CupcakeDetails;

@Repository
public class SweetCherryDaoImpl implements SweetCherryDao {

	@PersistenceContext
	private EntityManager entitymanager;

	private Logger logger = LoggerController.getLogger(SweetCherryDaoImpl.class);
	String methodName = null;

	@Override
	public CupcakeDetails addCupcakeDetails(CupcakeDetails cupcakedetails) {

		entitymanager.persist(cupcakedetails);// Insert
		return cupcakedetails;
	}

	@Override
	public CupcakeDetails readCupcakeDetailsById(int cupcakeId) {
		methodName = "readCupcakeDetailsById(int cupcakeId)";
		logger.info(methodName + " method is called from SweetCherryDao Layer");

		return entitymanager.find(CupcakeDetails.class, cupcakeId);
	}

	@Override
	public List<CupcakeDetails> readAllCupcakeDetails() {
        
		methodName = "readAllCupcakeDetails(CupcakeDetails cupcakedetails)";
		logger.info(methodName + " method is called from SweetCherryDao Layer");
		
		String jpql = "From CupcakeDetails";
		TypedQuery<CupcakeDetails> tquery = entitymanager.createQuery(jpql, CupcakeDetails.class);
		return tquery.getResultList();
	}

	@Override
	@Transactional
	public CupcakeDetails ModifyCupcakePrice(CupcakeDetails cupcakedetails) {
		methodName = "ModifyCupcakePrice(CupcakeDetails cupcakedetails)";
		logger.info(methodName + " method is called from SweetCherryDao Layer");

		return entitymanager.merge(cupcakedetails);
	}

	@Override
	public CupcakeDetails ModifyCupcakeName(CupcakeDetails cupcakedetails) {
		methodName = "ModifyCupcakeName(CupcakeDetails cupcakedetails)";
		logger.info(methodName + " method is called from SweetCherryDao Layer");
		
		return entitymanager.merge(cupcakedetails);
	}

	@Override
	public boolean removeCupcakeDetails(int cupcakeId) {
		methodName = "removeCupcakeDetails(int cupcakeId)";
		logger.info(methodName + " method is called from SweetCherryDao Layer");

		boolean result = false;
		CupcakeDetails student = readCupcakeDetailsById(cupcakeId);
		if (student != null) {
			entitymanager.remove(student);
			result = true;
		}
		return result;
	}

}
