package com.capgemini.exceptions;

public class PaymentFailedException extends Exception {

	public PaymentFailedException() {
		
	}
	public PaymentFailedException(String message) {
		super(message);
	}
}