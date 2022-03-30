package com.psoft.tccmatch.util;

import com.psoft.tccmatch.DTO.ProfessorDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroProfessor {

    static final String PROFESSOR_ID_NAO_CADASTRADO = "Professor com id %s não está cadastrado";

    static final String PROFESSOR_JA_CADASTRADO = "Professor com nome %s já está cadastrado";
    
    public static ResponseEntity<CustomErrorType> erroProfessorIdNaoEncontrado(long id) {
        return new ResponseEntity<>(new CustomErrorType(String.format(ErroProfessor.PROFESSOR_ID_NAO_CADASTRADO, id)),
                HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<CustomErrorType> erroProfessorJaCadastrado(ProfessorDTO professorDTO) {
        return new ResponseEntity<>(new CustomErrorType(String.format(ErroProfessor.PROFESSOR_JA_CADASTRADO, professorDTO.getNome())),
                HttpStatus.CONFLICT);
    }
}
