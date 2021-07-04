package com.security.demo.resource;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.demo.dto.ClienteDtoValido;
import com.security.demo.entity.Cliente;
import com.security.demo.service.ClienteService;
@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	//@CrossOrigin(origins = "http://localhost:8000")
	@GetMapping
	public List<Cliente> buscarTodosUsuarios() throws JsonMappingException, JsonProcessingException{
		List<Cliente> cliente = clienteService.todosUsuarios();
		
		Cliente c1 = cliente.stream().findFirst().orElse(null);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		Cliente cc = mapper.convertValue(c1, Cliente.class);
		
		System.out.println(cc.toString());
	
		//return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
	//return (!usuario.isEmpty())? ResponseEntity.ok().body(usuario) : ResponseEntity.notFound().build();
		return cliente;
	}
	
	@PostMapping
	public ResponseEntity<Cliente> salvarCliente(@RequestBody Cliente cliente){
		
		try{
		Cliente usuarioSalve = clienteService.salveCliente(cliente);
		
		return ResponseEntity.ok().body(usuarioSalve);
				                    
		}catch (RuntimeException e) {
			return  ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
	}
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> validarUser(@PathVariable Long id){
		
			Cliente usuario =  clienteService.getUsuario(id);

			
			return usuario != null ? ResponseEntity.ok().body(usuario)
					               :ResponseEntity.status(HttpStatus.NOT_FOUND).build();
					                    
			
	}	
	@GetMapping("/validar")
	public ResponseEntity<ClienteDtoValido> validarUser(@PathParam(value = "nome") String nome, @PathParam(value = "senha") String senha){
		try{
			ClienteDtoValido usuarioValido =  clienteService.validarUsuarioBd(nome, senha);

			
			return ResponseEntity.ok().body(usuarioValido);
					                    
			}catch (RuntimeException e) {
				return  ResponseEntity.status(HttpStatus.PROXY_AUTHENTICATION_REQUIRED).build();
			}
	}

}
