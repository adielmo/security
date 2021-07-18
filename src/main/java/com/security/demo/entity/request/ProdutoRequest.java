package com.security.demo.entity.request;

public class ProdutoRequest {
	
	//@NotBlank
	private String nome;
	
	//@PositiveOrZero
	//@NotNull
	private Double preco;
	
	//@PositiveOrZero
	//@NotNull
	private Integer quantidade;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	

}
