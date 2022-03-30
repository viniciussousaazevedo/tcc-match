package com.psoft.tccmatch.service;

import com.psoft.tccmatch.DTO.ProblemaOrientacaoDTO;
import com.psoft.tccmatch.enums.AutorProblemaOrientacaoEnum;
import com.psoft.tccmatch.model.ProblemaOrientacao;

import java.util.List;
import java.util.Optional;

public interface ProblemaOrientacaoService {
	
	List<ProblemaOrientacao> listarProblemas();

	void salvarProblema(ProblemaOrientacao problemaOrientacao);
	
	ProblemaOrientacao criarProblema(ProblemaOrientacaoDTO problemaOrientacaoDTO);

	List<ProblemaOrientacao> findByAutor(AutorProblemaOrientacaoEnum autor);
}
