package com.security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.demo.entity.Permisao;

@Repository
public interface PermissaoRepository extends JpaRepository<Permisao, Long> {

}
