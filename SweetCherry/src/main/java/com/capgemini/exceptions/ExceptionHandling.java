package com.capgemini.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandling {

	/*
	 * @ExceptionHandler(NoSuchCupcakeExists.class) public ResponseEntity<?>
	 * handleException(NoSuchStudentException e,WebRequest request) { return new
	 * ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST); }
	 */

	@ResponseBody

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)

	@ExceptionHandler(value = { NoSuchCupcakeExists.class })
	public String exceptionHandler(Exception e) {
		String message = e.getMessage();
		return message;
	}

}