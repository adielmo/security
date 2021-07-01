package com.security.demo.dto;

public class ClienteDtoValido {

	private String nome;
	private boolean acesso;

	public ClienteDtoValido() {
		// TODO Auto-generated constructor stub
	}

	public ClienteDtoValido(String nome, boolean acesso) {

		this.nome = nome;
		this.acesso = acesso;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isAcesso() {
		return acesso;
	}

	public void setAcesso(boolean acesso) {
		this.acesso = acesso;
	}

}
