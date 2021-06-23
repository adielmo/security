package com.security.demo.dto;

public class UsuarioDtoValido {

	private String nome;
	private boolean acesso;

	public UsuarioDtoValido() {
		// TODO Auto-generated constructor stub
	}

	public UsuarioDtoValido(String nome, boolean acesso) {

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
