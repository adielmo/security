package com.security.demo.map.request;

import org.springframework.stereotype.Component;

import com.security.demo.entity.Produto;
import com.security.demo.entity.request.ProdutoRequest;
import com.security.demo.map.Map;
import com.security.demo.securityException.EntidadeVaziaException;

@Component
public class ProdutoMap implements Map<ProdutoRequest, Produto>{

	@Override
	public Produto converter(ProdutoRequest produtoRequest) {

		if (produtoRequest == null) {
			throw new EntidadeVaziaException("Entidade Produto encontra-se vázia!");
		}
		Produto produto = new Produto();

		produto.setNome(produtoRequest.getNome());
		produto.setPreco(produtoRequest.getPreco());
		produto.setQuantidade(produtoRequest.getQuantidade());
		return produto;
	}


}
