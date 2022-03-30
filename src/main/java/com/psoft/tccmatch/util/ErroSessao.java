package com.psoft.tccmatch.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroSessao {

    private ErroSessao() {}

    static final String USUARIO_NAO_POSSUI_PERMISSAO = "Esse usuário não possui permissão para realizar essa ação.";

    static final String USUARIO_NAO_LOGADO = "É necessário estar logado para realizar essa operação.";

    public static ResponseEntity<CustomErrorType> erroUsuarioSemPermissao() {
        return new ResponseEntity<>(new CustomErrorType(String.format(ErroSessao.USUARIO_NAO_POSSUI_PERMISSAO)),
                HttpStatus.FORBIDDEN);
    }

    public static ResponseEntity<CustomErrorType> erroUsuarioNaoLogado() {
        return new ResponseEntity<>(new CustomErrorType(String.format(ErroSessao.USUARIO_NAO_LOGADO)),
                HttpStatus.FORBIDDEN);
    }
}
