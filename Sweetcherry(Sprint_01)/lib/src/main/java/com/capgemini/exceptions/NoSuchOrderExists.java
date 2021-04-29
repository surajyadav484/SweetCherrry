package com.capgemini.exceptions;

public class NoSuchOrderExists extends Exception{

	public NoSuchOrderExists(String message) {
		super(message);
	}

	public NoSuchOrderExists() {
	}
}