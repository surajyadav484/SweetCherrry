package com.capgemini.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.dao.SweetCherryDao;
import com.capgemini.dao.SweetCherryDaoImpl;

import com.capgemini.exceptions.NoSuchCupcakeExists;
import com.capgemini.logger.LoggerController;
import com.capgemini.model.CupcakeDetails;
import com.capgemini.model.Orders;

@Service
public class SweetCherryServiceImpl implements SweetCherryService {

	private Logger logger = LoggerController.getLogger(SweetCherryServiceImpl.class);
	String methodName = null;
	@Autowired

	private SweetCherryDao dao;

	@Override
	public CupcakeDetails findCupcakeById(int cupcakeId) throws NoSuchCupcakeExists {

		methodName = "findCupcakeById(int cupcakeId)";
		logger.info(methodName + " method is called from SweetCherryService Layer");
		try {
			CupcakeDetails cupcakedetails = dao.readCupcakeDetailsById(cupcakeId);
			if (cupcakedetails != null )
				return cupcakedetails;
			else
				throw new NoSuchElementException();
		}

		catch (NoSuchElementException e) {
			throw new NoSuchCupcakeExists("Cupcake details with " + cupcakeId + "is not found");
		}
	}

	@Override
	@Transactional
	public CupcakeDetails addCupcakeDetails(CupcakeDetails cupcakedetails) throws NoSuchCupcakeExists {

		methodName = "addCupcakeDetails(CupcakeDetails cupcakedetails)";
		logger.info(methodName + " method is called from SweetCherryService Layer");

		try {
			CupcakeDetails cupcakedetail = dao.addCupcakeDetails(cupcakedetails);
			if (cupcakedetail != null)
				return cupcakedetail;
			else
				throw new NoSuchElementException();
		}

		catch (NoSuchElementException e) {
			throw new NoSuchCupcakeExists("Cupcake details is not added.");
		}
	}

	@Override
	public List<CupcakeDetails> findAllCupcake() throws NoSuchCupcakeExists {
		
		methodName = "findAllCupcake(CupcakeDetails cupcakedetails)";
		logger.info(methodName + " method is called from SweetCherryService Layer");
				
		try {
			List<CupcakeDetails> cupcakedetails = dao.readAllCupcakeDetails();
			if (cupcakedetails != null)
				return cupcakedetails;
			else
				throw new NoSuchElementException("Cupcake details is not found.");
		}

		catch (NoSuchElementException e) {
			throw new NoSuchCupcakeExists("Cupcake details is not found.");
		}

	}

	@Override

	@Transactional
	public CupcakeDetails modifyCupcakePrice(CupcakeDetails cupcakedetails) throws NoSuchCupcakeExists {
		
		methodName = "modifyCupcakePrice(CupcakeDetails cupcakedetails)";
		logger.info(methodName + " method is called from SweetCherryService Layer");
		try {
		CupcakeDetails cupcakedetail1= dao.ModifyCupcakePrice(cupcakedetails);
		if (cupcakedetail1 != null)
			return cupcakedetail1;
		else
			throw new NoSuchElementException("Cupcake Price is not modified.");
	}

	catch (NoSuchElementException e) {
		throw new NoSuchCupcakeExists("Cupcake Price is not modified.");
	}

	}

	@Override
	public CupcakeDetails modifyCupcakeName(CupcakeDetails cupcakedetails) throws NoSuchCupcakeExists {
		methodName = "modifyCupcakeName(CupcakeDetails cupcakedetails)";
		logger.info(methodName + " method is called from SweetCherryService Layer");
		try {
		CupcakeDetails cupcakedetail2= dao.ModifyCupcakeName(cupcakedetails);
		
		if (cupcakedetail2 != null)
			return cupcakedetail2;
		else
			throw new NoSuchElementException("Cupcake Name is not modified.");
	}

	catch (NoSuchElementException e) {
		throw new NoSuchCupcakeExists("Cupcake Name is not modified.");
	}

	}

	@Override
	@Transactional
	public boolean removeCupcakeDetails(int cupcakeId) throws NoSuchCupcakeExists {
		methodName = "removeCupcakeDetails(int cupcakId)";
		logger.info(methodName + " method is called from SweetCherryService Layer");
        try {
		boolean result = dao.removeCupcakeDetails(cupcakeId);
		if (result)
			return result;
		else
			throw new NoSuchElementException("Cupcake with id" + cupcakeId + " is not found.");
        }
        catch(NoSuchElementException e) {
        	throw new NoSuchCupcakeExists("Cupcake with id" + cupcakeId + " is not found.");
        	
        }

	}

}
