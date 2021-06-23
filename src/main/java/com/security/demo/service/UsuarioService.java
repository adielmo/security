package com.security.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.demo.dto.UsuarioDtoValido;
import com.security.demo.entity.Usuario;
import com.security.demo.repository.UsuarioRepository;
import com.security.demo.securityException.EntidadeNaoEncotradaException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;

	public List<Usuario> todosUsuarios() {

		return usuarioRepository.findAll();
	}

	@Transactional
	public Usuario salveUsuario(Usuario usuario) {
		String nome = usuario.getNome().trim();
		Usuario newUsuario = getUsuarioLogin(nome);

		if (newUsuario != null) {
			throw new EntidadeNaoEncotradaException(String.format("%s já cadastro como usuário!", nome));
		}

		usuario.setSenha(encoder.encode(usuario.getSenha()));

		return usuarioRepository.save(usuario);

	}

	public UsuarioDtoValido validarUsuarioBd(String nome, String senha) {
		Usuario usuario = usuarioRepository.findByNomeEquals(nome).orElse(null);

		if (usuario == null) {
			throw new RuntimeException(String.format("Nome do usuario incorreto!"));

		}
		boolean isValido = validarSenhaEncoder(senha, usuario.getSenha());
		if (!isValido) {
			throw new RuntimeException(String.format("%s senha incorreta!", senha));
		}
		return new UsuarioDtoValido(usuario.getNome(), true);

	}

	private boolean validarSenhaEncoder(String senha, String password) {
		return encoder.matches(senha, password);
	}

	public Usuario getUsuario(Long id) {
		return usuarioRepository.findById(id).orElse(null);
	}

	public Usuario getUsuarioLogin(String nome) {
		return usuarioRepository.buscarPorNome(nome).orElse(null);
	}
}
