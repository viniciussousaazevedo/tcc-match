package com.psoft.tccmatch.repository;

import com.psoft.tccmatch.enums.AutorTemaEnum;
import com.psoft.tccmatch.model.Aluno;
import com.psoft.tccmatch.model.Professor;
import com.psoft.tccmatch.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemaRepository extends JpaRepository<Tema, Long>{
	
    List<Tema> findByTitulo(String titulo);
    
    List<Tema> findByOrientador(Professor professor);
    
    List<Tema> findByAutor(AutorTemaEnum autor);
    
    List<Tema> findBySemestre(String semestre);

    List<Tema> findByOrientando(Aluno aluno);

}
