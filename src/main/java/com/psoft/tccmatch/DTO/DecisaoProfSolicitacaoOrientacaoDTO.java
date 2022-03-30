package com.psoft.tccmatch.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DecisaoProfSolicitacaoOrientacaoDTO {
	
	private Long solicitacaoId;

	private String mensagemResposta;
}
