package com.psoft.tccmatch.controller;

import com.psoft.tccmatch.DTO.ProblemaOrientacaoDTO;
import com.psoft.tccmatch.enums.AutorProblemaOrientacaoEnum;
import com.psoft.tccmatch.enums.StatusUsuarioSessao;
import com.psoft.tccmatch.model.ProblemaOrientacao;
import com.psoft.tccmatch.service.ProblemaOrientacaoService;
import com.psoft.tccmatch.service.SessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProblemaOrientacaoController {

	@Autowired
	ProblemaOrientacaoService problemaOrientacaoService;

	@Autowired
	SessaoService sessaoService;

    @RequestMapping(value = "/problemas", method = RequestMethod.GET)
    public ResponseEntity<?> listarProblemas() {
		List<ProblemaOrientacao> problemasOrientacao = problemaOrientacaoService.listarProblemas();

        return new ResponseEntity<>(problemasOrientacao, HttpStatus.OK);
    }

    @RequestMapping(value = "/problema", method = RequestMethod.POST)
	public ResponseEntity<?> criarProblemaOrientacao(@RequestBody ProblemaOrientacaoDTO problemaOrientacaoDTO) {

		ResponseEntity<?> statusSessao = sessaoService.verificaSessao(StatusUsuarioSessao.ALUNO);
		if (statusSessao != null) {
			statusSessao = sessaoService.verificaSessao(StatusUsuarioSessao.PROFESSOR);
			if(statusSessao != null) {return statusSessao;}
		}

		ProblemaOrientacao problemaOrientacao = problemaOrientacaoService.criarProblema(problemaOrientacaoDTO);
		problemaOrientacaoService.salvarProblema(problemaOrientacao);

		return new ResponseEntity<>(problemaOrientacao, HttpStatus.CREATED);
	}
    
    @RequestMapping(value = "/problemas/{autor}", method = RequestMethod.GET)
    public ResponseEntity<?> listarProblemasPorAutor(@PathVariable AutorProblemaOrientacaoEnum autor){
    	ResponseEntity<?> statusSessao = sessaoService.verificaSessao(StatusUsuarioSessao.COORDENADOR);
    	if(statusSessao != null) {return statusSessao;}
    	
    	List<ProblemaOrientacao> problemasOrientacao = problemaOrientacaoService.findByAutor(autor);
    	
    	return new ResponseEntity<>(problemasOrientacao, HttpStatus.OK);

    	
    	
    }

}
