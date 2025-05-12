package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.service;

import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.entidad.Token;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.FormularioContacto;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.entidad.Usuario;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.ProfileResponse;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.RegisterRequest;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.Role;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.repository.FormularioContactoRepositorio;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.repository.TokenRepositorio;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.repository.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.Role.ADMIN;
import static edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.Role.USUARIO;

@Service
public class ServicioUsuario {
    @Autowired
    private FormularioContactoRepositorio formularioContactoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private TokenRepositorio tokenRepositorio;

    public FormularioContacto contactar(String nombre, String email, String mensaje) {
        FormularioContacto formularioContacto = new FormularioContacto(nombre, email, mensaje);
        return formularioContactoRepositorio.save(formularioContacto);
    }
    public Token login(String email,String password){
        Usuario usuario= usuarioRepositorio.findByEmail(email);
        if(usuario==null){
            return null;
        }

        Token token= tokenRepositorio.findByUsuario(usuarioRepositorio.findByEmail(email));
        if(token!=null){
            return token;
        }
        token =new Token();
        token.setUsuario(usuarioRepositorio.findByEmail(email));
        tokenRepositorio.save(token);
        return token;
    }
public ProfileResponse register(RegisterRequest registro) {
    if (registro == null) {
        return null;
    }
    Usuario nuevoUsuario = new Usuario();
    nuevoUsuario.setEmail(registro.email());
    nuevoUsuario.setNombre(registro.nombre());
    nuevoUsuario.setDni(registro.dni());
    nuevoUsuario.setPassword(registro.password());

    if (registro.email().endsWith("@vibewear.com")) {
        nuevoUsuario.setRol(Role.ADMIN);
    } else {
        nuevoUsuario.setRol(Role.USUARIO);
    }
    usuarioRepositorio.save(nuevoUsuario);
    return new ProfileResponse(
            nuevoUsuario.getNombre(),
            nuevoUsuario.getEmail(),
            nuevoUsuario.getRol()
    );

}

    /*public Usuario buscarUsuarioExistente(String email,String contraseña){
        return usuarioRepositorio.findByEmail(email);
    }
    public Usuario getUsuarioByEmail(String email) {
        if (email.endsWith("@vibewear.com")) {
            return new Usuario("admin@vibewear.com", "admin123", "admin");
        } else if ("comprador@site.com".equals(email)) {
            return new Usuario("comprador@site.com", "comprador123", "comprador");
        } else if ("vendedor@site.com".equals(email)) {
            return new Usuario("vendedor@site.com", "vendedor123", "vendedor");
        }
        return null;
    }*/
}
