package com.security.demo.resource;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.security.demo.entity.request.ProdutoRequest;
import com.security.demo.entity.response.ProdutoResponse;
import com.security.demo.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@PostMapping
	public ResponseEntity<ProdutoResponse> listarTodos(@RequestBody ProdutoRequest produtoRequest){
		ProdutoResponse produtoSalve= produtoService.create(produtoRequest);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				                             .buildAndExpand(produtoSalve.getId()).toUri();
		
	return ResponseEntity.created(uri).body(produtoSalve);
	}

}
