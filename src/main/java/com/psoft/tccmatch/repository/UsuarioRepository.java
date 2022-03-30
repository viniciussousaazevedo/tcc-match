package com.psoft.tccmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psoft.tccmatch.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends GenericUsuarioRepository<Usuario> {

}
