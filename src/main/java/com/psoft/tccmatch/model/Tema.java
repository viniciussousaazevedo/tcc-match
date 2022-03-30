package com.psoft.tccmatch.model;

import java.util.List;
import java.util.Set;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.psoft.tccmatch.enums.AutorTemaEnum;
import com.psoft.tccmatch.enums.StatusTrabalhoEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Tema {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descricao;
    
    private String semestre;

    @Enumerated(EnumType.STRING)
    private StatusTrabalhoEnum status;
    
    @Enumerated(EnumType.STRING)
    private AutorTemaEnum autor;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Professor orientador;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Aluno orientando;
    
    @JsonIgnore
    @ManyToMany
    @JoinTable(
  		  name = "tema_area", 
  		  joinColumns = @JoinColumn(name = "tema_id"), 
  		  inverseJoinColumns = @JoinColumn(name = "area_id"))
    private List<AreaEstudo> areasDeEstudo;

    @JsonIgnore
    @OneToMany(mappedBy = "tema", fetch = FetchType.LAZY, cascade = { CascadeType.DETACH })
    private Set<SolicitacaoOrientacao> solicitacoesOrientacao;

    public Tema(
            String titulo,
            String descricao,
            String semestre,
            Professor orientador,
            Aluno orientando,
            List<AreaEstudo> areasDeEstudo,
            StatusTrabalhoEnum status,
            AutorTemaEnum autor
    ) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.semestre = semestre;
		this.orientador = orientador;
        this.orientando = orientando;
		this.areasDeEstudo = areasDeEstudo;
        this.status = status;
        this.autor = autor;
	}

    @Override
    public String toString() {
        return "Tema{" +
                "id=" + id +
                ", titulo=" + titulo +
                ", descricao=" + descricao + '\'' +
                '}';
    }

    public boolean hasOrientador() {
        return this.getOrientador() != null;
    }

    public boolean hasOrientando() {
        return this.getOrientando() != null;
    }

}
