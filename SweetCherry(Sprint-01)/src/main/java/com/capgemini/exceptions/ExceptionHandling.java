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

	/*
	 * @ExceptionHandler(NoSuchCupcakeExists.class) public ResponseEntity<?>
	 * handleException(NoSuchStudentException e, WebRequest request) { return new
	 * ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST); }
	 */
	
	 private Logger logger= LoggerController.getLogger(ExceptionHandling.class);
	  @ResponseBody
	  
	  @ResponseStatus(value =HttpStatus.BAD_REQUEST)
	  
		@ExceptionHandler( { NoSuchCupcakeExists.class ,NoSuchOrderExists.class ,NoSuchUserExists.class})
		public String exceptionHandler(Exception e) {
		  logger.error("error encountered");
		  //System.out.println(e.printStackTrace());
		  String message = e.getMessage();
			return message;
		}
	 
}
