package com.security.demo.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.demo.entity.Produto;
import com.security.demo.entity.request.ProdutoRequest;
import com.security.demo.entity.response.ProdutoResponse;
import com.security.demo.map.Map;
import com.security.demo.repository.ProdutoRepository;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	private ProdutoRepository prodRepository;

	@Autowired
	private Map<Produto, ProdutoResponse> prodResponseMap;

	@Autowired
	private Map<ProdutoRequest, Produto> produtoMap;

	@Transactional
	@Override
	public ProdutoResponse create(ProdutoRequest produtoRequest) {
		Produto produto = produtoMap.converter(produtoRequest);

		return prodRepository.saveAndFlush(produto)
				           .map(prod -> prodResponseMap.converter(prod));
	}

}
