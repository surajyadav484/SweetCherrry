package com.capgemini.exceptions;

public class UserNameAndPasswordDoNotMatchRegularExpression extends Exception {
	
	private static final long serialVersionUID = 1L;

	public UserNameAndPasswordDoNotMatchRegularExpression() {
	}

	public UserNameAndPasswordDoNotMatchRegularExpression(String msg) {
		super(msg);
	}
}
