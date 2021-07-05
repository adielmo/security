package com.security.demo.resource;

import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.security.demo.entity.request.ProdutoRequest;
import com.security.demo.entity.response.ProdutoResponse;
import com.security.demo.service.ProdutoService;
import com.security.demo.util.page.ProdutoPageable;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(ProdutoController.class);

	
	@Autowired
	private ProdutoService produtoService;
	
	@GetMapping
	public ResponseEntity<Page<ProdutoResponse>> buscarProdutosPage(ProdutoPageable produtoPageable){
		LOGGER.info("Iniciando a buscar de um Pageable Produto");
		return ResponseEntity.ok(produtoService.getProdutoPage(produtoPageable));
	}

	
	
	@PostMapping
	public ResponseEntity<ProdutoResponse> saveProduto(@RequestBody ProdutoRequest produtoRequest){
		LOGGER.info("Iniciando a criação de um Produto");

		ProdutoResponse produtoSalve= produtoService.create(produtoRequest);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				                             .buildAndExpand(produtoSalve.getId()).toUri();
		
	return ResponseEntity.created(uri).body(produtoSalve);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProdutoResponse> buscarOneProduto(@PathVariable Long id){
		LOGGER.info("Iniciando a duscar de um Produto pelo Id");

		return new ResponseEntity<ProdutoResponse>(produtoService.buscarUmProduto(id), HttpStatus.OK);
	}
	
	@GetMapping("/nome")
	public ResponseEntity<ProdutoResponse> buscarProdutoNome(@RequestParam String nome){
		LOGGER.info("Iniciando a buscar de um Produto pelo Nome");

		return new ResponseEntity<ProdutoResponse>(produtoService.buscarProdutoNome(nome), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProdutoResponse> updateProduto(@Valid @RequestBody ProdutoRequest produtoRequest, @PathVariable Long id){
		LOGGER.info("Iniciando atualizção de um Produto");

		ProdutoResponse produtoSalve= produtoService.updateProduto(id, produtoRequest);		
		
	return new ResponseEntity<ProdutoResponse>(produtoSalve, HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProdutoId(@PathVariable Long id) {
		LOGGER.info("Iniciando uma Exclusão do Produto pelo Id");

		return produtoService.deleteProduto(id) == true ? 
								ResponseEntity.ok().build()
							  : ResponseEntity.badRequest().build();

	}

}
