package com.psoft.tccmatch.service;

import com.psoft.tccmatch.DTO.SessaoResponseDTO;
import com.psoft.tccmatch.DTO.UsuarioDTO;
import com.psoft.tccmatch.enums.StatusUsuarioSessao;
import com.psoft.tccmatch.model.Sessao;
import com.psoft.tccmatch.model.Usuario;

import org.springframework.http.ResponseEntity;

public interface SessaoService {

    void registraSessao(UsuarioDTO usuarioDTO);

    void sairSessao();

    ResponseEntity<?> verificaSessao(StatusUsuarioSessao classe);

    SessaoResponseDTO criaResponseDTO();

    void salvaSessao();

    Sessao getSessao();

    boolean isAluno();

    boolean isProfessor();

    boolean isCoordenador();

    boolean isLogado();
}