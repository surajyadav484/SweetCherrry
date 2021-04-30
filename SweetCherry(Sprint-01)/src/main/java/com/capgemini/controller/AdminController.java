package com.capgemini.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.exceptions.NoSuchCupcakeExists;
import com.capgemini.logger.LoggerController;
import com.capgemini.model.CupcakeDetails;
import com.capgemini.service.SweetCherryService;
import com.capgemini.service.SweetCherryServiceImpl;


@RestController
@RequestMapping(path = "admin")
public class AdminController {

	@Autowired
	private SweetCherryService cupcakeservice;
	

	private Logger logger = LoggerController.getLogger(SweetCherryServiceImpl.class);
	String methodName = null;
	
	// http://localhost:9090/sweetcherry-api/admin/modifyCupcakePrice/
	@GetMapping(produces = "application/json", path = "modifyCupcakePrice/{cupcakeId}/{cupcakePrice}")
	public CupcakeDetails modifyCupcakePrice(@PathVariable("cupcakeId") int cupcakeId,@PathVariable("cupcakePrice") double cupcakePrice ) throws NoSuchCupcakeExists {
		return cupcakeservice.updateCupcakePriceByCupcakeId(cupcakeId, cupcakePrice);
	}

	// http://localhost:9090/sweetcherry-api/admin/modifyCupcakeName
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces ="application/json", path = "modifyCupcakeName/{cupcakeId}/{cupcakeName}") 
	public CupcakeDetails modifyCupcakeName(@PathVariable("cupcakeId") int cupcakeId,@PathVariable("cupcakeName") String cupcakeName) throws NoSuchCupcakeExists { 
		return cupcakeservice.modifyCupcakeName(cupcakeId,cupcakeName); 
	}

	// http://localhost:9090/sweetcherry-api/admin/removeCupcakeDeatils/
	  @DeleteMapping(path = "/removeCupcakeDeatils/{cupcakeId}") 
	  public String removeCupcakeDetailsById(@PathVariable("cupcakeId") int cupcakeId) throws NoSuchCupcakeExists { 
		  return cupcakeservice.removeCupcakeDetails(cupcakeId); 
		  }
	 
	
}
