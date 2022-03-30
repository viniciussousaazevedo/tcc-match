package com.psoft.tccmatch.enums;

public enum StatusTrabalhoEnum {
    PENDENTE("Pendente"),
    RECUSADO("Recusado"),
    ACEITO("Aceito"),
    FINALIZADO("Finalizado");

    private String status;

    StatusTrabalhoEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
