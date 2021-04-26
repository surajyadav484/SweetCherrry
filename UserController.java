package com.capgemini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.model.CupcakeDetails;
import com.capgemini.service.SweetCherryService;

@RestController
@RequestMapping(path = "user")
public class UserController {

	@Autowired
	private SweetCherryService service;
	
	// http://localhost:9090/sweetcherry-api/user/cupcakes
	@GetMapping(path = "cupcakes")
	public List<CupcakeDetails>  getAllCupcakeDetails() {
		return service.findAllCupcakeDetails();
	}
	
}
