package com.capgemini;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.capgemini.model.CupcakeCategory;
import com.capgemini.model.CupcakeDetails;
import com.capgemini.model.Orders;
import com.capgemini.model.UserDetails;

@SpringBootApplication
public class SweetCherry {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(SweetCherry.class, args);
		
	}

}
