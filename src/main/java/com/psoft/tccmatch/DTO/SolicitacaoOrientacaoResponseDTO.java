package com.psoft.tccmatch.DTO;


import com.psoft.tccmatch.enums.StatusTrabalhoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitacaoOrientacaoResponseDTO {

    private long id;

    private AlunoOwnedResponseDTO solicitante;

    private ProfessorOwnedResponseDTO orientador;

    private TemaOwnedResponseDTO tema;

    private StatusTrabalhoEnum status;

    private String respostaSolicitacao;
}