package com.psoft.tccmatch.DTO;

import com.psoft.tccmatch.enums.StatusUsuarioSessao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessaoResponseDTO {

    private StatusUsuarioSessao tipoUsuario;

    private UsuarioOwnedResponseDTO usuario;

}
