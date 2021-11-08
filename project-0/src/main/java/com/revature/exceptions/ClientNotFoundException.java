package com.revature.exceptions;

public class ClientNotFoundException extends Exception {

	public ClientNotFoundException() {
		super();
	
	}

	public ClientNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public ClientNotFoundException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public ClientNotFoundException(String message) {
		super(message);
		
	}

	public ClientNotFoundException(Throwable cause) {
		super(cause);
		
	}

}
