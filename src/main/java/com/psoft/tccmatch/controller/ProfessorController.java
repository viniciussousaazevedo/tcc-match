package com.psoft.tccmatch.controller;

import com.psoft.tccmatch.DTO.ProfessorDTO;
import com.psoft.tccmatch.DTO.ProfessorResponseDTO;
import com.psoft.tccmatch.enums.StatusUsuarioSessao;
import com.psoft.tccmatch.model.*;
import com.psoft.tccmatch.service.*;
import com.psoft.tccmatch.util.ErroAluno;
import com.psoft.tccmatch.util.ErroAreaEstudo;
import com.psoft.tccmatch.util.ErroProfessor;
import com.psoft.tccmatch.util.ErroUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class ProfessorController {

    @Autowired
    ProfessorService professorService;
    
    @Autowired
    AreaEstudoService areaEstudoService;

    @Autowired
    SessaoService sessaoService;

    @Autowired
    AlunoService alunoService;

    @Autowired
    TemaService temaService;

    @Autowired
    UsuarioService usuarioService;

    @RequestMapping(value = "/professor/", method = RequestMethod.POST)
    public ResponseEntity<?> cadastrarProfessor(@RequestBody ProfessorDTO professorDTO) {
        ResponseEntity<?> statusSessao = sessaoService.verificaSessao(StatusUsuarioSessao.COORDENADOR);
        if (statusSessao != null) return statusSessao;

    	List<Long> idsAreas = professorDTO.getIdAreasDeEstudo();
    	for(long id : idsAreas) {
    		Optional<AreaEstudo> optionalArea = areaEstudoService.findById(id);
        	if (!optionalArea.isPresent()) {
        		return ErroAreaEstudo.erroAreaNaoEncontrada(id);
            }
    	}

        if (this.usuarioService.findByEmail(professorDTO.getEmail()).isPresent()) {
            return ErroUsuario.erroEmailJaCadastrado();
        }
    	
        Professor professor =  professorService.cadastrarProfessor(professorDTO);
        professorService.salvarProfessor(professor);
        ProfessorResponseDTO profResponse = professorService.criarResponseDTO(professor, professor.getAreasDeEstudo());

        return new ResponseEntity<>(profResponse, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/professores", method = RequestMethod.GET)
    public ResponseEntity<?> listarProfessores() {
        List<Professor> professores = professorService.listarProfessores();
        List<ProfessorResponseDTO> profsResponse = professorService.gerarListaResponseDTO(professores);
        return new ResponseEntity<>(profsResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/professor/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> consultaProfessorPorId(@PathVariable("id") long id) {
        Optional<Professor> optionalProfessor = professorService.findById(id);
        if (!optionalProfessor.isPresent()) {
            return ErroProfessor.erroProfessorIdNaoEncontrado(id);
        }
        Professor professor = optionalProfessor.get();
        ProfessorResponseDTO profResponse = professorService.criarResponseDTO(professor, professor.getAreasDeEstudo());

        return new ResponseEntity<>(profResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/professor/areas", method = RequestMethod.PUT)
    public ResponseEntity<?> adicionaAreaEstudo(@RequestBody long idArea){
        ResponseEntity<?> statusSessao = sessaoService.verificaSessao(StatusUsuarioSessao.PROFESSOR);
        if (statusSessao != null) return statusSessao;
    	
    	Optional<AreaEstudo> optionalArea = areaEstudoService.findById(idArea);
    	if (!optionalArea.isPresent()) {
    		return ErroAreaEstudo.erroAreaNaoEncontrada(idArea);
        }

        Professor professor = (Professor) this.sessaoService.getSessao().getUsuario();

    	professor = professorService.adicionaAreaEstudo(professor ,optionalArea.get());
    	ProfessorResponseDTO profResponse = professorService.criarResponseDTO(professor, professor.getAreasDeEstudo());
    	  
    	return new ResponseEntity<>(profResponse, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/professor/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> atualizaProfessor(@PathVariable("id") long id, @RequestBody ProfessorDTO professorDTO) {

        ResponseEntity<?> statusSessao = sessaoService.verificaSessao(StatusUsuarioSessao.COORDENADOR);
        if (statusSessao != null) return statusSessao;

        Optional<Professor> optionalProfessor = professorService.findById(id);
        if (!optionalProfessor.isPresent()) {
            return ErroProfessor.erroProfessorIdNaoEncontrado(id);
        }
        
        for(long idArea : professorDTO.getIdAreasDeEstudo()) {
        	Optional<AreaEstudo> optionalArea = areaEstudoService.findById(idArea);
        	if (!optionalArea.isPresent()) {
        		return ErroAreaEstudo.erroAreaNaoEncontrada(idArea);
            }
        }

        if (this.usuarioService.findByEmail(professorDTO.getEmail()).isPresent()) {
            return ErroUsuario.erroEmailJaCadastrado();
        }
        
        Professor professor = professorService.atualizaProfessor(professorDTO, optionalProfessor.get());
        professorService.salvarProfessor(professor);
        
        ProfessorResponseDTO profResponse = professorService.criarResponseDTO(professor, professor.getAreasDeEstudo());
    	

        return new ResponseEntity<>(profResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/professor/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeProfessor(@PathVariable("id") long id) {
        ResponseEntity<?> statusSessao = sessaoService.verificaSessao(StatusUsuarioSessao.COORDENADOR);
        if (statusSessao != null) return statusSessao;

        Optional<Professor> optionalProfessor = professorService.findById(id);
        if (!optionalProfessor.isPresent()) {
            return ErroProfessor.erroProfessorIdNaoEncontrado(id);
        }
   
        Professor professor = optionalProfessor.get();
        ProfessorResponseDTO profResponse = professorService.criarResponseDTO(professor, professor.getAreasDeEstudo());
        temaService.removeTemasByProfessor(professor);
        professorService.removeProfessor(optionalProfessor.get());

        return new ResponseEntity<>(profResponse, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/professor/disponibilidade/", method = RequestMethod.PUT)
    public ResponseEntity<?> atualizaDisponibilidade(@RequestBody boolean novaDisponibilidade) {
        ResponseEntity<?> statusSessao = sessaoService.verificaSessao(StatusUsuarioSessao.PROFESSOR);
        if (statusSessao != null) return statusSessao;
        
        Professor professor = (Professor) this.sessaoService.getSessao().getUsuario();
        
        this.professorService.atualizaDisponibilidade(professor, novaDisponibilidade);
        this.professorService.salvarProfessor(professor);
        
        ProfessorResponseDTO profResponse = professorService.criarResponseDTO(professor, professor.getAreasDeEstudo());
    	
        return new ResponseEntity<>(profResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/aluno/listarProfessorPorAreas", method = RequestMethod.GET)
    public ResponseEntity<?> listaProfessoresPorAreasDoAluno() {
        ResponseEntity<?> statusSessao = sessaoService.verificaSessao(StatusUsuarioSessao.ALUNO);
        if (statusSessao != null) return statusSessao;

        Aluno aluno = (Aluno) this.sessaoService.getSessao().getUsuario();

        Set<Professor> professores = this.areaEstudoService.getProfessoresByAreas(aluno.getAreasDeEstudo());
        List<ProfessorResponseDTO> profsResponse = professorService.gerarListaResponseDTO(new ArrayList<>(professores));
        return new ResponseEntity<>(profsResponse, HttpStatus.OK);
    }

}
