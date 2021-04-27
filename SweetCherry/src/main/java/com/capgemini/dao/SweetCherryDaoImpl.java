package com.capgemini.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.capgemini.model.CupcakeDetails;



@Repository
public class SweetCherryDaoImpl implements SweetCherryDao{

	@PersistenceContext
	private EntityManager entitymanager;
	
	
	 @Override
	   public CupcakeDetails AddCupcakeDetails(CupcakeDetails cupcakedetails) {
		   entitymanager.persist(cupcakedetails);//Insert 
		   return cupcakedetails;
	   }
	@Override
	public CupcakeDetails readCupcakeDetailsById(int cupcakeId) {
		
		return entitymanager.find(CupcakeDetails.class, cupcakeId);
	}



	@Override
	public List<CupcakeDetails> readAllCupcakeDetails() {
		
		String jpql="From CupcakeDetails";
		TypedQuery<CupcakeDetails> tquery=entitymanager.createQuery(jpql, CupcakeDetails.class);
		return tquery.getResultList();
	}
	@Override
	@Transactional
	public CupcakeDetails ModifyCupcakePrice(CupcakeDetails cupcakedetails) {
		
		return entitymanager.merge(cupcakedetails);
	}
	@Override
	public CupcakeDetails ModifyCupcakeName(CupcakeDetails cupcakedetails) {
		return entitymanager.merge(cupcakedetails);
	}
	@Override
	public boolean removeCupcakeDetails(int cupcakeId) {
		
			boolean result=false;
	    	  CupcakeDetails student=readCupcakeDetailsById(cupcakeId);
	    	  if(student!=null) {
			  entitymanager.remove(student);
			  result=true;
	            }    	  
	    	  return result;
	}



	

}
