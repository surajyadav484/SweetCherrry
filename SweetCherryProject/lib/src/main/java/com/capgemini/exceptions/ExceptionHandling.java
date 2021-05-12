package com.capgemini.exceptions;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.capgemini.logger.LoggerController;

@ControllerAdvice
public class ExceptionHandling {

	/*
	 * Logger class provides the methods for logging. logger object is used to log
	 * messages for specific system or application component
	 */
	private Logger logger = LoggerController.getLogger(ExceptionHandling.class);

	@ResponseBody

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)

	@ExceptionHandler({ NoSuchCupcakeExists.class, NoSuchOrderExists.class, NoSuchUserExists.class,
			NoSuchAddressExists.class, PaymentFailedException.class, InvalidIdException.class,
			UserNameAndPasswordDoNotMatchRegularExpression.class })
	public String exceptionHandler(Exception e) {

		logger.error("error encountered");

		return e.getMessage();
	}

}
