package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.repository;

import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.FormularioContacto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormularioContactoRepositorio extends CrudRepository<FormularioContacto, Long> {
}
