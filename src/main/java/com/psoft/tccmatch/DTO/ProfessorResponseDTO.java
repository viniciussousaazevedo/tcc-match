package com.psoft.tccmatch.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorResponseDTO {
	
	private long id;

	private String nome;

	private String email;

	private String labs;

	private boolean isDisponivel;

	private List<AreaEstudoOwnedResponseDTO> areasEstudo;
}
