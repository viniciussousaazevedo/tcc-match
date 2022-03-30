package com.psoft.tccmatch.controller;

import com.psoft.tccmatch.DTO.AlunoOwnedResponseDTO;
import com.psoft.tccmatch.DTO.ProfessorOwnedResponseDTO;
import com.psoft.tccmatch.DTO.TemaDTO;
import com.psoft.tccmatch.DTO.TemaResponseDTO;
import com.psoft.tccmatch.DTO.TemasSemestreByAreaDTO;
import com.psoft.tccmatch.enums.AutorTemaEnum;
import com.psoft.tccmatch.enums.StatusTrabalhoEnum;
import com.psoft.tccmatch.enums.StatusUsuarioSessao;
import com.psoft.tccmatch.model.*;
import com.psoft.tccmatch.service.*;
import com.psoft.tccmatch.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class TemaController {

	@Autowired
	TemaService temaService;

	@Autowired
	AreaEstudoService areaEstudoService;
  
	@Autowired
	ProfessorService professorService;

	@Autowired
	AlunoService alunoService;

	@Autowired
	SessaoService sessaoService;

    @RequestMapping(value = "/temas", method = RequestMethod.GET)
    public ResponseEntity<?> listarTemas(
			@RequestParam(value = "autor", required = false) AutorTemaEnum autor,
			@RequestParam(value = "semestre", required = false) String semestre
	) {
		StatusUsuarioSessao usuarioSessao;

		if (autor == AutorTemaEnum.ALUNO) {
			usuarioSessao = StatusUsuarioSessao.PROFESSOR;
		} else if (autor == AutorTemaEnum.PROFESSOR) {
			usuarioSessao = StatusUsuarioSessao.ALUNO;
		} else {
			usuarioSessao = StatusUsuarioSessao.COORDENADOR;
		}

		ResponseEntity<?> statusSessao = sessaoService.verificaSessao(usuarioSessao);
		if (statusSessao != null) return statusSessao;

		List<Tema> temas;

		if (autor != null) {
			temas = this.temaService.listarTemasByAutor(autor);
		} else if (semestre == null) {
			temas = this.temaService.listarTemas();
		} else {
			temas = this.temaService.listarTemasBySemestre(semestre);
		}

		List<TemaResponseDTO> temasResponse = temaService.gerarListaResponseDTO(temas);

		return new ResponseEntity<>(temasResponse, HttpStatus.OK);
	}

	@RequestMapping(value = "/temas/areaEstudo", method= RequestMethod.GET)
	public ResponseEntity<?> listarTemasPorSemestreDestacandoAreas(
			@RequestParam(value = "semestre", required = true) String semestre
	){
		ResponseEntity<?> statusSessao = sessaoService.verificaSessao(StatusUsuarioSessao.COORDENADOR);
		if (statusSessao != null) return statusSessao;

		List<Tema> temasSemestre = temaService.listarTemasBySemestre(semestre);
		List<TemasSemestreByAreaDTO> temasSemestreByArea = temaService.listarTemasSemestreByArea(temasSemestre);
		return new ResponseEntity<>(temasSemestreByArea,HttpStatus.OK);
	}

    @RequestMapping(value = "/tema", method = RequestMethod.POST)
	public ResponseEntity<?> criarTema(@RequestBody TemaDTO temaDTO) {
		if (!this.sessaoService.isLogado()) return ErroSessao.erroUsuarioNaoLogado();

		Sessao sessao = this.sessaoService.getSessao();
    	Professor orientador = null;
    	Aluno orientando = null;
    	ProfessorOwnedResponseDTO orientadorResponse = null;
		AlunoOwnedResponseDTO orientandoResponse = null;

    	ResponseEntity<?> entradaInvalida = analisaPerfilOrientacao(temaDTO.getOrientadorId(), temaDTO.getOrientandoId());
		if (entradaInvalida != null) return entradaInvalida;

		if (this.sessaoService.isCoordenador()) {
			if(temaDTO.getOrientadorId() != null) {
				Optional<Professor> professorOp = this.professorService.findById(temaDTO.getOrientadorId());
				if (!professorOp.isPresent()) {
					return ErroProfessor.erroProfessorIdNaoEncontrado(temaDTO.getOrientadorId());
				}
				orientador = professorOp.get();
				orientadorResponse = professorService.criarOwnedResponseDTO(orientador);
			}
			if(temaDTO.getOrientandoId() != null) {
				Optional<Aluno> alunoOp = this.alunoService.findById(temaDTO.getOrientandoId());
				if (!alunoOp.isPresent()) {
					return ErroAluno.erroAlunoNaoEncontrado(temaDTO.getOrientandoId());
				}
				orientando = alunoOp.get();
				orientandoResponse = alunoService.criarOwnedResponseDTO(orientando);
			}
		} else if (this.sessaoService.isAluno()) {
			orientando = (Aluno) sessao.getUsuario();
			temaDTO.setOrientandoId(orientando.getId());
			orientandoResponse = alunoService.criarOwnedResponseDTO(orientando);
		} else if (this.sessaoService.isProfessor()) {
			orientador = (Professor) sessao.getUsuario();
			temaDTO.setOrientadorId(orientador.getId());
			orientadorResponse = professorService.criarOwnedResponseDTO(orientador);
		}
    	
    	for (Long areaEstudoId : temaDTO.getIdAreasDeEstudo()) {
			Optional<AreaEstudo> area = this.areaEstudoService.findById(areaEstudoId);
			
			if (!area.isPresent()) {
				return ErroAreaEstudo.erroAreaNaoEncontrada(areaEstudoId);
			}
		}   	

		Tema tema = temaService.criarTema(temaDTO);
		temaService.salvarTema(tema);
		temaService.notificaAlunos(tema);
		TemaResponseDTO temaResponse = temaService.criarResponseDTO(tema, orientadorResponse, orientandoResponse, tema.getAreasDeEstudo());
		return new ResponseEntity<>(temaResponse, HttpStatus.CREATED);
	}

	private ResponseEntity<?> analisaPerfilOrientacao(Long orientadorId, Long orientandoId) {

		if ((this.sessaoService.isAluno() || this.sessaoService.isProfessor()) &&
				(orientadorId != null || orientandoId != null)) {
			return ErroTema.erroCampoParaCoordenador();
		}
		
		if (this.sessaoService.isCoordenador() && (orientandoId == null || orientadorId == null)) {
			return ErroTema.erroDeclaracaoAlunoProfessorObrigatoria();
		}
		
		return null;

	}

	@RequestMapping(value = "/temas/professor", method = RequestMethod.GET)
    public ResponseEntity<?> listarTemasDoProfessor() {

		ResponseEntity<?> statusSessao = sessaoService.verificaSessao(StatusUsuarioSessao.PROFESSOR);
		if (statusSessao != null) return statusSessao;

		Professor professor = (Professor) this.sessaoService.getSessao().getUsuario();

		List<Tema> temas = temaService.listarTemasProfessor(professor);
		List<TemaResponseDTO> temasResponse = temaService.gerarListaResponseDTO(temas);
		
        return new ResponseEntity<>(temasResponse, HttpStatus.OK);
    }

	@RequestMapping(value = "/temas/{temaId}/finalizar", method= RequestMethod.PUT)
	public ResponseEntity<?> finalizarOrientacaoTCC(
			@RequestParam(value = "semestre", required = true) String semestre,
			@PathVariable("temaId") Long temaId
	){
		ResponseEntity<?> statusSessao = sessaoService.verificaSessao(StatusUsuarioSessao.COORDENADOR);
		if (statusSessao != null) return statusSessao;
		
		Optional<Tema> temaOp = temaService.findById(temaId);
		if (!temaOp.isPresent()) {
			return ErroTema.erroTemaInexistente(temaId);
		}

		Tema tema = temaOp.get();

		if (!tema.getStatus().equals((StatusTrabalhoEnum.ACEITO))) {
			return ErroTema.erroTemaNaoPodeSerFinalizado();
		}

		if (temaService.verificaSemestreEhMenor(tema, semestre)) {
			return ErroTema.erroSemestreFinalizacaoMenorQueAtual();
		}

		TemaResponseDTO temaResponseDTO = temaService.finalizaOrientacaoTCC(tema, semestre);

		return new ResponseEntity<>(temaResponseDTO,HttpStatus.OK);
	}

	@RequestMapping(value = "/temas/professor/coordenador", method= RequestMethod.GET)
	public ResponseEntity<?> listarTemasProfessorEmCursoCadastradoPorCoordenador(){
		ResponseEntity<?> statusSessao = sessaoService.verificaSessao(StatusUsuarioSessao.PROFESSOR);
		if (statusSessao != null) return statusSessao;

		List<TemaResponseDTO> temasProfessorDTO = temaService.listarTemasProfessorEmCursoCadastradoPorCoordenador();

		if (temasProfessorDTO.isEmpty()) {
			return ErroTema.erroProfessorSemTemasCadastradosPorCoordenador();
		}
			
		return new ResponseEntity<>(temasProfessorDTO,HttpStatus.OK);
	}
}
