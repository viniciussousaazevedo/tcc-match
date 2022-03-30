package com.psoft.tccmatch.service;

import com.psoft.tccmatch.DTO.SolicitacaoOrientacaoResponseDTO;
import com.psoft.tccmatch.model.Aluno;
import com.psoft.tccmatch.model.Professor;
import com.psoft.tccmatch.model.SolicitacaoOrientacao;
import com.psoft.tccmatch.model.Tema;

import java.util.List;

public interface SolicitacaoOrientacaoService {
	
	List<SolicitacaoOrientacao> listarSolicitacoes();

	void salvarSolicitacao(SolicitacaoOrientacao solicitacaoOrientacao);
	
	SolicitacaoOrientacao criarSolicitacao(Aluno aluno, Tema tema);

	SolicitacaoOrientacaoResponseDTO criaResponseDTO(SolicitacaoOrientacao solicitacao);

	void aceitarSolicitacaoOrientacao(SolicitacaoOrientacao solicitacao, String mensagem);

	void recusarSolicitacaoOrientacao(SolicitacaoOrientacao solicitacao, String mensagem);
}
