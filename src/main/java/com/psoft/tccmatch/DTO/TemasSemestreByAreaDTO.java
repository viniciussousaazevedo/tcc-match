package com.psoft.tccmatch.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemasSemestreByAreaDTO {

	private AreaEstudoOwnedResponseDTO area;
	private List<TemaOwnedResponseDTO> temas;
		
}
