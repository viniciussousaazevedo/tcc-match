package com.psoft.tccmatch.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroTema {
	
	static final String DECLARACAO_DE_ALUNO_E_PROFESSOR_OBRIGATORIA = "Você deve informar qual o professor e o aluno na criação do tema";

    static final String TEMA_INEXISTENTE = "O tema de id %s não existe";

    static final String TEMA_INDISPONIVEL_PARA_SOLICITAR_ORIENTACAO = "Este tema encontra-se indiponível para solicitação de orientação";

    static final String CAMPO_APENAS_PARA_COORDENADOR = "O campo de orientando e orientador é de preenchimento exclusivo do coordenador";

    static final String TEMA_NAO_PODE_SER_FINALIZADO  = "Apenas temas com Status de ACEITO podem ser Finalizados";

    static final String SEMESTRE_FINALIZACAO_MENOR_QUE_ATUAL  = "É impossível o semestre de finalização do Tema ser anterior a quando o Tema foi inciado";
	
    static final String PROFESSOR_NAO_POSSUI_TEMAS_CADASTRADOS_POR_COORDENADOR = "Você não possui Temas cadastrados pela Coordenação";

	public static ResponseEntity<CustomErrorType> erroDeclaracaoAlunoProfessorObrigatoria() {
        return new ResponseEntity<>(new CustomErrorType(String.format(ErroTema.DECLARACAO_DE_ALUNO_E_PROFESSOR_OBRIGATORIA)),
                HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<CustomErrorType> erroTemaInexistente(Long id) {
        return new ResponseEntity<>(new CustomErrorType(String.format(ErroTema.TEMA_INEXISTENTE, id)),
                HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<CustomErrorType> erroTemaIndisponivelSolicitacaoOrientacao() {
        return new ResponseEntity<>(new CustomErrorType(String.format(ErroTema.TEMA_INDISPONIVEL_PARA_SOLICITAR_ORIENTACAO)),
                HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> erroCampoParaCoordenador() {
        return new ResponseEntity<>(new CustomErrorType(String.format(ErroTema.CAMPO_APENAS_PARA_COORDENADOR)),
                HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> erroProfessorSemTemasCadastradosPorCoordenador() {
        return new ResponseEntity<>(new CustomErrorType(String.format(ErroTema.PROFESSOR_NAO_POSSUI_TEMAS_CADASTRADOS_POR_COORDENADOR)),
                HttpStatus.BAD_REQUEST);
    }

        public static ResponseEntity<?> erroTemaNaoPodeSerFinalizado() {
        return new ResponseEntity<>(new CustomErrorType(String.format(ErroTema.TEMA_NAO_PODE_SER_FINALIZADO)),
                HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> erroSemestreFinalizacaoMenorQueAtual() {
        return new ResponseEntity<>(new CustomErrorType(String.format(ErroTema.SEMESTRE_FINALIZACAO_MENOR_QUE_ATUAL)),
                HttpStatus.BAD_REQUEST);
    }
}
