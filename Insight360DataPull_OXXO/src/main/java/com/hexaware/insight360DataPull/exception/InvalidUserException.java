package com.hexaware.insight360DataPull.exception;

public class InvalidUserException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String errorCode;
	private String errorMessage;

	public InvalidUserException(String message) {
		this.errorMessage=message;
	}
	
	public InvalidUserException(String errorCode,String message) {
		this.errorCode = errorCode;
		this.errorMessage=message;
	}

	public String getErrorCode() {	
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
