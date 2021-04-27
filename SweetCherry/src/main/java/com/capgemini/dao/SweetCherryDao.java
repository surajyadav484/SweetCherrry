package com.capgemini.dao;

import java.util.List;

import com.capgemini.model.CupcakeDetails;
import com.capgemini.model.Orders;

public interface SweetCherryDao {
	public CupcakeDetails AddCupcakeDetails(CupcakeDetails cupcakedetails);
    public CupcakeDetails readCupcakeDetailsById(int cupcakeId);
	public List<CupcakeDetails> readAllCupcakeDetails();
	public CupcakeDetails ModifyCupcakePrice(CupcakeDetails cupcakedetails);

	
	public CupcakeDetails ModifyCupcakeName(CupcakeDetails cupcakedetails);
	public boolean  removeCupcakeDetails(int cupcakeId);
	
	
	}
