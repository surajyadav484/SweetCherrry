package com.capgemini.exceptions;

public class NoSuchAddressExists extends Exception {
	public NoSuchAddressExists() {

	}

	public NoSuchAddressExists(String message) {
		super(message);
	}
}
