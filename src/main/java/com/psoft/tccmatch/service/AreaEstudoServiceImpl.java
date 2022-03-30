package com.psoft.tccmatch.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.psoft.tccmatch.DTO.AlunoOwnedResponseDTO;
import com.psoft.tccmatch.DTO.AreaEstudoDTO;
import com.psoft.tccmatch.DTO.AreaEstudoOwnedResponseDTO;
import com.psoft.tccmatch.DTO.AreaEstudoResponseDTO;
import com.psoft.tccmatch.DTO.ProfessorOwnedResponseDTO;
import com.psoft.tccmatch.DTO.TemaOwnedResponseDTO;
import com.psoft.tccmatch.model.Aluno;
import com.psoft.tccmatch.model.AreaEstudo;
import com.psoft.tccmatch.model.Professor;
import com.psoft.tccmatch.model.Tema;
import com.psoft.tccmatch.repository.AreaEstudoRepository;

@Service
public class AreaEstudoServiceImpl implements AreaEstudoService {

	@Autowired
	private AreaEstudoRepository areaEstudoRepository;
	
	@Autowired
	private AlunoService alunoService;
	
	@Autowired
	private ProfessorService professorService;
	
	@Autowired
	private TemaService temaService;
	
	@Override
	public AreaEstudo cadastrarAreaEstudo(AreaEstudoDTO areaEstudoDTO) {
		return new AreaEstudo(areaEstudoDTO.getNome(),new ArrayList<Professor>(),new ArrayList<Tema>());
	}
	
	@Override
	public void salvarAreaEstudo(AreaEstudo areaEstudo) {
		areaEstudoRepository.save(areaEstudo);
		
	}

	@Override
	public List<AreaEstudo> listarAreasEstudo() {
		return areaEstudoRepository.findAll();
	}

	@Override
	public Optional<AreaEstudo> findById(long id) {
		return areaEstudoRepository.findById(id);
	}
  
	@Override
	public Set<Professor> getProfessoresByAreas(List<AreaEstudo> areas) {
		Set<Professor> professores = new HashSet<Professor>();
		areas.forEach(
				a -> professores.addAll(
						a.getProfessores()
						.stream()
						.filter(p -> p.isDisponivel())
						.collect(Collectors.toList())
					)
				);
		
		return professores;
	}

	@Override
	public AreaEstudoOwnedResponseDTO criarOwnedResponseDTO(AreaEstudo areaEstudo) {
		return new AreaEstudoOwnedResponseDTO(areaEstudo.getId(),areaEstudo.getNome());
	}

	@Override
	public List<AreaEstudoOwnedResponseDTO> gerarListaOwnedResponseDTO(List<AreaEstudo> areasEstudo) {
		List<AreaEstudoOwnedResponseDTO> areasOwnedResponse = new ArrayList<>();
		for(AreaEstudo area : areasEstudo) {
			AreaEstudoOwnedResponseDTO areaOwnedResponse = criarOwnedResponseDTO(area);
			areasOwnedResponse.add(areaOwnedResponse);
		}
		return areasOwnedResponse;
	}

	@Override
	public AreaEstudoResponseDTO criarResponseDTO(AreaEstudo areaEstudo, List<Professor> professores,
			List<Aluno> alunos, List<Tema> temas) {
		List<ProfessorOwnedResponseDTO> profOwnedResponses = professorService.gerarListaOwnedResponseDTO(professores);
		List<AlunoOwnedResponseDTO> alunoOwnedResponses = alunoService.gerarListaOwnedResponseDTO(alunos);
		List<TemaOwnedResponseDTO> temaOwnedResponses = temaService.gerarListaOwnedResponseDTO(temas);
		return new AreaEstudoResponseDTO(areaEstudo.getId(),areaEstudo.getNome(),profOwnedResponses,alunoOwnedResponses,temaOwnedResponses);
	}

	@Override
	public List<AreaEstudoResponseDTO> gerarListaResponseDTO(List<AreaEstudo> areasEstudo) {
		List<AreaEstudoResponseDTO> areaEstudoResponseDTOs = new ArrayList<>();
		for(AreaEstudo area : areasEstudo) {
			AreaEstudoResponseDTO areaEstudoResponseDTO = criarResponseDTO(area, area.getProfessores(), area.getAlunos(), area.getTemas());
			areaEstudoResponseDTOs.add(areaEstudoResponseDTO);
		}
		return areaEstudoResponseDTOs;
	}

	

}
