package com.psoft.tccmatch.service;

import com.psoft.tccmatch.DTO.AlunoOwnedResponseDTO;
import com.psoft.tccmatch.DTO.ProfessorOwnedResponseDTO;
import com.psoft.tccmatch.DTO.TemaDTO;
import com.psoft.tccmatch.DTO.TemaOwnedResponseDTO;
import com.psoft.tccmatch.DTO.TemaResponseDTO;
import com.psoft.tccmatch.DTO.TemasSemestreByAreaDTO;
import com.psoft.tccmatch.enums.AutorTemaEnum;
import com.psoft.tccmatch.model.Aluno;
import com.psoft.tccmatch.model.AreaEstudo;
import com.psoft.tccmatch.model.Professor;
import com.psoft.tccmatch.model.Tema;

import java.util.List;
import java.util.Optional;

public interface TemaService {
	
	List<Tema> listarTemas();

	List<Tema> listarTemasProfessor(Professor professor);

	List<TemaResponseDTO> listarTemasProfessorEmCursoCadastradoPorCoordenador();
	
	List<Tema> listarTemasByAutor(AutorTemaEnum autor);

	void salvarTema(Tema tema);
	
	Tema criarTema(TemaDTO temaDTO);
	
	TemaResponseDTO criarResponseDTO(Tema tema, ProfessorOwnedResponseDTO orientador,
			AlunoOwnedResponseDTO orientando, List<AreaEstudo> areasEstudo);

	TemaOwnedResponseDTO criarOwnedResponseDTO(Tema tema);

	List<TemaOwnedResponseDTO> gerarListaOwnedResponseDTO(List<Tema> temas);
	
	List<TemaResponseDTO> gerarListaResponseDTO(List<Tema> temas);

	Optional<Tema> findById(Long id);

	List<Tema> listarTemasBySemestre(String semestre);

	void removeTemasByProfessor(Professor professor);

	void removeTemasByAluno(Aluno aluno);

    void notificaAlunos(Tema tema);

	List<TemasSemestreByAreaDTO> listarTemasSemestreByArea(List<Tema> temasSemestre);

	TemaResponseDTO finalizaOrientacaoTCC(Tema tema, String semestre);

	Boolean verificaSemestreEhMenor(Tema tema, String semestre);
	
	Tema criarEmailProfInteressado(Professor professor, Tema tema);


}
