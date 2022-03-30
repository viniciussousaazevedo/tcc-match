package com.psoft.tccmatch.controller;

import java.util.Optional;

import com.psoft.tccmatch.DTO.SessaoResponseDTO;
import com.psoft.tccmatch.DTO.UsuarioDTO;
import com.psoft.tccmatch.enums.StatusUsuarioSessao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;


import com.psoft.tccmatch.service.UsuarioService;
import com.psoft.tccmatch.service.SessaoService;
import com.psoft.tccmatch.model.Usuario;
import com.psoft.tccmatch.util.ErroUsuario;

@RestController
@RequestMapping("/api")
public class SessaoController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    SessaoService sessaoService;

    @RequestMapping(value = "/usuario", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody UsuarioDTO usuarioDTO) {

        if (!this.sessaoService.getSessao().getStatus().equals(StatusUsuarioSessao.NAO_LOGADO)) {
            return ErroUsuario.erroUsuarioJaLogado();
        }

        Optional<Usuario> optionalUsuario = usuarioService.findByEmail(usuarioDTO.getEmail());
        if (!optionalUsuario.isPresent()) {
            return ErroUsuario.erroUsuarioNaoEncontrado(usuarioDTO.getEmail());
        }

        if (!usuarioService.verificaSenha(usuarioDTO.getEmail(), usuarioDTO.getSenha())) {
            return ErroUsuario.erroSenhaIncorreta();
        }

        sessaoService.registraSessao(usuarioDTO);
        this.sessaoService.salvaSessao();
        SessaoResponseDTO sessaoResponseDTO = this.sessaoService.criaResponseDTO();

        return new ResponseEntity<>(sessaoResponseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/usuario/", method = RequestMethod.PUT)
    public ResponseEntity<?> logout() {

        sessaoService.sairSessao();
        this.sessaoService.salvaSessao();
        return new ResponseEntity<>("Usuario deslogado com sucesso!", HttpStatus.OK);
    }

    @RequestMapping(value = "/usuario/", method = RequestMethod.GET)
    public ResponseEntity<?> getUsuario() {

        SessaoResponseDTO sessaoResponseDTO = this.sessaoService.criaResponseDTO();

        return new ResponseEntity<>(sessaoResponseDTO, HttpStatus.OK);
    }

}
