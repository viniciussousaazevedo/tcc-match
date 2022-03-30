package com.psoft.tccmatch.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemaDTO {

	private String titulo;
	
	private String descricao;
	
	private String semestre;

	private List<Long> idAreasDeEstudo;
	
	private Long orientadorId;

	private Long orientandoId;

}
