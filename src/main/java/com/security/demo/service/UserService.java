package com.security.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.demo.dto.UserDtoValido;
import com.security.demo.entity.User;
import com.security.demo.repository.UserRepository;
import com.security.demo.securityException.EntidadeNaoEncotradaException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	/*
	 * @Autowired private BCryptPasswordEncoder encoder;
	 */

	public List<User> todosUser() {

		return userRepository.findAll();
	}

	@Transactional
	public User salveUser(User user) {
		String nome = user.getNome().trim();
		User newUser = getUserLogin(nome);

		if (newUser != null) {
			throw new EntidadeNaoEncotradaException(String.format("%s já cadastro como usuário!", nome));
		}

		//user.setSenha(encoder.encode(user.getSenha()));

		return userRepository.save(user);

	}

	public UserDtoValido validarUserBd(String nome, String senha) {
		User user = userRepository.findByNomeEquals(nome).orElse(null);

		if (user == null) {
			throw new RuntimeException(String.format("Nome do usuario incorreto!"));

		}
		boolean isValido = validarSenhaEncoder(senha, user.getSenha());
		if (!isValido) {
			throw new RuntimeException(String.format("%s senha incorreta!", senha));
		}
		return new UserDtoValido(user.getNome(), true);

	}

	private boolean validarSenhaEncoder(String senha, String password) {
		return true;//encoder.matches(senha, password);
	}
	public User getUser(Long id) {
		return userRepository.findById(id).orElseThrow(() ->
		new EntidadeNaoEncotradaException(String.format("User com código %d não existe!", id)));
	}
	public User getUserLogin(String nome) {
		return userRepository.buscarPorNome(nome).orElse(null);
	}
	
	/*
	 * @Bean public PasswordEncoder passwordEncoderResource() { return new
	 * BCryptPasswordEncoder(); }
	 */
}
