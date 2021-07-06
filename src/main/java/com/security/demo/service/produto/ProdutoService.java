package com.security.demo.service.produto;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.security.demo.entity.request.ProdutoRequest;
import com.security.demo.entity.response.ProdutoResponse;
import com.security.demo.util.page.ClientePageable;
import com.security.demo.util.page.ProdutoPageable;

//@Component
public interface ProdutoService {

	ProdutoResponse create(ProdutoRequest produtoRequest);
	
	Page<ProdutoResponse> getProdutoPage(ProdutoPageable produtoPageable);
	
	ProdutoResponse updateProduto(Long id, ProdutoRequest produtoRequest);
	
	boolean deleteProduto(Long id);

	List<ProdutoResponse> buscarTodosProdutos();
	
	ProdutoResponse buscarUmProduto(Long id);
	
	ProdutoResponse buscarProdutoNome(String nome);
   
	

}
