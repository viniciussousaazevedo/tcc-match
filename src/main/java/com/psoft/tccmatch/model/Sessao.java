package com.psoft.tccmatch.model;


import javax.persistence.*;

import com.psoft.tccmatch.enums.StatusUsuarioSessao;
import lombok.Data;

@Entity
@Data
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusUsuarioSessao status;

    @OneToOne
    private Usuario usuario;

    public Sessao() {
        this.status = StatusUsuarioSessao.NAO_LOGADO;
        this.usuario = null;
    }
}
