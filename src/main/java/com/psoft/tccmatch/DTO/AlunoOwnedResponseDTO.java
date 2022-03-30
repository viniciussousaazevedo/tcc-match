package com.psoft.tccmatch.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlunoOwnedResponseDTO {
	
	private long id;

	private String nome;

	private int matricula;

	private String email;

}
