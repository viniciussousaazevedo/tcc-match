package com.psoft.tccmatch.service;

import com.psoft.tccmatch.DTO.*;
import com.psoft.tccmatch.enums.AutorTemaEnum;
import com.psoft.tccmatch.enums.StatusTrabalhoEnum;
import com.psoft.tccmatch.model.*;
import com.psoft.tccmatch.repository.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TemaServiceImpl implements TemaService {

	@Autowired
	private TemaRepository temaRepository;

	@Autowired
	private AreaEstudoService areaEstudoService;

	@Autowired
	private ProfessorService professorService;

	@Autowired
	private AlunoService alunoService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private SessaoService sessaoService;

	@Autowired
	private TemaService temaService;
	
	public List<Tema> listarTemas() {
		return temaRepository.findAll();
	}

	public List<Tema> listarTemasProfessor(Professor professor) {
		return temaRepository.findByOrientador(professor)
				.stream()
				.filter(t -> t.getAutor() == AutorTemaEnum.PROFESSOR)
				.collect(Collectors.toList());
	}

	private List<Tema> listarTemasByAluno(Aluno aluno) {
		return temaRepository.findByOrientando(aluno);
	}

	@Override
	public List<Tema> listarTemasByAutor(AutorTemaEnum autor) {
		return this.temaRepository.findByAutor(autor);
	}

	public void salvarTema(Tema tema) {
		temaRepository.save(tema);
	}

	@Override
	public Tema criarTema(TemaDTO temaDTO) {
		List<AreaEstudo> areas = this.buscarAreas(temaDTO);

		Professor professorResponsavel = null;
		Aluno alunoResponsavel = null;

		if (temaDTO.getOrientadorId() != null) {
			professorResponsavel = this.professorService.findById(temaDTO.getOrientadorId()).get();
		}

		if (temaDTO.getOrientandoId() != null) {
			alunoResponsavel = this.alunoService.findById(temaDTO.getOrientandoId()).get();
		}

		StatusTrabalhoEnum status = StatusTrabalhoEnum.PENDENTE;
		AutorTemaEnum autor = AutorTemaEnum.COORDENADOR;

		if (this.sessaoService.isProfessor()) {
			autor = AutorTemaEnum.PROFESSOR;
		} else if (this.sessaoService.isCoordenador()) {
			status = StatusTrabalhoEnum.ACEITO;
		} else if (this.sessaoService.isAluno()) {
			autor = AutorTemaEnum.ALUNO;
		}

		if (temaDTO.getSemestre() == null) temaDTO.setSemestre("2021.1");

		Tema tema = new Tema(
				temaDTO.getTitulo(),
				temaDTO.getDescricao(),
				temaDTO.getSemestre(),
				professorResponsavel,
				alunoResponsavel,
				areas,
				status,
				autor);
		this.salvarIdTemaAreas(areas, tema);
		return tema;
	}

	private List<AreaEstudo> buscarAreas(TemaDTO temaDTO) {
		List<Long> listaIds = temaDTO.getIdAreasDeEstudo();
		List<AreaEstudo> areas = new ArrayList<>();
		for (long id : listaIds) {
			Optional<AreaEstudo> area = areaEstudoService.findById(id);
			areas.add(area.get());
		}
		return areas;
	}

	private void salvarIdTemaAreas(List<AreaEstudo> areas, Tema tema) {
		for (AreaEstudo area : areas) {
			area.getTemas().add(tema);
		}
	}

	@Override
	public TemaOwnedResponseDTO criarOwnedResponseDTO(Tema tema) {
		return new TemaOwnedResponseDTO(
				tema.getId(),
				tema.getTitulo(),
				tema.getStatus(),
				tema.getAutor());
	}

	@Override
	public List<TemaOwnedResponseDTO> gerarListaOwnedResponseDTO(List<Tema> temas) {
		List<TemaOwnedResponseDTO> temasOwnedResponse = new ArrayList<>();
		for (Tema tema : temas) {
			TemaOwnedResponseDTO temaOwnedResponse = criarOwnedResponseDTO(tema);
			temasOwnedResponse.add(temaOwnedResponse);
		}
		return temasOwnedResponse;
	}

	@Override
	public TemaResponseDTO criarResponseDTO(Tema tema, ProfessorOwnedResponseDTO orientador,
			AlunoOwnedResponseDTO orientando, List<AreaEstudo> areasEstudo) {
		List<AreaEstudoOwnedResponseDTO> areasOwnedResponse = areaEstudoService.gerarListaOwnedResponseDTO(areasEstudo);
		return new TemaResponseDTO(tema.getId(), tema.getTitulo(), tema.getDescricao(),
				tema.getSemestre(), tema.getStatus(), tema.getAutor(),
				orientador, orientando, areasOwnedResponse);
	}

	@Override
	public List<TemaResponseDTO> gerarListaResponseDTO(List<Tema> temas) {
		List<TemaResponseDTO> temasResponse = new ArrayList<>();
		for (Tema tema : temas) {
			ProfessorOwnedResponseDTO orientadorResponse = null;
			AlunoOwnedResponseDTO orientandoResponse = null;
			if (tema.hasOrientador()) {
				orientadorResponse = professorService.criarOwnedResponseDTO(tema.getOrientador());
			}
			if (tema.hasOrientando()) {
				orientandoResponse = alunoService.criarOwnedResponseDTO(tema.getOrientando());
			}
			TemaResponseDTO temaResponse = criarResponseDTO(tema, orientadorResponse, orientandoResponse,
					tema.getAreasDeEstudo());
			temasResponse.add(temaResponse);
		}
		return temasResponse;
	}

	private void removeTema(Tema tema) {
		temaRepository.delete(tema);
	}

	@Override
	public Optional<Tema> findById(Long id) {
		return this.temaRepository.findById(id);
	}

	@Override
	public List<Tema> listarTemasBySemestre(String semestre) {
		List<Tema> temasSemestre = this.temaRepository.findBySemestre(semestre);
		List<Tema> temasEmCursoOuFinalizados = new ArrayList<>();
		for (Tema tema : temasSemestre) {
			if (tema.hasOrientador() && tema.hasOrientando()) {
				temasEmCursoOuFinalizados.add(tema);
			}
		}
		return temasEmCursoOuFinalizados;
	}

	public void removeTemasByProfessor(Professor professor) {
		List<Tema> temas = this.listarTemasProfessor(professor);
		for (Tema tema : temas) {
			if (tema.hasOrientando()) {
				tema.setStatus(StatusTrabalhoEnum.PENDENTE);
				tema.setOrientador(null);
				this.salvarTema(tema);
			} else {
				this.removeTema(tema);
			}
		}
	}

	public void removeTemasByAluno(Aluno aluno) {
		List<Tema> temas = this.listarTemasByAluno(aluno);
		for (Tema tema : temas) {
			if (tema.hasOrientador()) {
				tema.setOrientando(null);
				this.salvarTema(tema);
			} else {
				this.removeTema(tema);
			}
		}
	}

	@Override
	public void notificaAlunos(Tema tema) {
		if (tema.getAutor() == AutorTemaEnum.PROFESSOR) {
			String assunto = "Novo tema de TCC cadastrado";
			String template = "O professor %s cadastrou o tema %s na área %s!";

			List<AreaEstudo> areasEstudos = areaEstudoService.listarAreasEstudo();
			for (AreaEstudo area : areasEstudos) {
				if (tema.getAreasDeEstudo().contains(area)) {
					String conteudo = String.format(template, tema.getOrientador(), tema.getTitulo(), area.toString());
					emailService.enviaEmailsAlunos(area.getAlunos(), assunto, conteudo);
				}
			}
		}
	}

	@Override
	public List<TemasSemestreByAreaDTO> listarTemasSemestreByArea(List<Tema> temasSemestre) {
		Map<Long, Set<TemaOwnedResponseDTO>> mapAreaTemas = mapearIdAreaComTemas(temasSemestre);
		List<TemasSemestreByAreaDTO> temasSemestreByAreaList = gerarListaTemasSemestreByAreaDTO(mapAreaTemas);
		return temasSemestreByAreaList;
	}

	private Map<Long, Set<TemaOwnedResponseDTO>> mapearIdAreaComTemas(List<Tema> temas) {
		Map<Long, Set<TemaOwnedResponseDTO>> mapAreaTemas = new HashMap<>();
		for (Tema tema : temas) {
			for (AreaEstudo area : tema.getAreasDeEstudo()) {
				Set<TemaOwnedResponseDTO> conjuntoTemas = mapAreaTemas.get(area.getId());
				if (conjuntoTemas == null) {
					conjuntoTemas = new HashSet<>();
				}
				TemaOwnedResponseDTO temaResponse = criarOwnedResponseDTO(tema);
				conjuntoTemas.add(temaResponse);
				mapAreaTemas.put(area.getId(), conjuntoTemas);
			}
		}
		return mapAreaTemas;
	}

	private List<TemasSemestreByAreaDTO> gerarListaTemasSemestreByAreaDTO(
			Map<Long, Set<TemaOwnedResponseDTO>> mapAreaTemas) {
		List<TemasSemestreByAreaDTO> temasSemestreByAreaList = new ArrayList<>();
		for (long idArea : mapAreaTemas.keySet()) {
			AreaEstudo area = areaEstudoService.findById(idArea).get();
			AreaEstudoOwnedResponseDTO areaResponse = areaEstudoService.criarOwnedResponseDTO(area);
			TemasSemestreByAreaDTO temasSemestreByArea = new TemasSemestreByAreaDTO(areaResponse,
					new ArrayList<TemaOwnedResponseDTO>(mapAreaTemas.get(idArea)));
			temasSemestreByAreaList.add(temasSemestreByArea);
		}
		return temasSemestreByAreaList;
	}

	public List<TemaResponseDTO> listarTemasProfessorEmCursoCadastradoPorCoordenador() {
		Professor professor = (Professor) this.sessaoService.getSessao().getUsuario();
		List<Tema> temas = temaRepository.findByOrientador(professor);
		List<Tema> temasProfessor = new ArrayList<>();
		for (Tema tema : temas) {
			if (tema.getAutor().equals(AutorTemaEnum.COORDENADOR) &&
					tema.getStatus().equals(StatusTrabalhoEnum.ACEITO)) {
				temasProfessor.add(tema);
			}
		}

		List<TemaResponseDTO> temasProfessorDTO = temaService.gerarListaResponseDTO(temasProfessor);

		return temasProfessorDTO;
	}

	public TemaResponseDTO finalizaOrientacaoTCC(Tema tema, String semestre) {
		tema.setSemestre(semestre);
		tema.setStatus(StatusTrabalhoEnum.FINALIZADO);
		sessaoService.salvaSessao();
		TemaResponseDTO temaResponseDTO = temaService.criarResponseDTO(tema, professorService.criarOwnedResponseDTO(tema.getOrientador()), alunoService.criarOwnedResponseDTO(tema.getOrientando()), tema.getAreasDeEstudo());
		return temaResponseDTO;
	}

	public Boolean verificaSemestreEhMenor(Tema tema, String semestre) {
		Boolean resposta = false;
		String semestreAtual = tema.getSemestre().replace(".", "");
		String semestreFinalizacao = semestre.replace(".", "");
		
		if (Integer.parseInt(semestreFinalizacao) < Integer.parseInt(semestreAtual)) {
			resposta = true; 
		}

		return resposta;
	}

	@Override
	public Tema criarEmailProfInteressado(Professor professor, Tema tema) {
		tema.setStatus(StatusTrabalhoEnum.ACEITO);
		tema.setOrientador(professor);
		String assunto = "Um professor tem interesse no seu TCC";
		String conteudo = String.format("O professor %s manifestou interesse no seu tema de TCC %s!",
				professor.getNome(),
				tema.getTitulo()
		);
		emailService.enviaEmail(tema.getOrientando().getEmail(), assunto, conteudo);

		return tema;
	}
}
