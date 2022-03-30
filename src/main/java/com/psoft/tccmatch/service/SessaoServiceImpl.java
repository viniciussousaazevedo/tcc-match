package com.psoft.tccmatch.service;

import com.psoft.tccmatch.DTO.SessaoResponseDTO;
import com.psoft.tccmatch.DTO.UsuarioDTO;
import com.psoft.tccmatch.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.psoft.tccmatch.enums.StatusUsuarioSessao;
import com.psoft.tccmatch.repository.SessaoRepository;
import com.psoft.tccmatch.util.ErroSessao;

@Service
public class SessaoServiceImpl implements SessaoService {

    @Autowired
    SessaoRepository sessaoRepository;

    @Autowired
    UsuarioService usuarioService;

    public void registraSessao(UsuarioDTO usuarioDTO) {

        Usuario usuario = this.usuarioService.findByEmail(usuarioDTO.getEmail()).get();
        StatusUsuarioSessao tipoUsuario = StatusUsuarioSessao.COORDENADOR;

        if (usuario.getClass().equals(Aluno.class)) {
            tipoUsuario = StatusUsuarioSessao.ALUNO;
        } else if (usuario.getClass().equals(Professor.class)) {
            tipoUsuario = StatusUsuarioSessao.PROFESSOR;
        }

        Sessao sessao = this.getSessao();

        sessao.setUsuario(usuario);
        sessao.setStatus(tipoUsuario);
    }

    public Sessao getSessao() {
        return this.sessaoRepository.findAll().get(0);
    }

    @Override
    public boolean isAluno() {
        return this.getSessao().getStatus().equals(StatusUsuarioSessao.ALUNO);
    }

    @Override
    public boolean isProfessor() {
        return this.getSessao().getStatus().equals(StatusUsuarioSessao.PROFESSOR);
    }

    @Override
    public boolean isCoordenador() {
        return this.getSessao().getStatus().equals(StatusUsuarioSessao.COORDENADOR);
    }

    @Override
    public boolean isLogado() {
        return !this.getSessao().getStatus().equals(StatusUsuarioSessao.NAO_LOGADO);
    }

    public void sairSessao() {
        Sessao sessao = this.getSessao();
        sessao.setUsuario(null);
        sessao.setStatus(StatusUsuarioSessao.NAO_LOGADO);
    }

    public ResponseEntity<?> verificaSessao(StatusUsuarioSessao classe) {

        Sessao sessao = this.getSessao();

        if (!this.isLogado()) {
            return ErroSessao.erroUsuarioNaoLogado();
        }

        if (!sessao.getStatus().equals(classe)) {
            return ErroSessao.erroUsuarioSemPermissao();
        }

        return null;
    }

    public SessaoResponseDTO criaResponseDTO() {
        Usuario usuario = this.getSessao().getUsuario();
        return new SessaoResponseDTO(this.getSessao().getStatus(), this.usuarioService.criaOwnedResponseDTO(usuario));
    }

    @Override
    public void salvaSessao() {
        this.sessaoRepository.save(this.getSessao());
    }

}
