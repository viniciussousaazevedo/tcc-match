package com.psoft.tccmatch.controller;

import java.util.List;
import java.util.Optional;

import com.psoft.tccmatch.enums.StatusUsuarioSessao;
import com.psoft.tccmatch.service.*;
import com.psoft.tccmatch.util.ErroUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.psoft.tccmatch.DTO.AlunoDTO;
import com.psoft.tccmatch.DTO.AlunoResponseDTO;
import com.psoft.tccmatch.model.Aluno;
import com.psoft.tccmatch.model.AreaEstudo;
import com.psoft.tccmatch.util.ErroAluno;
import com.psoft.tccmatch.util.ErroAreaEstudo;

@RestController
@RequestMapping("/api")
public class AlunoController {

	@Autowired
	AlunoService alunoService;
	
	@Autowired
	AreaEstudoService areaEstudoService;

	@Autowired
	SessaoService sessaoService;

	@Autowired
	TemaService temaService;

	@Autowired
	UsuarioService usuarioService;
	
	@RequestMapping(value = "/aluno/", method = RequestMethod.POST)
	public ResponseEntity<?> cadastraAluno(@RequestBody AlunoDTO alunoDTO) {

		ResponseEntity<?> statusSessao = sessaoService.verificaSessao(StatusUsuarioSessao.COORDENADOR);
		if (statusSessao != null) return statusSessao;
		
		for (Long areaEstudoId : alunoDTO.getIdAreasDeEstudo()) {
			Optional<AreaEstudo> area = this.areaEstudoService.findById(areaEstudoId);
			
			if (!area.isPresent()) {
				return ErroAreaEstudo.erroAreaNaoEncontrada(areaEstudoId);
			}
		}

		if (this.usuarioService.findByEmail(alunoDTO.getEmail()).isPresent()) {
			return ErroUsuario.erroEmailJaCadastrado();
		}
		
		Aluno aluno = this.alunoService.cadastraAluno(alunoDTO);
		this.alunoService.salvaAluno(aluno);
		AlunoResponseDTO alunoResponse = alunoService.criarResponseDTO(aluno, aluno.getAreasDeEstudo());
		
		return new ResponseEntity<>(alunoResponse, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/aluno/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> atualizaAluno(@PathVariable("id") Long id, @RequestBody AlunoDTO alunoDTO) {

		ResponseEntity<?> statusSessao = sessaoService.verificaSessao(StatusUsuarioSessao.COORDENADOR);
		if (statusSessao != null) return statusSessao;

		Optional<Aluno> alunoOp = this.alunoService.findById(id);
		
		if (!alunoOp.isPresent()) {
			return ErroAluno.erroAlunoNaoEncontrado(id);
		}

		if (this.usuarioService.findByEmail(alunoDTO.getEmail()).isPresent()) {
			return ErroUsuario.erroEmailJaCadastrado();
		}

		for (Long areaEstudoId : alunoDTO.getIdAreasDeEstudo()) {
			Optional<AreaEstudo> area = this.areaEstudoService.findById(areaEstudoId);

			if (!area.isPresent()) return ErroAreaEstudo.erroAreaNaoEncontrada(areaEstudoId);
		}
		
		Aluno aluno = this.alunoService.atualizaAluno(alunoDTO, alunoOp.get());
		this.alunoService.salvaAluno(aluno);
		AlunoResponseDTO alunoResponse = alunoService.criarResponseDTO(aluno, aluno.getAreasDeEstudo());
		
		return new ResponseEntity<>(alunoResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/aluno/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeAluno(@PathVariable("id") Long id) {

		ResponseEntity<?> statusSessao = sessaoService.verificaSessao(StatusUsuarioSessao.COORDENADOR);
		if (statusSessao != null) return statusSessao;

		Optional<Aluno> alunoOp = this.alunoService.findById(id);
		if (!alunoOp.isPresent()) {
			return ErroAluno.erroAlunoNaoEncontrado(id);
		}

		Aluno aluno = alunoOp.get();
		AlunoResponseDTO alunoResponse = alunoService.criarResponseDTO(aluno, aluno.getAreasDeEstudo());
		this.temaService.removeTemasByAluno(aluno);
		this.alunoService.removeAluno(aluno);

		return new ResponseEntity<>(alunoResponse, HttpStatus.OK);
	}

	@RequestMapping(value = "/aluno/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultaAlunoPorId(@PathVariable("id") long id){
		Optional<Aluno> alunoOp = this.alunoService.findById(id);

		if (!alunoOp.isPresent()) {
			return ErroAluno.erroAlunoNaoEncontrado(id);
		}

		Aluno aluno = alunoOp.get();
		AlunoResponseDTO alunoResponse = alunoService.criarResponseDTO(aluno, aluno.getAreasDeEstudo());

		return new ResponseEntity<>(alunoResponse,HttpStatus.OK);

	}
	
	@RequestMapping(value = "/aluno/areas", method = RequestMethod.PUT)
	public ResponseEntity<?> adicionaAreaEstudo(@RequestBody long idArea){

		ResponseEntity<?> statusSessao = sessaoService.verificaSessao(StatusUsuarioSessao.ALUNO);
		if (statusSessao != null) return statusSessao;

		Aluno aluno = (Aluno) this.sessaoService.getSessao().getUsuario();
		
		Optional<AreaEstudo> areaOp = areaEstudoService.findById(idArea);
    	if (!areaOp.isPresent()) {
    		return ErroAreaEstudo.erroAreaNaoEncontrada(idArea);
        }
		aluno = alunoService.adicionaAreaEstudo(aluno,areaOp.get());
		AlunoResponseDTO alunoResponse = alunoService.criarResponseDTO(aluno, aluno.getAreasDeEstudo());
		
		return new ResponseEntity<>(alunoResponse,HttpStatus.OK);
	}

	@RequestMapping(value = "/alunos", method = RequestMethod.GET)
	public ResponseEntity<?> listarAlunos() {
		List<Aluno> alunos = alunoService.listarAlunos();

		return new ResponseEntity<>(alunoService.gerarListaResponseDTO(alunos), HttpStatus.OK);
	}

}
