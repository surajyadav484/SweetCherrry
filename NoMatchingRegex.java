package com.capgemini.exceptions;

public class NoMatchingRegex extends Exception {
	
	private static final long serialVersionUID = 1L;

	public NoMatchingRegex() {
	}

	public NoMatchingRegex(String msg) {
		super(msg);
	}
}
