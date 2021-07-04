package com.security.demo.service;

import org.springframework.stereotype.Component;

import com.security.demo.entity.request.ProdutoRequest;
import com.security.demo.entity.response.ProdutoResponse;

@Component
public interface ProdutoService {

	ProdutoResponse create(ProdutoRequest produtoRequest);
	

}
