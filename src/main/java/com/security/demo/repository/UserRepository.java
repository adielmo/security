package com.security.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.security.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

//Optional<Usuario> findByNomeContaining(String nome);
	@Query("from Cliente where nome= :nome")
	Optional<User> buscarPorNome(@Param("nome") String nome);
	
	Optional<User> findByNomeEquals( String nome);

			
}
