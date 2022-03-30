package com.psoft.tccmatch.enums;

public enum StatusUsuarioSessao {
    NAO_LOGADO("Não logado"),
    ALUNO("Aluno"),
    PROFESSOR("Professor"),
    COORDENADOR("Coordenador");

    private String status;

    StatusUsuarioSessao(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
