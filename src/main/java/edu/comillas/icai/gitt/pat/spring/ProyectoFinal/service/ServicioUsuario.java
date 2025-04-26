package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.service;

import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.FormularioContacto;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.Usuario;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.repository.FormularioContactoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioUsuario {
    @Autowired
    private FormularioContactoRepositorio formularioContactoRepositorio;

    public FormularioContacto contactar(String nombre, String email, String mensaje) {
        FormularioContacto formularioContacto = new FormularioContacto(nombre, email, mensaje);
        return formularioContactoRepositorio.save(formularioContacto);
    }

    public Usuario getUsuarioByEmail(String email) {
        if ("admin@site.com".equals(email)) {
            return new Usuario("admin@site.com", "admin123", "admin");
        } else if ("comprador@site.com".equals(email)) {
            return new Usuario("comprador@site.com", "comprador123", "comprador");
        } else if ("vendedor@site.com".equals(email)) {
            return new Usuario("vendedor@site.com", "vendedor123", "vendedor");
        }
        return null;
    }
}
