package com.psoft.tccmatch.repository;

import com.psoft.tccmatch.model.Professor;
import com.psoft.tccmatch.model.SolicitacaoOrientacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SolicitacaoOrientacaoRepository extends JpaRepository<SolicitacaoOrientacao, Long>{
    List<SolicitacaoOrientacao> findByOrientador(Professor professor);

    Optional<SolicitacaoOrientacao> findById(Long Id);
}