package com.capgemini.exceptions;

// NoSuchAddressExists exception class extends the Exception class
public class NoSuchAddressExists extends Exception {
	// Defining default constructor
	public NoSuchAddressExists() {

	}

	// Defining parameterized constructor with String parameter
	public NoSuchAddressExists(String message) {

		// Calling super constructor by passing the error message
		super(message);
	}
}
