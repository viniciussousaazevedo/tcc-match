package com.psoft.tccmatch.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destinatario;

    private String assunto;

    private String conteudo;

    public Email(String destinatario, String assunto, String conteudo) {
        this.destinatario = destinatario;
        this.assunto = assunto;
        this.conteudo = conteudo;
    }

}
