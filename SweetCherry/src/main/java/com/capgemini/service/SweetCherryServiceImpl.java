package com.capgemini.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.dao.SweetCherryDao;
import com.capgemini.exceptions.HandleExceptions;
import com.capgemini.exceptions.NoSuchCupcakeExists;
import com.capgemini.model.CupcakeDetails;
import com.capgemini.model.Orders;

@Service
public class SweetCherryServiceImpl implements SweetCherryService {
	@Autowired
	private SweetCherryDao dao;
	@Override
	public CupcakeDetails findCupcakeById(int cupcakeId) throws NoSuchCupcakeExists {
	
		CupcakeDetails cupcakedetails= dao.readCupcakeDetailsById(cupcakeId);
		
		if(cupcakedetails!=null)
			return cupcakedetails;
		else
			 throw new NoSuchCupcakeExists("Cupcake Details with id"+cupcakeId+ " not found");
	}
	@Override
	@Transactional
	public CupcakeDetails AddCupcakeDetails(CupcakeDetails cupcakedetails) {
		
		return dao.AddCupcakeDetails(cupcakedetails);
	}
	@Override
	public List<CupcakeDetails> findAllCupcake() {
		return dao.readAllCupcakeDetails();
		
	}
	@Override
	
	@Transactional
	public CupcakeDetails modifyCupcakePrice(CupcakeDetails cupcakedetails) {
		return dao.ModifyCupcakePrice(cupcakedetails);
	}
	@Override
	public CupcakeDetails modifyCupcakeName(CupcakeDetails cupcakedetails) {
		return dao.ModifyCupcakeName(cupcakedetails);
	}
	@Override
	@Transactional
	public boolean removeCupcakeDetails(int cupcakeId) throws NoSuchCupcakeExists {
		
		
			boolean result= dao.removeCupcakeDetails(cupcakeId);
			if(result)
				return result;
			else 
				throw  new NoSuchCupcakeExists("Cupcake with id" +cupcakeId+"not found");
			
		}


	

}
