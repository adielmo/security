package com.security.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.demo.dto.ClienteDtoValido;
import com.security.demo.entity.Cliente;
import com.security.demo.repository.ClienteRepository;
import com.security.demo.securityException.EntidadeNaoEncotradaException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;

	public List<Cliente> todosUsuarios() {

		return clienteRepository.findAll();
	}

	@Transactional
	public Cliente salveCliente(Cliente cliente) {
		String nome = cliente.getNome().trim();
		Cliente newUsuario = getClienteLogin(nome);

		if (newUsuario != null) {
			throw new EntidadeNaoEncotradaException(String.format("%s já cadastro como usuário!", nome));
		}

		cliente.setSenha(encoder.encode(cliente.getSenha()));

		return clienteRepository.save(cliente);

	}

	public ClienteDtoValido validarUsuarioBd(String nome, String senha) {
		Cliente usuario = clienteRepository.findByNomeEquals(nome).orElse(null);

		if (usuario == null) {
			throw new RuntimeException(String.format("Nome do usuario incorreto!"));

		}
		boolean isValido = validarSenhaEncoder(senha, usuario.getSenha());
		if (!isValido) {
			throw new RuntimeException(String.format("%s senha incorreta!", senha));
		}
		return new ClienteDtoValido(usuario.getNome(), true);

	}

	private boolean validarSenhaEncoder(String senha, String password) {
		return encoder.matches(senha, password);
	}

	public Cliente getUsuario(Long id) {
		return clienteRepository.findById(id).orElse(null);
	}

	public Cliente getClienteLogin(String nome) {
		return clienteRepository.buscarPorNome(nome).orElse(null);
	}
}
