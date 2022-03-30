package com.psoft.tccmatch.controller;

import java.util.List;
import java.util.Optional;

import com.psoft.tccmatch.enums.StatusUsuarioSessao;
import com.psoft.tccmatch.service.ProfessorService;
import com.psoft.tccmatch.service.SessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.psoft.tccmatch.DTO.AreaEstudoDTO;
import com.psoft.tccmatch.DTO.AreaEstudoOwnedResponseDTO;
import com.psoft.tccmatch.DTO.AreaEstudoResponseDTO;
import com.psoft.tccmatch.model.AreaEstudo;
import com.psoft.tccmatch.service.AreaEstudoService;
import com.psoft.tccmatch.util.ErroAreaEstudo;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AreaEstudoController {
	
	@Autowired
	AreaEstudoService areaEstudoService;

	@Autowired
	ProfessorService professorService;

	@Autowired
	SessaoService sessaoService;
	
	@RequestMapping(value = "/areaEstudo", method = RequestMethod.POST)
	public ResponseEntity<?> cadastrarAreaEstudo(@RequestBody AreaEstudoDTO areaEstudoDTO) {

		ResponseEntity<?> statusSessao = sessaoService.verificaSessao(StatusUsuarioSessao.COORDENADOR);
		if (statusSessao != null) return statusSessao;
		
		AreaEstudo areaEstudo = areaEstudoService.cadastrarAreaEstudo(areaEstudoDTO);
		areaEstudoService.salvarAreaEstudo(areaEstudo);
		AreaEstudoOwnedResponseDTO areaResponse = areaEstudoService.criarOwnedResponseDTO(areaEstudo);
		
		return new ResponseEntity<>(areaResponse, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/areaEstudo/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarAreaEstudo(@PathVariable("id") long id ){
		Optional<AreaEstudo> opArea = areaEstudoService.findById(id);
		if(!opArea.isPresent()) {
			return ErroAreaEstudo.erroAreaNaoEncontrada(id);
		}
		AreaEstudo area = opArea.get();
		AreaEstudoResponseDTO areaResponse = areaEstudoService.criarResponseDTO(area, area.getProfessores(), area.getAlunos(), area.getTemas());
		
		return new ResponseEntity<>(areaResponse, HttpStatus.OK);
		
	}

	@RequestMapping(value = "/areasEstudo", method = RequestMethod.GET)
	public ResponseEntity<?> listarAreasEstudo() {
		List<AreaEstudo> areaEstudos = areaEstudoService.listarAreasEstudo();
		List<AreaEstudoResponseDTO> areaEstudoDTOS = areaEstudoService.gerarListaResponseDTO(areaEstudos);

		return new ResponseEntity<>(areaEstudoDTOS, HttpStatus.OK);
	}

}
