package com.psoft.tccmatch.service;

import com.psoft.tccmatch.DTO.UsuarioOwnedResponseDTO;
import com.psoft.tccmatch.model.Coordenador;
import com.psoft.tccmatch.model.Usuario;
import java.util.Optional;

public interface UsuarioService {

    Optional<Usuario> findByEmail(String email);

    Boolean verificaSenha(String email, String senha);

    UsuarioOwnedResponseDTO criaOwnedResponseDTO(Usuario usuario);

    Coordenador getCoordenador();

}