package com.psoft.tccmatch.enums;

public enum AutorTemaEnum {

	PROFESSOR("Professor"),
	ALUNO("Aluno"),
	COORDENADOR("Coordenador");
	
	private String autor;

	AutorTemaEnum(String autor) {this.autor = autor;} 
	
	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}
	
}
