package com.rab3tech.service.exception;

/**
 * 
 * @author nagendra
 *
 */
public class BankServiceException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BankServiceException(String message) {
		super(message);
	}
	
	public BankServiceException(String message,Throwable throwable) {
		super(message,throwable);
	}

}
