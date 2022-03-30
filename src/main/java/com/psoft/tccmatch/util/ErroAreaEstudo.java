package com.psoft.tccmatch.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroAreaEstudo {
	
	static final String AREA_NAO_EXISTE = "A área de estudo de id igual a %s não foi cadastrada no sistema";
	static final String AREA_JA_CADASTRADA = "A área de estudo de id igual a %s já foi cadastrada no sistema";
	
	public static ResponseEntity<CustomErrorType> erroAreaNaoEncontrada(long id){
		return new ResponseEntity<>(new CustomErrorType(String.format(ErroAreaEstudo.AREA_NAO_EXISTE, id)),
                HttpStatus.NOT_FOUND);
	}
	public static ResponseEntity<CustomErrorType> erroAreaJaCadastrada(long id){
		return new ResponseEntity<>(new CustomErrorType(String.format(ErroAreaEstudo.AREA_JA_CADASTRADA, id)),
                HttpStatus.BAD_REQUEST);
	}
}
