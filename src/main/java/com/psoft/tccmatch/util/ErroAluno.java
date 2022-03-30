package com.psoft.tccmatch.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroAluno {

	static final String ALUNO_NAO_ENCONTRADO = "Aluno com id %s não está cadastrado";
	
	public static ResponseEntity<CustomErrorType> erroAlunoNaoEncontrado(long id) {
		return new ResponseEntity<>(new CustomErrorType(String.format(ErroAluno.ALUNO_NAO_ENCONTRADO, id)),
                HttpStatus.NOT_FOUND);
	}
}
