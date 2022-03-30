package com.psoft.tccmatch.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorDTO {

    private String nome;

    private String email;

	private String laboratorios;
    
    private List<Long> idAreasDeEstudo;
    
    private boolean isDisponivel;

    private String senha;
}
