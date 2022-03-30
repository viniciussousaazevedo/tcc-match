package com.psoft.tccmatch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Professor extends Usuario {


	private String laboratorios;
	
	private boolean isDisponivel;

	@ManyToMany
    @JoinTable(
    		  name = "professor_area", 
    		  joinColumns = @JoinColumn(name = "professor_id"), 
    		  inverseJoinColumns = @JoinColumn(name = "area_id"))
    private List<AreaEstudo> areasDeEstudo;

    @JsonIgnore
    @OneToMany(mappedBy = "orientador", fetch = FetchType.LAZY, cascade = { CascadeType.DETACH })
    private List<SolicitacaoOrientacao> solicitacoesOrientacao;

    public Professor(String nome, String email, String laboratorios, List<AreaEstudo> areasDeEstudo, boolean isDisponivel, String senha) {
        super(nome, email, senha);
        this.laboratorios = laboratorios;
        this.areasDeEstudo = areasDeEstudo;
        this.isDisponivel = isDisponivel;
    }

    @Override
    public String toString() {
        return  super.getNome();
    }

}
