package com.psoft.tccmatch.service;

import com.psoft.tccmatch.DTO.ProfessorDTO;
import com.psoft.tccmatch.DTO.ProfessorOwnedResponseDTO;
import com.psoft.tccmatch.DTO.ProfessorResponseDTO;
import com.psoft.tccmatch.model.AreaEstudo;
import com.psoft.tccmatch.model.Professor;
import com.psoft.tccmatch.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;
 
    @Autowired
    private AreaEstudoService areaEstudoService;
    
    @Override
    public Professor cadastrarProfessor(ProfessorDTO professorDTO) {
    	List<AreaEstudo> areas = pegaAreas(professorDTO);
    	Professor professor = new Professor(
    			professorDTO.getNome(),
    			professorDTO.getEmail(),
    			professorDTO.getLaboratorios(),
    			areas,
    			professorDTO.isDisponivel(),
				professorDTO.getSenha());
    	salvaIdProfAreas(areas,professor);
    	return professor;
    }
    
    private List<AreaEstudo> pegaAreas(ProfessorDTO professorDTO){
      List<Long> listaIds = professorDTO.getIdAreasDeEstudo();
      List<AreaEstudo> areas = new ArrayList<>();
      for(long id : listaIds) {
        Optional<AreaEstudo> area = areaEstudoService.findById(id);
        areas.add(area.get());
      }
      return areas;
    }
    
    private void salvaIdProfAreas(List<AreaEstudo> areas,Professor professor) {
    	for(AreaEstudo area : areas) {
    		area.getProfessores().add(professor);
    	}
    }

    @Override
    public void salvarProfessor(Professor professor) {
        professorRepository.save(professor);
    }

    @Override
    public List<Professor> listarProfessores() {
        return professorRepository.findAll();
    }

    @Override
    public Optional<Professor> findById(long id) {
        return professorRepository.findById(id);
    }

	@Override
	public Professor adicionaAreaEstudo(Professor professor, AreaEstudo area) {
		if(!professor.getAreasDeEstudo().contains(area)) {
			professor.getAreasDeEstudo().add(area);
			area.getProfessores().add(professor);
			professorRepository.save(professor);
			areaEstudoService.salvarAreaEstudo(area);
		}
		return professor;
	}

    @Override
    public void removeProfessor(Professor professor) {
        professorRepository.delete(professor);
    }

    @Override
    public Professor atualizaProfessor(ProfessorDTO professorDTO, Professor professor) {
        List<AreaEstudo> areas = pegaAreas(professorDTO);

        professor.setNome(professorDTO.getNome());
        professor.setEmail(professorDTO.getEmail());
        professor.setLaboratorios(professorDTO.getLaboratorios());
        professor.setAreasDeEstudo(areas);
		return professor;
    }

	@Override
	public void atualizaDisponibilidade(Professor professor, boolean novaDisponibilidade) {
		professor.setDisponivel(novaDisponibilidade);
	}

	@Override
	public ProfessorResponseDTO criarResponseDTO(Professor professor, List<AreaEstudo> areasEstudo) {
		return new ProfessorResponseDTO(
				professor.getId(),
				professor.getNome(),
				professor.getEmail(),
				professor.getLaboratorios(),
				professor.isDisponivel(),
				this.areaEstudoService.gerarListaOwnedResponseDTO(professor.getAreasDeEstudo())
		);
	}

	@Override
	public ProfessorOwnedResponseDTO criarOwnedResponseDTO(Professor professor) {
		return new ProfessorOwnedResponseDTO(
				professor.getId(), 
				professor.getNome(), 
				professor.getEmail());
	}
	
	@Override
	public List<ProfessorOwnedResponseDTO> gerarListaOwnedResponseDTO(List<Professor> professores) {
		List<ProfessorOwnedResponseDTO> profsOwnedResponse = new ArrayList<>();
		for(Professor prof : professores) {
			ProfessorOwnedResponseDTO profOwnedResponse = criarOwnedResponseDTO(prof);
			profsOwnedResponse.add(profOwnedResponse);
		}
		return profsOwnedResponse;
	}

	@Override
	public List<ProfessorResponseDTO> gerarListaResponseDTO(List<Professor> professores) {
		List<ProfessorResponseDTO> profsResponse = new ArrayList<>();
        for(Professor professor : professores) {
        	ProfessorResponseDTO profResponse = criarResponseDTO(professor, professor.getAreasDeEstudo());
        	profsResponse.add(profResponse);
        }
        return profsResponse;
	}

}
