package com.psoft.tccmatch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psoft.tccmatch.model.AreaEstudo;

public interface AreaEstudoRepository extends JpaRepository<AreaEstudo, Long>{
	
	Optional<AreaEstudo> findByNome(String nome);
}
