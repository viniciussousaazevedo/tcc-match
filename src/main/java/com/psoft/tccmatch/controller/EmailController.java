package com.psoft.tccmatch.controller;

import com.psoft.tccmatch.model.Email;
import com.psoft.tccmatch.model.Professor;
import com.psoft.tccmatch.model.Sessao;
import com.psoft.tccmatch.model.Usuario;
import com.psoft.tccmatch.service.EmailService;
import com.psoft.tccmatch.service.ProfessorService;
import com.psoft.tccmatch.service.SessaoService;
import com.psoft.tccmatch.util.ErroProfessor;
import com.psoft.tccmatch.util.ErroSessao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmailController {

    @Autowired
    EmailService emailService;

    @Autowired
    ProfessorService professorService;

    @Autowired
    SessaoService sessaoService;

    @RequestMapping(value = "/email/caixa-de-entrada", method = RequestMethod.GET)
    public ResponseEntity<?> listarEmailsUsuario() {

        if (!this.sessaoService.isLogado()) return ErroSessao.erroUsuarioNaoLogado();

        Sessao sessao = this.sessaoService.getSessao();

        Usuario usuario = sessao.getUsuario();

		List<Email> emails = emailService.findByDestinatario(usuario.getEmail());

        return new ResponseEntity<List<Email>>(emails, HttpStatus.OK);
    }

}
