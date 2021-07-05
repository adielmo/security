package com.security.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.security.demo.entity.Produto;
import com.security.demo.entity.response.ProdutoResponse;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
	 
	@Query("SELECT new com.security.demo.entity.response.ProdutoResponse(prod.id, prod.nome, prod.preco, prod.quantidade) FROM Produto prod")
	List<ProdutoResponse> fidAllProdut();
		
	@Query("SELECT new com.security.demo.entity.response.ProdutoResponse(prod.id, prod.nome, prod.preco, prod.quantidade) FROM Produto prod WHERE prod.id = :id")
	Optional<ProdutoResponse> buscarProdut(@Param("id") Long id);
	
	@Query("SELECT new com.security.demo.entity.response.ProdutoResponse(prod.id, prod.nome, prod.preco, prod.quantidade) FROM Produto prod WHERE nome like %:nome%")
	List<ProdutoResponse> buscarProdutNome(@Param("nome") String nome);
	
	

}
