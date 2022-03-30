package com.psoft.tccmatch.DTO;

import com.psoft.tccmatch.enums.AutorTemaEnum;
import com.psoft.tccmatch.enums.StatusTrabalhoEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TemaOwnedResponseDTO {
	
	@EqualsAndHashCode.Include
	private long id;

	private String titulo;

	private StatusTrabalhoEnum status;

	private AutorTemaEnum autor;

}
