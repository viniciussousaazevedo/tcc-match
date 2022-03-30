package com.psoft.tccmatch.model;

import com.psoft.tccmatch.enums.StatusTrabalhoEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class SolicitacaoOrientacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "solicitante_id", referencedColumnName = "id")
    private Aluno solicitante;

    @ManyToOne
    @JoinColumn(name = "orientador_id", referencedColumnName = "id")
    private Professor orientador;

    @ManyToOne
    @JoinColumn(name = "tema_id", referencedColumnName = "id")
    private Tema tema;

    @Enumerated(EnumType.STRING)
    private StatusTrabalhoEnum status;

    private String respostaSolicitacao;

    public SolicitacaoOrientacao(Aluno solicitante, Professor orientador, Tema tema, StatusTrabalhoEnum status) {
        this.solicitante = solicitante;
        this.orientador = orientador;
        this.tema = tema;
        this.status = status;
    }

    @Override
    public String toString() {
        return "SolicitacaoOrientacao{" +
                "id=" + id +
                ", status=" + status +
                ", solicitante=" + solicitante.getNome() +
                ", orientador=" + orientador.getNome() +
                ", respostaSolicitacao=" + respostaSolicitacao + '\'' +
                '}';
    }
}
