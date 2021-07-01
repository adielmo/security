package com.security.demo.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.demo.entity.Usuario;
import com.security.demo.repository.UsuarioRepository;

@Service
public class AppUserDetailsService implements UserDetailsService{
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
Usuario usuario = usuarioRepository.findByEmail(email)
                   .orElseThrow(() -> new UsernameNotFoundException("Úsuario ou Senha incorreta!"));
		return new User(email, usuario.getSenha(), getPermissoes(usuario));
	}

	private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		usuario.getPermisoes().forEach(x -> authorities.add(new SimpleGrantedAuthority(x.getDescricao().toUpperCase())));
		
		return authorities;
	}

}
