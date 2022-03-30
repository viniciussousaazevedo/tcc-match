package com.psoft.tccmatch.service;

import com.psoft.tccmatch.DTO.ProfessorDTO;
import com.psoft.tccmatch.DTO.ProfessorOwnedResponseDTO;
import com.psoft.tccmatch.DTO.ProfessorResponseDTO;
import com.psoft.tccmatch.model.AreaEstudo;
import com.psoft.tccmatch.model.Professor;
import java.util.List;
import java.util.Optional;

public interface ProfessorService {

    Professor cadastrarProfessor(ProfessorDTO professorDTO);

    void salvarProfessor(Professor professor);

    List<Professor> listarProfessores();

    Optional<Professor> findById(long id);

	Professor adicionaAreaEstudo(Professor professor, AreaEstudo areaEstudo);

    void removeProfessor(Professor professor);

    Professor atualizaProfessor(ProfessorDTO professorDTO, Professor professor);
    
    void atualizaDisponibilidade(Professor professor, boolean novaDisponibilidade);

    ProfessorResponseDTO criarResponseDTO(Professor professor, List<AreaEstudo> areasEstudo);

    ProfessorOwnedResponseDTO criarOwnedResponseDTO(Professor professor);
   
	List<ProfessorOwnedResponseDTO> gerarListaOwnedResponseDTO(List<Professor> professores);

	List<ProfessorResponseDTO> gerarListaResponseDTO(List<Professor> professores);
}
