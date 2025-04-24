package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.service;

import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.FormularioContacto;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.repository.FormularioContactoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioUsuario {
    @Autowired
    private FormularioContactoRepositorio formularioContactoRepositorio;

    public FormularioContacto contactar(String nombre, String email, String mensaje) {
        FormularioContacto formularioContacto = new FormularioContacto(nombre, email, mensaje);
        return formularioContactoRepositorio.save(formularioContacto); // Devuelve el objeto guardado
    }
}
