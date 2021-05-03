package com.capgemini.exceptions;

public class NoSuchUserExists extends Exception{

	public NoSuchUserExists(String message) {
		super(message);
	}
}