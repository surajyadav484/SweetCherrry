package com.capgemini.exceptions;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.capgemini.logger.LoggerController;

@ControllerAdvice
public class ExceptionHandling {

	private Logger logger = LoggerController.getLogger(ExceptionHandling.class);

	// @ResponseBody is a Spring annotation which binds a method return value to the
	// web response body.
	@ResponseBody

	// @ResponseStatus Marks a method or exception class with the status code() and
	// reason() that should be returned
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)

	// The @ExceptionHandler is an annotation used to handle the specific exceptions
	// and sending the custom responses to the client
	@ExceptionHandler({ NoSuchCupcakeExists.class, NoSuchOrderExists.class, PaymentFailedException.class,
			NoSuchAddressExists.class, NoSuchUserExists.class })

	// this method is sued to handle the different exception.
	public String exceptionHandler(Exception e) {
		logger.error("error encountered"); // error level logger is used.

		// returning message to the calling method
		return e.getMessage();
		
		
	}

}