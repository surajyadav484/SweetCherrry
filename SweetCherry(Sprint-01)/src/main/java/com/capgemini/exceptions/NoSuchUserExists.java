package com.capgemini.exceptions;

public class NoSuchUserExists extends Exception{

	private static final long serialVersionUID = 1L;

	public NoSuchUserExists(String message) {
		super(message);
	}
}
