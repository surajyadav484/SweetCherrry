package com.capgemini.exceptions;

public class NoSuchCupcakeExists extends Exception {
	public NoSuchCupcakeExists() {
		
	}
	public  NoSuchCupcakeExists(String message) {
		super(message);
	}

}
