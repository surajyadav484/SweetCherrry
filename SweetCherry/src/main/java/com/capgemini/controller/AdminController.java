package com.capgemini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.exceptions.NoSuchCupcakeExists;
import com.capgemini.model.CupcakeDetails;
import com.capgemini.service.SweetCherryService;

@RestController
@RequestMapping(path = "admin")
public class AdminController {

	@Autowired

	private SweetCherryService service;

	// http://localhost:9090/sweetcherry-api/admin/cupcakedetails
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json", path = "addcupcakedetails")
	public CupcakeDetails AddCupcakeDetails(@RequestBody CupcakeDetails cupcakedetails) throws NoSuchCupcakeExists {
		return service.addCupcakeDetails(cupcakedetails);

	}

	// http://localhost:9090/sweetcherry-api/admin/getByCupcakeId
	@GetMapping(path = "/getByCupcakeId/{cupcakeId}")
	public CupcakeDetails getCupcakeById(@PathVariable("cupcakeId") int cupcakeId) throws NoSuchCupcakeExists {
		return service.findCupcakeById(cupcakeId);
	}

	// http://localhost:9090/sweetcherry-api/admin/getAllCupcake
	@GetMapping(path = "/getAllCupcake", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CupcakeDetails> getAllCupcakeDetails() throws NoSuchCupcakeExists {
		return service.findAllCupcake();

	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json", path = "modifyCupcakePrice")

	public CupcakeDetails modifyCupcakePrice(@RequestBody CupcakeDetails cupcakedetails) throws NoSuchCupcakeExists {
		return service.modifyCupcakePrice(cupcakedetails);
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json", path = "modifyCupcakeName")

	public CupcakeDetails modifyCupcakeName(@RequestBody CupcakeDetails cupcakedetails) throws NoSuchCupcakeExists {
		return service.modifyCupcakePrice(cupcakedetails);
	}

	@DeleteMapping(path = "/removeCupcakeDeatils/{cupcakeId}")
	public boolean removeCupcakeDetailsById(@PathVariable("cupcakeId") int cupcakeId) throws NoSuchCupcakeExists {
		return service.removeCupcakeDetails(cupcakeId);
	}

}
