package com.capgemini.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.capgemini.exceptions.NoSuchCupcakeExists;
import com.capgemini.model.CupcakeDetails;

public interface SweetCherryService {
	public CupcakeDetails findCupcakeById(int cupcakeId) throws NoSuchCupcakeExists;

	public CupcakeDetails addCupcakeDetails(CupcakeDetails cupcakedetails) throws NoSuchCupcakeExists;

	public List<CupcakeDetails> findAllCupcake() throws NoSuchCupcakeExists;

	public CupcakeDetails modifyCupcakePrice(CupcakeDetails cupcakedetails) throws NoSuchCupcakeExists ;

	public CupcakeDetails modifyCupcakeName(CupcakeDetails cupcakedetails) throws NoSuchCupcakeExists ;

	public boolean removeCupcakeDetails(int cupcakeId) throws NoSuchCupcakeExists;

	
}
