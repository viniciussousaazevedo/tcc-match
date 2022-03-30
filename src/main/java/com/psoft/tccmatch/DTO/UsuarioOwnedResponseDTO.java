package com.psoft.tccmatch.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioOwnedResponseDTO {

    private Long id;

    private String email;

    private String nome;

}
