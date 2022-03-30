package com.psoft.tccmatch.enums;

public enum AutorProblemaOrientacaoEnum {
	
	PROFESSOR("Professor"),
	ALUNO("Aluno");
	
	private String autor;
	
	AutorProblemaOrientacaoEnum(String autor){this.autor = autor;}
	
	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}
	

}
