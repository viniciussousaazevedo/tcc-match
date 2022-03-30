package com.psoft.tccmatch.controller;

import org.springframework.web.bind.annotation.RequestBody;

import com.psoft.tccmatch.DTO.DecisaoProfSolicitacaoOrientacaoDTO;
import com.psoft.tccmatch.DTO.SolicitacaoOrientacaoDTO;
import com.psoft.tccmatch.DTO.SolicitacaoOrientacaoResponseDTO;
import com.psoft.tccmatch.DTO.TemaResponseDTO;
import com.psoft.tccmatch.enums.AutorTemaEnum;
import com.psoft.tccmatch.enums.StatusTrabalhoEnum;
import com.psoft.tccmatch.enums.StatusUsuarioSessao;
import com.psoft.tccmatch.model.*;
import com.psoft.tccmatch.repository.SolicitacaoOrientacaoRepository;
import com.psoft.tccmatch.service.*;
import com.psoft.tccmatch.util.ErroSolicitacaoOrientacao;
import com.psoft.tccmatch.util.ErroTema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class SolicitacaoOrientacaoController {

	@Autowired
	SolicitacaoOrientacaoService solicitacaoOrientacaoService;

	@Autowired
	SolicitacaoOrientacaoRepository solicitacaoOrientacaoRepository;

	@Autowired
	ProfessorService professorService;

	@Autowired
	TemaService temaService;

	@Autowired
	AlunoService alunoService;

	@Autowired
	SessaoService sessaoService;

    @RequestMapping(value = "/solicitacoes", method = RequestMethod.GET)
    public ResponseEntity<?> listarSolicitacoes() {
		List<SolicitacaoOrientacao> solicitacaoOrientacoes = solicitacaoOrientacaoService.listarSolicitacoes();

		List<SolicitacaoOrientacaoResponseDTO> solicitacaoOrientacoesResponse = new ArrayList<>();
		for (SolicitacaoOrientacao solicitacao : solicitacaoOrientacoes) {
			solicitacaoOrientacoesResponse.add(this.solicitacaoOrientacaoService.criaResponseDTO(solicitacao));
		}

        return new ResponseEntity<>(solicitacaoOrientacoesResponse, HttpStatus.OK);
    }

	@RequestMapping(value = "/solicitacoes/professor", method = RequestMethod.GET)
    public ResponseEntity<?> listarSolicitacoesDoProfessor() {

		ResponseEntity<?> statusSessao = sessaoService.verificaSessao(StatusUsuarioSessao.PROFESSOR);
		if (statusSessao != null) return statusSessao;

		Professor professor = (Professor) this.sessaoService.getSessao().getUsuario();

		List<SolicitacaoOrientacao> solicitacaoOrientacoes = professor.getSolicitacoesOrientacao();

		List<SolicitacaoOrientacaoResponseDTO> solicitacaoOrientacoesResponse = new ArrayList<>();
		for (SolicitacaoOrientacao solicitacao : solicitacaoOrientacoes) {
			solicitacaoOrientacoesResponse.add(this.solicitacaoOrientacaoService.criaResponseDTO(solicitacao));
		}

        return new ResponseEntity<>(solicitacaoOrientacoesResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/solicitacao/orientacao/tema/{temaId}", method = RequestMethod.POST)
	public ResponseEntity<?> criarSolicitacaoOrientacao(@PathVariable("temaId") Long temaId) {

		ResponseEntity<?> statusSessao = sessaoService.verificaSessao(StatusUsuarioSessao.ALUNO);
		if (statusSessao != null) return statusSessao;

		Optional<Tema> temaOp = this.temaService.findById(temaId);
		if (!temaOp.isPresent()) {
			return ErroTema.erroTemaInexistente(temaId);
		}

		Tema tema = temaOp.get();
		if (tema.hasOrientando()) {
			return ErroTema.erroTemaIndisponivelSolicitacaoOrientacao();
		}

		Aluno aluno = (Aluno) this.sessaoService.getSessao().getUsuario();
		SolicitacaoOrientacao solicitacao = this.solicitacaoOrientacaoService.criarSolicitacao(aluno, tema);
		this.solicitacaoOrientacaoService.salvarSolicitacao(solicitacao);
		SolicitacaoOrientacaoResponseDTO solicitacaoResponse = this.solicitacaoOrientacaoService.criaResponseDTO(solicitacao);

		return new ResponseEntity<>(solicitacaoResponse, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/solicitacao/tema/aceitar/", method = RequestMethod.PUT)
	public ResponseEntity<?> aceitarSolicitacaoOrientacao(@RequestBody DecisaoProfSolicitacaoOrientacaoDTO solicitacaoDTO) {

		ResponseEntity<?> statusSessao = sessaoService.verificaSessao(StatusUsuarioSessao.PROFESSOR);
		if (statusSessao != null) return statusSessao;

		Optional<SolicitacaoOrientacao> solicitacaoOp = solicitacaoOrientacaoRepository.findById(solicitacaoDTO.getSolicitacaoId());
		if (!solicitacaoOp.isPresent()) {
			return ErroSolicitacaoOrientacao.erroSolicitacaoNaoEncontrado(solicitacaoDTO.getSolicitacaoId());
		}

		SolicitacaoOrientacao solicitacao = solicitacaoOp.get();

		Professor professor = (Professor) this.sessaoService.getSessao().getUsuario();

		if (!solicitacao.getOrientador().equals(professor)) {
			return ErroSolicitacaoOrientacao.erroOrientadorErrado();
		}

		if (solicitacao.getStatus().equals(StatusTrabalhoEnum.ACEITO)) {
			return ErroSolicitacaoOrientacao.erroSolicitacaoJaAceita(solicitacaoDTO.getSolicitacaoId());
		}
		
		this.solicitacaoOrientacaoService.aceitarSolicitacaoOrientacao(solicitacao, solicitacaoDTO.getMensagemResposta());
		this.solicitacaoOrientacaoService.salvarSolicitacao(solicitacao);
		SolicitacaoOrientacaoResponseDTO solicitacaoResponse = this.solicitacaoOrientacaoService.criaResponseDTO(solicitacao);

		return new ResponseEntity<>(solicitacaoResponse, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/solicitacao/tema/recusar/", method = RequestMethod.PUT)
	public ResponseEntity<?> recusarSolicitacaoOrientacao(@RequestBody DecisaoProfSolicitacaoOrientacaoDTO solicitacaoDTO) {

		ResponseEntity<?> statusSessao = sessaoService.verificaSessao(StatusUsuarioSessao.PROFESSOR);
		if (statusSessao != null) return statusSessao;

		Optional<SolicitacaoOrientacao> solicitacaoOp = solicitacaoOrientacaoRepository.findById(solicitacaoDTO.getSolicitacaoId());
		if (!solicitacaoOp.isPresent()) {
			return ErroSolicitacaoOrientacao.erroSolicitacaoNaoEncontrado(solicitacaoDTO.getSolicitacaoId());
		}

		SolicitacaoOrientacao solicitacao = solicitacaoOp.get();

		Professor professor = (Professor) this.sessaoService.getSessao().getUsuario();

		if (!solicitacao.getOrientador().equals(professor)) {
			return ErroSolicitacaoOrientacao.erroOrientadorErrado();
		}

		if (solicitacao.getStatus().equals(StatusTrabalhoEnum.ACEITO)) {
			return ErroSolicitacaoOrientacao.erroSolicitacaoJaAceita(solicitacaoDTO.getSolicitacaoId());
		}
		
		this.solicitacaoOrientacaoService.recusarSolicitacaoOrientacao(solicitacao, solicitacaoDTO.getMensagemResposta());
		this.solicitacaoOrientacaoService.salvarSolicitacao(solicitacao);
		SolicitacaoOrientacaoResponseDTO solicitacaoResponse = this.solicitacaoOrientacaoService.criaResponseDTO(solicitacao);

		return new ResponseEntity<>(solicitacaoResponse, HttpStatus.CREATED);

	}
	
	@RequestMapping(value = "/solicitacao/orientar/tema/{temaId}", method = RequestMethod.POST)
	public ResponseEntity<?> orientarTemaAluno(@PathVariable("temaId") long id){
		ResponseEntity<?> statusSessao = sessaoService.verificaSessao(StatusUsuarioSessao.PROFESSOR);
		if (statusSessao != null) return statusSessao;

		Optional<Tema> temaOptional = temaService.findById(id);
		if (!temaOptional.isPresent()) {
			return ErroTema.erroTemaInexistente(id);
		}

		Tema tema = temaOptional.get();
		if (tema.hasOrientador()) {
			return ErroTema.erroTemaIndisponivelSolicitacaoOrientacao();
		}

		Professor professor = (Professor) this.sessaoService.getSessao().getUsuario();
		Tema temaAtualizado = this.temaService.criarEmailProfInteressado(professor, tema);
		SolicitacaoOrientacao solicitacao = new SolicitacaoOrientacao(
				temaAtualizado.getOrientando(), 
				temaAtualizado.getOrientador(), 
				temaAtualizado, 
				StatusTrabalhoEnum.ACEITO);
	
		solicitacao.setRespostaSolicitacao("Aceito pelo professor " + professor.getNome());
		this.solicitacaoOrientacaoService.salvarSolicitacao(solicitacao);
		SolicitacaoOrientacaoResponseDTO solicitacaoResponse = this.solicitacaoOrientacaoService.criaResponseDTO(solicitacao);
		
		return new ResponseEntity<>(solicitacaoResponse, HttpStatus.OK);
	}

}
