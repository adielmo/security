package com.security.demo.securityException;

public class CpfJaCadastradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CpfJaCadastradoException(String msg) {
		super(msg);
	}
}
