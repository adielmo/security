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

import com.security.demo.dto.UsuarioDtoValido;
import com.security.demo.entity.Usuario;
import com.security.demo.service.UsuarioService;
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	//@CrossOrigin(origins = "http://localhost:8000")
	@GetMapping
	public List<Usuario> buscarTodosUsuarios(){
		return usuarioService.todosUsuarios();
		//return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
	//return (!usuario.isEmpty())? ResponseEntity.ok().body(usuario) : ResponseEntity.notFound().build();	
	}
	
	@PostMapping
	public ResponseEntity<Usuario> salvarUsuario(@RequestBody Usuario usuario){
		
		try{
		Usuario usuarioSalve = usuarioService.salveUsuario(usuario);
		
		return ResponseEntity.ok().body(usuarioSalve);
				                    
		}catch (RuntimeException e) {
			return  ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
	}
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> validarUser(@PathVariable Long id){
		
			Usuario usuario =  usuarioService.getUsuario(id);

			
			return usuario != null ? ResponseEntity.ok().body(usuario)
					               :ResponseEntity.status(HttpStatus.NOT_FOUND).build();
					                    
			
	}	
	@GetMapping("/validar")
	public ResponseEntity<UsuarioDtoValido> validarUser(@PathParam(value = "nome") String nome, @PathParam(value = "senha") String senha){
		try{
			UsuarioDtoValido usuarioValido =  usuarioService.validarUsuarioBd(nome, senha);

			
			return ResponseEntity.ok().body(usuarioValido);
					                    
			}catch (RuntimeException e) {
				return  ResponseEntity.status(HttpStatus.PROXY_AUTHENTICATION_REQUIRED).build();
			}
	}

}
