package com.security.demo.securityException;

public class EntidadeNaoEncotradaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntidadeNaoEncotradaException(String msg) {
		super(msg);
	}
}
