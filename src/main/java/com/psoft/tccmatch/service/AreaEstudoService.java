package com.psoft.tccmatch.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import com.psoft.tccmatch.DTO.AreaEstudoDTO;
import com.psoft.tccmatch.DTO.AreaEstudoOwnedResponseDTO;
import com.psoft.tccmatch.DTO.AreaEstudoResponseDTO;
import com.psoft.tccmatch.model.Aluno;
import com.psoft.tccmatch.model.AreaEstudo;
import com.psoft.tccmatch.model.Professor;
import com.psoft.tccmatch.model.Tema;


public interface AreaEstudoService {

   AreaEstudo cadastrarAreaEstudo(AreaEstudoDTO areaEstudoDTO);

    void salvarAreaEstudo(AreaEstudo areaEstudo);

    List<AreaEstudo> listarAreasEstudo();

    Optional<AreaEstudo> findById(long id);
    
    Set<Professor> getProfessoresByAreas(List<AreaEstudo> areas);
    
    AreaEstudoOwnedResponseDTO criarOwnedResponseDTO(AreaEstudo areaEstudo);
    
    AreaEstudoResponseDTO criarResponseDTO(AreaEstudo areaEstudo, List<Professor> professores,
			List<Aluno> alunos, List<Tema> temas);
       
    List<AreaEstudoOwnedResponseDTO> gerarListaOwnedResponseDTO(List<AreaEstudo> areasEstudo);

    List<AreaEstudoResponseDTO> gerarListaResponseDTO(List<AreaEstudo> areasEstudo);

}
