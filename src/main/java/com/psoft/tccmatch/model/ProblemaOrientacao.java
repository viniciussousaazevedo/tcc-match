package com.psoft.tccmatch.model;

import javax.persistence.*;

import com.psoft.tccmatch.enums.AutorProblemaOrientacaoEnum;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ProblemaOrientacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Enumerated(EnumType.STRING)
    private AutorProblemaOrientacaoEnum autor; 

    private String descricaoProblema;

    public ProblemaOrientacao(AutorProblemaOrientacaoEnum autor, String descricaoProblema) {
		this.autor = autor;
		this.descricaoProblema = descricaoProblema;
	}

    @Override
    public String toString() {
        return "ProblemaOrientacao{" +
                "id=" + id +
                ", autor=" + autor.getAutor() +
                ", descricao=" + descricaoProblema + '\'' +
                '}';
    }
}
