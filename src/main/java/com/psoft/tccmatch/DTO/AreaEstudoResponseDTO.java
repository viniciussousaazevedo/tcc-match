package com.psoft.tccmatch.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaEstudoResponseDTO {
	
	private long id;

	private String nome;

	private List<ProfessorOwnedResponseDTO> professores;

	private List<AlunoOwnedResponseDTO> alunos;

	private List<TemaOwnedResponseDTO> temas;

}
