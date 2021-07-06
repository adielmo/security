package com.security.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.demo.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	List<Cliente> findByNomeContaining(String nome);
	Optional<Cliente> findByEmail(String cpf);
	Optional<Cliente> findByCpf(String cpf);

	boolean existsByEmail(String email);
	boolean existsByCpf(String cpf);

}
