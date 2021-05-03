package com.capgemini.exceptions;

public class NoSuchCupcakeExists extends Exception {
	
	
	private static final long serialVersionUID = 1L;

	public NoSuchCupcakeExists() {

	}

	public NoSuchCupcakeExists(String message) {
		super(message);
	}

}
