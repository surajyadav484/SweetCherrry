package com.capgemini.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.service.SweetCherryService;

@RestController
@RequestMapping(path = "admin")
public class AdminController {

	@Autowired
	private SweetCherryService cupcakeservice;
	
	
}
