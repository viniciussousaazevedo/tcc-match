package com.psoft.tccmatch.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroUsuario {

    private ErroUsuario() {}

    static final String USUARIO_NAO_ENCONTRADO = "Usuario com email %s não está cadastrado";

    static final String SENHA_INCORRETA = "A senha está incorreta";

    static final String USUARIO_JA_LOGADO = "Já existe um usuário logado no sistema";

    static final String EMAIL_JA_CADASTRADO = "Este email já existe no sistema";

    public static ResponseEntity<CustomErrorType> erroUsuarioNaoEncontrado(String email) {
        return new ResponseEntity<>(new CustomErrorType(String.format(ErroUsuario.USUARIO_NAO_ENCONTRADO, email)),
                HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<CustomErrorType> erroSenhaIncorreta() {
        return new ResponseEntity<>(new CustomErrorType(String.format(ErroUsuario.SENHA_INCORRETA)),
                HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<CustomErrorType> erroUsuarioJaLogado() {
        return new ResponseEntity<>(new CustomErrorType(String.format(ErroUsuario.USUARIO_JA_LOGADO)),
                HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<CustomErrorType> erroEmailJaCadastrado() {
        return new ResponseEntity<>(new CustomErrorType(String.format(ErroUsuario.EMAIL_JA_CADASTRADO)),
                HttpStatus.BAD_REQUEST);
    }
}