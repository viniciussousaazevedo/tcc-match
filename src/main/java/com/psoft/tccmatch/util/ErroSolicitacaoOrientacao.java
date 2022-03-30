package com.psoft.tccmatch.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroSolicitacaoOrientacao {

	static final String SOLICITACAO_NAO_ENCONTRADA = "Solicitação com id %s não está cadastrada";

	static final String NAO_CONFERE_ORIENTADOR = "Você não é o orientador requisitado nesta Solicitação";

	static final String SOLICITACAO_JA_ACEITA = "Solicitação com id %s já consta como aceita";
	
	public static ResponseEntity<CustomErrorType> erroSolicitacaoNaoEncontrado(long id) {
		return new ResponseEntity<>(new CustomErrorType(String.format(ErroSolicitacaoOrientacao.SOLICITACAO_NAO_ENCONTRADA, id)),
                HttpStatus.NOT_FOUND);
	}
	
	public static ResponseEntity<CustomErrorType> erroOrientadorErrado() {
		return new ResponseEntity<>(new CustomErrorType(String.format(ErroSolicitacaoOrientacao.NAO_CONFERE_ORIENTADOR)),
                HttpStatus.NOT_FOUND);
	}

	public static ResponseEntity<CustomErrorType> erroSolicitacaoJaAceita(long id) {
		return new ResponseEntity<>(new CustomErrorType(String.format(ErroSolicitacaoOrientacao.SOLICITACAO_JA_ACEITA, id)),
                HttpStatus.NOT_FOUND);
	}
}
