package com.security.demo.map.response;

import org.springframework.stereotype.Component;

import com.security.demo.entity.Produto;
import com.security.demo.entity.response.ProdutoResponse;
import com.security.demo.map.Map;
import com.security.demo.securityException.EntidadeVaziaException;

@Component
public class ProdutoResponseMap implements Map<Produto, ProdutoResponse> {

	@Override
	public ProdutoResponse converter(Produto produto) {

		if (produto == null) {

			throw new EntidadeVaziaException("Entidade Produto encontra-se vázia!");
		}
		ProdutoResponse response = new ProdutoResponse();
		response.setId(produto.getId());
		response.setNome(produto.getNome());
		response.setPreco(produto.getPreco());
		response.setQuantidade(produto.getQuantidade());

		return response;

	}

}
