package com.psoft.tccmatch.service;

import com.psoft.tccmatch.model.Aluno;
import com.psoft.tccmatch.model.Email;
import com.psoft.tccmatch.model.Usuario;
import com.psoft.tccmatch.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    EmailRepository emailRepository;

    @Override
    public void enviaEmail(String destinatatio, String assunto, String conteudo) {
        Email email = new Email(destinatatio, assunto, conteudo);
        emailRepository.save(email);
    }

    @Override
    public void enviaEmailsAlunos(List<Aluno> alunos, String assunto, String conteudo) {
        for (Aluno aluno : alunos) {
            this.enviaEmail(aluno.getEmail(), assunto, conteudo);
        }
    }

    @Override
    public List<Email> findByDestinatario(String destinatario) {
        return emailRepository.findByDestinatario(destinatario);
    }

}
