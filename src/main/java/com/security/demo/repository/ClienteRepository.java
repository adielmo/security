package com.security.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.security.demo.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

//Optional<Usuario> findByNomeContaining(String nome);
	@Query("from Usuario where nome= :nome")
	Optional<Cliente> buscarPorNome(@Param("nome") String nome);
	
	Optional<Cliente> findByNomeEquals( String nome);

			
}
