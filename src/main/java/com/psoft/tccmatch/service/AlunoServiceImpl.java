package com.psoft.tccmatch.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.psoft.tccmatch.DTO.AlunoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.psoft.tccmatch.DTO.AlunoDTO;
import com.psoft.tccmatch.model.Aluno;
import com.psoft.tccmatch.model.AreaEstudo;
import com.psoft.tccmatch.repository.AlunoRepository;
import com.psoft.tccmatch.DTO.AlunoOwnedResponseDTO;
import com.psoft.tccmatch.DTO.AreaEstudoOwnedResponseDTO;

@Service
public class AlunoServiceImpl implements AlunoService {

	@Autowired
	AlunoRepository alunoRepository;
	
	@Autowired
	AreaEstudoService areaEstudoService;
	
	@Override
	public Aluno cadastraAluno(AlunoDTO alunoDTO) {
		List<AreaEstudo> areas = pegaAreas(alunoDTO);
		Aluno aluno = new Aluno(
				alunoDTO.getNome(),
				alunoDTO.getMatricula(),
				alunoDTO.getEmail(),
				alunoDTO.getPeriodoParaConclusao(),
				areas,
				alunoDTO.getSenha());
		salvaIdAlunoAreas(areas, aluno);
		return aluno;
	}
	
	private List<AreaEstudo> pegaAreas(AlunoDTO alunoDTO){
		List<Long> listaIds = alunoDTO.getIdAreasDeEstudo();
		List<AreaEstudo> areas = new ArrayList<>();
		for(long id : listaIds) {
			Optional<AreaEstudo> area = areaEstudoService.findById(id);
			areas.add(area.get());
		}
		return areas;
	}
	
	private void salvaIdAlunoAreas(List<AreaEstudo> areas, Aluno aluno) {
		for(AreaEstudo area : areas) {
    		area.getAlunos().add(aluno);
    	}
	}


	@Override
	public void salvaAluno(Aluno aluno) {
		this.alunoRepository.save(aluno);
	}

	@Override
	public Aluno atualizaAluno(AlunoDTO alunoDTO, Aluno aluno) {
		aluno.setEmail(alunoDTO.getEmail());
		aluno.setMatricula(alunoDTO.getMatricula());
		aluno.setNome(alunoDTO.getNome());
		aluno.setPeriodoParaConclusao(alunoDTO.getPeriodoParaConclusao());

		aluno.setAreasDeEstudo(new ArrayList<>());
		for (Long areaId : alunoDTO.getIdAreasDeEstudo()) {
			Optional<AreaEstudo> areaOp = this.areaEstudoService.findById(areaId);
			aluno.getAreasDeEstudo().add(areaOp.get());
		}
		
		return aluno;
	}

	@Override
	public void removeAluno(Aluno aluno) {
		this.alunoRepository.delete(aluno);		
	}

	@Override
	public Optional<Aluno> findById(Long id) {
		return this.alunoRepository.findById(id);
	}

	@Override
	public Aluno adicionaAreaEstudo(Aluno aluno, AreaEstudo areaEstudo) {
		if(!aluno.getAreasDeEstudo().contains(areaEstudo)) {
			aluno.getAreasDeEstudo().add(areaEstudo);
			areaEstudo.getAlunos().add(aluno);
			alunoRepository.save(aluno);
			areaEstudoService.salvarAreaEstudo(areaEstudo);
		}
		return aluno;
	}

	@Override
	public AlunoResponseDTO criarResponseDTO(Aluno aluno, List<AreaEstudo> areasEstudo) {
		List<AreaEstudoOwnedResponseDTO> areasOwnedResponse = areaEstudoService.gerarListaOwnedResponseDTO(areasEstudo);
		return new AlunoResponseDTO(aluno.getId(), aluno.getNome(), aluno.getMatricula(),
								    aluno.getEmail(), aluno.getPeriodoParaConclusao(), 
									areasOwnedResponse);
	}

	@Override
	public AlunoOwnedResponseDTO criarOwnedResponseDTO(Aluno aluno) {
		return new AlunoOwnedResponseDTO(
				aluno.getId(), 
				aluno.getNome(), 
				aluno.getMatricula(), 
				aluno.getEmail());
	}
	
	@Override
	public List<AlunoOwnedResponseDTO> gerarListaOwnedResponseDTO(List<Aluno> alunos) {
		List<AlunoOwnedResponseDTO> alunosOwnedResponse = new ArrayList<>();
		for(Aluno aluno : alunos) {
			AlunoOwnedResponseDTO alunoOwnedResponse = criarOwnedResponseDTO(aluno);
			alunosOwnedResponse.add(alunoOwnedResponse);
		}
		return alunosOwnedResponse;
	}

	@Override
	public List<Aluno> listarAlunos() {
		return alunoRepository.findAll();
	}

	@Override
	public List<AlunoResponseDTO> gerarListaResponseDTO(List<Aluno> alunos) {
		List<AlunoResponseDTO> alunoResponseDTOS = new ArrayList<>();

		for(Aluno aluno : alunos) {
			AlunoResponseDTO alunoResponseDTO = criarResponseDTO(aluno, aluno.getAreasDeEstudo());

			alunoResponseDTOS.add(alunoResponseDTO);
		}

		return alunoResponseDTOS;
	}
}
