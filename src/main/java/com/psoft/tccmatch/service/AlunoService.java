package com.psoft.tccmatch.service;

import java.util.List;
import java.util.Optional;

import com.psoft.tccmatch.DTO.AlunoDTO;
import com.psoft.tccmatch.DTO.AlunoOwnedResponseDTO;
import com.psoft.tccmatch.DTO.AlunoResponseDTO;
import com.psoft.tccmatch.model.Aluno;
import com.psoft.tccmatch.model.AreaEstudo;


public interface AlunoService {
	
	Aluno cadastraAluno(AlunoDTO alunoDTO);
	
	void salvaAluno(Aluno aluno);
	
	Aluno atualizaAluno(AlunoDTO alunoDTO, Aluno aluno);
	
	void removeAluno(Aluno aluno);
	
	Optional<Aluno> findById(Long id);

	Aluno adicionaAreaEstudo(Aluno aluno, AreaEstudo areaEstudo);
	
	AlunoResponseDTO criarResponseDTO(Aluno aluno, List<AreaEstudo> areasEstudo);

	AlunoOwnedResponseDTO criarOwnedResponseDTO(Aluno aluno);

	List<AlunoOwnedResponseDTO> gerarListaOwnedResponseDTO(List<Aluno> alunos);

	List<Aluno> listarAlunos();

	List<AlunoResponseDTO> gerarListaResponseDTO(List<Aluno> alunos);

}
