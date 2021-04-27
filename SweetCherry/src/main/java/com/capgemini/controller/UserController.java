package com.capgemini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import com.capgemini.exceptions.NoSuchCupcakeExists;
import com.capgemini.model.CupcakeDetails;
import com.capgemini.service.SweetCherryService;

public class UserController {

	
	@Autowired
	private SweetCherryService service;
	@GetMapping(path = "cupcakeById")
    public CupcakeDetails getCupcakeById(int cupcakeId) throws NoSuchCupcakeExists {
        return service.findCupcakeById(cupcakeId);
    }
	
	@GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
	public List<CupcakeDetails> getAllCupcakeDetails(){
		return service.findAllCupcake();
	
}
}
