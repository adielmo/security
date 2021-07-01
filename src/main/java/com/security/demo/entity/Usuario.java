package com.security.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {
	
	private Long id;
	private String nome;
	private String email;
	private String senha;

	@ManyToMany(fetch = FetchType.EAGER)//Toda vez q buscar Usuário, trás suas Permissões
	@JoinTable(name = "usuario_permissao", joinColumns = @JoinColumn(name="codigo_usuario")
	           , inverseJoinColumns = @JoinColumn(name="codigo_permissao"))
	List<Permisao> permisoes = new ArrayList<>();
	
	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	public Usuario(Long id, String nome, String email, String senha, List<Permisao> permisoes) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.permisoes = permisoes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Permisao> getPermisoes() {
		return permisoes;
	}

	public void setPermisoes(List<Permisao> permisoes) {
		this.permisoes = permisoes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	

}
