package com.security.demo.resource;

import java.net.URI;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.security.demo.entity.request.ClienteRequest;
import com.security.demo.entity.request.ProdutoRequest;
import com.security.demo.entity.response.ClienteResponse;
import com.security.demo.entity.response.ProdutoResponse;
import com.security.demo.service.cliente.ClienteService;
import com.security.demo.service.produto.ProdutoService;
import com.security.demo.util.page.ClientePageable;
import com.security.demo.util.page.ProdutoPageable;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(ClienteController.class);

	
	@Autowired
	private ClienteService clienteService;
	
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CLIENTE') and #oauth2.hasScope('read')")
	@GetMapping
	public ResponseEntity<Page<ClienteResponse>> buscarProdutosPage(ClientePageable clientePageable){
		LOGGER.info("Iniciando a buscar de um Pageable Cliente");
		return ResponseEntity.ok(clienteService.getClientePage(clientePageable));
	}

                             	// ROLE do usuário and Scope do Cliente = true
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CLIENTE') and #oauth2.hasScope('write')")
	@PostMapping
	public ResponseEntity<ClienteResponse> saveProduto(@RequestBody ClienteRequest clienteRequest){
		LOGGER.info("Iniciando a criação de um Cliente");

		ClienteResponse clienteSalve= clienteService.create(clienteRequest);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				                             .buildAndExpand(clienteSalve.getId()).toUri();
		
	return ResponseEntity.created(uri).body(clienteSalve);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ClienteResponse> buscarOneProduto(@PathVariable Long id){
		LOGGER.info("Iniciando a duscar de um Cliente pelo Id");

		return new ResponseEntity<ClienteResponse>(clienteService.buscarUmCliente(id), HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CLIENTE') and #oauth2.hasScope('read')")
	@GetMapping("/nome")
	public ResponseEntity<ClienteResponse> buscarProdutoNome(@PathParam("nome") String nome){
		LOGGER.info("Iniciando a buscar de um Produto pelo Nome");

		return new ResponseEntity<ClienteResponse>(clienteService.buscarClienteNome(nome), HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ROLE_ATUALIZAR_CLIENTE') and #oauth2.hasScope('write')")
	@PutMapping("/{id}")
	public ResponseEntity<ClienteResponse> updateProduto(@Valid @RequestBody ClienteRequest clienteRequest, @PathVariable Long id){
		LOGGER.info("Iniciando atualizção de um Cliente");		 	
		
	return new ResponseEntity<ClienteResponse>(clienteService.updateCliente(id, clienteRequest),
			                                  HttpStatus.NO_CONTENT);
	}
	
	@PreAuthorize("hasAuthority('ROLE_REMOVER_CLIENTE') and #oauth2.hasScope('write')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProdutoId(@PathVariable Long id) {
		LOGGER.info("Iniciando uma Exclusão do Cliente pelo Id");

		return clienteService.deleteCliente(id) == true ? 
								ResponseEntity.ok().build()
							  : ResponseEntity.badRequest().build();

	}

}
