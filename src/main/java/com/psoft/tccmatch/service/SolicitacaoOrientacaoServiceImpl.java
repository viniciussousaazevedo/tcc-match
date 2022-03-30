package com.psoft.tccmatch.service;

import com.psoft.tccmatch.DTO.SolicitacaoOrientacaoDTO;
import com.psoft.tccmatch.DTO.SolicitacaoOrientacaoResponseDTO;
import com.psoft.tccmatch.enums.StatusTrabalhoEnum;
import com.psoft.tccmatch.model.*;
import com.psoft.tccmatch.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SolicitacaoOrientacaoServiceImpl implements SolicitacaoOrientacaoService {
	
	@Autowired
	private SolicitacaoOrientacaoRepository solicitacaoOrientacaoRepository;

	@Autowired
	private AlunoService alunoService;

	@Autowired
	private TemaService temaService;

	@Autowired
	private ProfessorService professorService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private UsuarioService usuarioService;

	public List<SolicitacaoOrientacao> listarSolicitacoes() {
		return solicitacaoOrientacaoRepository.findAll();
	}

	public void salvarSolicitacao(SolicitacaoOrientacao solicitacaoOrientacao) {
		solicitacaoOrientacaoRepository.save(solicitacaoOrientacao);
	}

	public SolicitacaoOrientacao criarSolicitacao(Aluno aluno, Tema tema) {
		String assunto = "Nova solicitação de orientação de TCC!";

		String conteudo =  String.format(
				"O aluno(a) %s solicitou orientação no tema %s!",
				aluno.getNome(),
				tema.getTitulo()
		);

		emailService.enviaEmail(
				tema.getOrientador().getEmail(),
				assunto,
				conteudo
		);
		return new SolicitacaoOrientacao(aluno, tema.getOrientador(), tema, StatusTrabalhoEnum.PENDENTE);
	}

	@Override
	public SolicitacaoOrientacaoResponseDTO criaResponseDTO(SolicitacaoOrientacao solicitacao) {
		return new SolicitacaoOrientacaoResponseDTO(
				solicitacao.getId(),
				this.alunoService.criarOwnedResponseDTO(solicitacao.getSolicitante()),
				this.professorService.criarOwnedResponseDTO(solicitacao.getOrientador()),
				this.temaService.criarOwnedResponseDTO(solicitacao.getTema()),
				solicitacao.getStatus(),
				solicitacao.getRespostaSolicitacao()
		);
	}

	public List<SolicitacaoOrientacao> findByOrientador(Professor professor) {
		return solicitacaoOrientacaoRepository.findByOrientador(professor);
	}

	public void aceitarSolicitacaoOrientacao(SolicitacaoOrientacao solicitacao, String mensagem) {
		solicitacao.setStatus(StatusTrabalhoEnum.ACEITO);
		solicitacao.getTema().setOrientador(solicitacao.getOrientador());
		solicitacao.getTema().setOrientando(solicitacao.getSolicitante());
		solicitacao.getTema().setStatus(StatusTrabalhoEnum.ACEITO);
		solicitacao.setRespostaSolicitacao(mensagem);

		String destinatario = usuarioService.getCoordenador().getEmail();
		String assunto = "Novo tema de TCC aceito";
		String conteudo = String.format(
				"O professor %s aceitou a solicitação de orientação do aluno(a) %s no tema %s",
				solicitacao.getOrientador().getNome(),
				solicitacao.getSolicitante().getNome(),
				solicitacao.getTema().getTitulo()
		);
		this.emailService.enviaEmail(destinatario, assunto, conteudo);
	}

	public void recusarSolicitacaoOrientacao(SolicitacaoOrientacao solicitacao, String mensagem) {
		solicitacao.setStatus(StatusTrabalhoEnum.RECUSADO);
		solicitacao.setRespostaSolicitacao(mensagem);
	}
}
