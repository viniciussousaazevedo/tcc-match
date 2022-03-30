package com.psoft.tccmatch.DTO;

import java.util.List;

import com.psoft.tccmatch.enums.AutorTemaEnum;
import com.psoft.tccmatch.enums.StatusTrabalhoEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemaResponseDTO {
	
	private Long id;

	private String titulo;

	private String descricao;
	
	private String semestre;

	private StatusTrabalhoEnum status;

	private AutorTemaEnum autor;

	private ProfessorOwnedResponseDTO orientador;

	private AlunoOwnedResponseDTO orientando;

	private List<AreaEstudoOwnedResponseDTO> areasEstudo;

}
