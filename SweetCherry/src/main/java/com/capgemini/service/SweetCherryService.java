package com.capgemini.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.capgemini.exceptions.NoSuchCupcakeExists;
import com.capgemini.model.CupcakeDetails;

public interface SweetCherryService {
	public CupcakeDetails findCupcakeById(int cupcakeId) throws NoSuchCupcakeExists;

	public CupcakeDetails AddCupcakeDetails(CupcakeDetails cupcakedetails);

	public List<CupcakeDetails> findAllCupcake();

	public CupcakeDetails modifyCupcakePrice(CupcakeDetails cupcakedetails) ;

	public CupcakeDetails modifyCupcakeName(CupcakeDetails cupcakedetails) ;

	public boolean removeCupcakeDetails(int cupcakeId) throws NoSuchCupcakeExists;
}
