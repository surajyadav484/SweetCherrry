package com.capgemini.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SweetCherry {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(SweetCherry.class, args);
	}

}
