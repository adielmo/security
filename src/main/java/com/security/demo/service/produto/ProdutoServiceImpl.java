package com.security.demo.service.produto;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.security.demo.entity.Produto;
import com.security.demo.entity.request.ProdutoRequest;
import com.security.demo.entity.response.ProdutoResponse;
import com.security.demo.map.Map;
import com.security.demo.repository.ProdutoRepository;
import com.security.demo.securityException.EntidadeNaoEncotradaException;
import com.security.demo.util.page.ProdutoPageable;

@Service
public class ProdutoServiceImpl implements ProdutoService {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(ProdutoService.class);

	@Autowired
	private ProdutoRepository prodRepository;

	@Autowired
	private Map<Produto, ProdutoResponse> responseMap;

	@Autowired
	private Map<ProdutoRequest, Produto> produtoMap;
	
	@Transactional
	@Override
	public ProdutoResponse create(ProdutoRequest produtoRequest) {
		LOGGER.info("Criando um registro de Produto");
		Assert.notNull(produtoRequest, "ProdutoRequest inválida, null !");

		Produto produto = produtoMap.converter(produtoRequest);
		return prodRepository.saveAndFlush(produto)
				     .map(prod -> responseMap.converter(prod));
	}

	@Override
	public List<ProdutoResponse> buscarTodosProdutos() {

		return prodRepository.fidAllProdut();
	}
	
	@Override
	public Page<ProdutoResponse> getProdutoPage(ProdutoPageable prodPageable) {
		LOGGER.info("Buscando um registro de Produto Page");
		Assert.notNull(prodPageable, "Request inválida, null !");
    Sort sort = Sort.by(prodPageable.getSortDirection(), prodPageable.getSortBy());
    Pageable pageable = PageRequest.of(prodPageable.getPageNumber(), prodPageable.getSizePage(), sort);
    
		return prodRepository.findAll(pageable).map(prod -> this.responseMap.converter(prod));
	}
	
    @Transactional
	@Override
	public ProdutoResponse updateProduto(Long id, ProdutoRequest produtoRequest) {
    	LOGGER.info("Uppdate um registro de Produto");
		Assert.notNull(produtoRequest, "Request inválida, null !");
   Produto produto = getProdutoId(id);
  Produto updateProduto =  mergeProduto(produtoRequest, produto);
 
		return prodRepository.saveAndFlush(updateProduto)
				   .map(prodUpd -> this.responseMap.converter(updateProduto));
	}

	@Transactional
	@Override
	public boolean deleteProduto(Long id) {
    	LOGGER.info("Deletando um registro do tipo Produto");
		Assert.notNull(id, "ID inválido !");

	  getProdutoId(id);
	  prodRepository.deleteById(id);
	  return true;	

//	LOGGER.warn("Erro ao remover o registro {}", id);

	}


	@Override
	public ProdutoResponse buscarUmProduto(Long id) {
    	LOGGER.info("Buscando um registro tipo Produto pelo Id");

		return prodRepository.buscarProdut(id)
				.orElseThrow(() -> new EntidadeNaoEncotradaException(String.format("Produto não encontrado, com Id %d !", id)));
	}

	@Override
	public ProdutoResponse buscarProdutoNome(String nome) {
    	LOGGER.info("Buscando um registro tipo Produto pelo Nome");

		return prodRepository.buscarProdutNome(nome.strip()).stream().findFirst()
				.orElseThrow(() -> new EntidadeNaoEncotradaException(String.format("Produto não encontrado, com Nome %s !", nome)));
	}
	
	public Produto getProdutoId(Long id) {
		 return prodRepository.findById(id)
		    		.orElseThrow(() -> new EntidadeNaoEncotradaException(String.format("Produto não encontrado, com Id %d !", id)));
	}
	

	   
    public Produto mergeProduto(ProdutoRequest produtoRequest, Produto produto) {

    	if (produtoRequest.getNome() != null) {
			produto.setNome(produtoRequest.getNome());
		}
    	
    	if (produtoRequest.getPreco() != null) {
			produto.setPreco(produtoRequest.getPreco());
		}
    	if (produtoRequest.getQuantidade() != null) {
			produto.setQuantidade(produtoRequest.getQuantidade());
		}
    	
    	return produto;
	}


	
}
