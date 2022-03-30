package com.psoft.tccmatch.repository;

import com.psoft.tccmatch.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailRepository extends JpaRepository<Email, Long> {
    List<Email> findByDestinatario(String destinatario);
}
