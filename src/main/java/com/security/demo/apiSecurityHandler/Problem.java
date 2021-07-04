package com.security.demo.apiSecurityHandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Problem {
	
	private Integer status;
	private OffsetDateTime data;
	private String titulo;
	private List<Campo> campos;
	private String path;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public OffsetDateTime getData() {
		return data;
	}

	public void setData(OffsetDateTime data) {
		this.data = data;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<Campo> getCampos() {
		return campos;
	}

	public void setCampos(List<Campo> campos) {
		this.campos = campos;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public static class Campo {
		private String campo;
		private String messagem;

		public Campo() {
			// TODO Auto-generated constructor stub
		}

		public Campo(String campo, String messagem) {

			this.campo = campo;
			this.messagem = messagem;
		}

		public String getCampo() {
			return campo;
		}

		public String getMessagem() {
			return messagem;
		}

	}


}
