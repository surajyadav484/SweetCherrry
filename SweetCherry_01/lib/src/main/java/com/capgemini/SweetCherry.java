package com.capgemini;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/*SpringBootApplication annotaion shows the starting point of the programm execution.
it is combination of three anootations-
 1) @EnableAutoConfiguration
 2) @Configuration
 3) @ComponentScan*/
@SpringBootApplication
public class SweetCherry { //This is the main Class from where the program execution starts

	// this is the main method of the class.
	public static void main(String[] args) {

		//ApplicationContext is container which is going to store the beans.
		//SpringApplication.run() is used to launch the application.
		ApplicationContext context = SpringApplication.run(SweetCherry.class, args);

	}

}