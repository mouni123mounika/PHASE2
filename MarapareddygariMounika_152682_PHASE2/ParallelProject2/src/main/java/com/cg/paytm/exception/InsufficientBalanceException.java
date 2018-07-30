package com.cg.paytm.exception;

public class InsufficientBalanceException extends RuntimeException {
	
	private static final long serialVersionUID = 3705590195205247262L;

	public InsufficientBalanceException() {
		super();
	}

	public InsufficientBalanceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InsufficientBalanceException(String message, Throwable cause) {
		super(message, cause);
	}

	public InsufficientBalanceException(String message) {
		super(message);
	}

	public InsufficientBalanceException(Throwable cause) {
		super(cause);
	}
	
	

}
