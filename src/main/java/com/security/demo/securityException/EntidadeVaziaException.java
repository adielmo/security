package com.security.demo.securityException;

public class EntidadeVaziaException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EntidadeVaziaException(String msg) {
		super(msg);
	}

}
