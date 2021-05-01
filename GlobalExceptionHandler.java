package com.capgemini.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler extends Exception {
	private static final long serialVersionUID = 1L;

	@ResponseBody
    @ResponseStatus(value =HttpStatus.BAD_REQUEST)

      @ExceptionHandler(value = {NoSuchUserExists.class, Exception.class, InvalidIdException.class, NoMatchingRegex.class})
      public String exceptionHandler(Exception e) {
          String message = e.getMessage();
          return message;
      }
}
 