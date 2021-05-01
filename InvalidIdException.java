package com.capgemini.exceptions;

public class InvalidIdException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidIdException(String message) {
		super(message) ;
	}
}
