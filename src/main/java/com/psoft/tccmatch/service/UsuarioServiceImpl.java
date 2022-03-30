package com.psoft.tccmatch.service;

import java.util.List;
import java.util.Optional;

import com.psoft.tccmatch.DTO.UsuarioOwnedResponseDTO;
import com.psoft.tccmatch.model.Coordenador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psoft.tccmatch.model.Usuario;
import com.psoft.tccmatch.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return this.usuarioRepository.findByEmail(email);
    }

    public Boolean verificaSenha(String email, String senha) {

        Optional<Usuario> usuarioOpcional = this.usuarioRepository.findByEmail(email);
        Boolean resposta = false;

        if (usuarioOpcional.isPresent() && usuarioOpcional.get().getSenha().equals(senha)) {
            resposta = true;
        }

        return resposta;
    }

    @Override
    public UsuarioOwnedResponseDTO criaOwnedResponseDTO(Usuario usuario) {
        return (usuario == null ? null : new UsuarioOwnedResponseDTO(usuario.getId(), usuario.getEmail(), usuario.getNome()));
    }

    @Override
    public Coordenador getCoordenador() {
        Coordenador coordenador = null;
        List<Usuario> usuarios = this.usuarioRepository.findAll();
        for (Usuario usuario: usuarios) {
            if (usuario.getClass() == Coordenador.class) {
                coordenador = (Coordenador) usuario;
            }
        }

        return coordenador;
    }


}