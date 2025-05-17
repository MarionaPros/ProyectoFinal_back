package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.repository;

import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.entidad.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepositorio extends CrudRepository<Usuario, Long> {
    Usuario findByEmail(String email);
}
