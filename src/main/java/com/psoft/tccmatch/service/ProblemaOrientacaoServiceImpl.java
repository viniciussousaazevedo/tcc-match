package com.psoft.tccmatch.service;

import com.psoft.tccmatch.DTO.ProblemaOrientacaoDTO;
import com.psoft.tccmatch.enums.AutorProblemaOrientacaoEnum;
import com.psoft.tccmatch.model.ProblemaOrientacao;
import com.psoft.tccmatch.repository.ProblemaOrientacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProblemaOrientacaoServiceImpl implements ProblemaOrientacaoService {
	
	@Autowired
	private ProblemaOrientacaoRepository problemaOrientacaoRepository;
	
	@Autowired
	private SessaoService sessaoService;
	
	public List<ProblemaOrientacao> listarProblemas() {
		return problemaOrientacaoRepository.findAll();
	}

	public void salvarProblema(ProblemaOrientacao problemaOrientacao) {
		problemaOrientacaoRepository.save(problemaOrientacao);
	}

	@Override
	public ProblemaOrientacao criarProblema(ProblemaOrientacaoDTO problemaOrientacaoDTO) {
		AutorProblemaOrientacaoEnum autor = AutorProblemaOrientacaoEnum.ALUNO;
		if(this.sessaoService.isProfessor()) {
			autor = AutorProblemaOrientacaoEnum.PROFESSOR;
		}
		return new ProblemaOrientacao(autor,problemaOrientacaoDTO.getDescricaoProblema());
	}

	@Override
	public List<ProblemaOrientacao> findByAutor(AutorProblemaOrientacaoEnum autor) {
		return this.problemaOrientacaoRepository.findByAutor(autor);
	}



}
