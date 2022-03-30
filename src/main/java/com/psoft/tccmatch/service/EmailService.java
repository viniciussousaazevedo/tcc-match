package com.psoft.tccmatch.service;

import com.psoft.tccmatch.model.Aluno;
import com.psoft.tccmatch.model.Email;
import com.psoft.tccmatch.model.Professor;
import com.psoft.tccmatch.model.Usuario;

import java.util.List;

public interface EmailService {

    void enviaEmail(String destinatatio, String assunto, String conteudo);

    void enviaEmailsAlunos(List<Aluno> alunos, String assunto, String conteudo);

    List<Email> findByDestinatario(String destinatario);

}
