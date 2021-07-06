package com.security.demo.entity;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Endereco {
	
	private String bairro;
	private String cidade;
	private String logradouro;
	private String cep;
	private String numero;
	
	public Endereco() {
		// TODO Auto-generated constructor stub
	}

	public Endereco(String bairro, String cidade, String logradouro, String cep, String numero) {
		this.bairro = bairro;
		this.cidade = cidade;
		this.logradouro = logradouro;
		this.cep = cep;
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	
	
	
	
	

}
