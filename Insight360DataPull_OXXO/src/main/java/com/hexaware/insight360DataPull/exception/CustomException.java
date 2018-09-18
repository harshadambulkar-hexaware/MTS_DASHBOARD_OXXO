package com.hexaware.insight360DataPull.exception;

public class CustomException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6336313587355783680L;
	private String message = null;
	 
    public CustomException() {
        super();
    }
 
    public CustomException(String message) {
        super(message);
        this.message = message;
    }
 
    public CustomException(Throwable cause) {
        super(cause);
    }
 
    @Override
    public String toString() {
        return message;
    }
 
    @Override
    public String getMessage() {
        return message;
    }
}
