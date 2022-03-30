package com.psoft.tccmatch.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class AreaEstudo {

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String nome;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "areasDeEstudo")
	private List<Professor> professores;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "areasDeEstudo")
	private List<Aluno> alunos;
	
	@ManyToMany(mappedBy = "areasDeEstudo")
	private List<Tema> temas; 
	
	public AreaEstudo(String nome, List<Professor> professores, List<Tema> temas) {
		this.professores = professores;
		this.temas = temas;
		this.nome = nome;
	}

	@Override
	public String toString() {
		return nome;
	}

}
