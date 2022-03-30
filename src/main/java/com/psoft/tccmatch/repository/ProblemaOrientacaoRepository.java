package com.psoft.tccmatch.repository;

import com.psoft.tccmatch.enums.AutorProblemaOrientacaoEnum;
import com.psoft.tccmatch.model.ProblemaOrientacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProblemaOrientacaoRepository extends JpaRepository<ProblemaOrientacao, Long>{
    
	List<ProblemaOrientacao> findByAutor(AutorProblemaOrientacaoEnum autor);
	
}