package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.repository;

import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.entidad.Token;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.entidad.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepositorio extends CrudRepository<Token, String> {
    Token findByUsuario(Usuario usuario);
}
