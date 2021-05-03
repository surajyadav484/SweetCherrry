package com.capgemini.exceptions;

public class NoSuchOrderExists extends Exception{

	private static final long serialVersionUID = 1L;

	public NoSuchOrderExists(String message) {
		super(message);
	}

	public NoSuchOrderExists() {
	}
}
