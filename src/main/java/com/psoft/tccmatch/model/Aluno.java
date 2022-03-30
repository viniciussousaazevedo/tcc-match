package com.psoft.tccmatch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Aluno extends Usuario {

	
	private int matricula;
	
	private String periodoParaConclusao;
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
    		  name = "aluno_area", 
    		  joinColumns = @JoinColumn(name = "aluno_id"), 
    		  inverseJoinColumns = @JoinColumn(name = "area_id"))
    private List<AreaEstudo> areasDeEstudo;

	@JsonIgnore
	@OneToMany(mappedBy = "solicitante", fetch = FetchType.LAZY, cascade = { CascadeType.DETACH })
    private Set<SolicitacaoOrientacao> solicitacoesOrientacao;	
	
	public Aluno(String nome, int matricula, String email, String periodoParaConclusao, List<AreaEstudo> areasDeEstudo, String senha) {
		super(nome, email, senha);
		this.matricula = matricula;
		this.periodoParaConclusao = periodoParaConclusao;
		this.areasDeEstudo = areasDeEstudo;
	}

}
