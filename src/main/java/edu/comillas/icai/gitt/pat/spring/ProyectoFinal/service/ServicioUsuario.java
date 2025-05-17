package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.service;

import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.entidad.Carrito;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.entidad.CarritoItem;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.entidad.Token;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.*;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.entidad.Usuario;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.repository.FormularioContactoRepositorio;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.repository.TokenRepositorio;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.repository.UsuarioRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        if(usuario==null|| !usuario.getPassword().equals(password)){
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
    nuevoUsuario.setDireccion(registro.direccion());
    nuevoUsuario.setPassword(registro.password());
    nuevoUsuario.setTelefono(registro.telefono());
    nuevoUsuario.setFechaNacimiento(registro.fechaNacimiento());

    if (registro.email().endsWith("@vibewear.com")) {
        nuevoUsuario.setRol(Role.ADMIN);
    } else {
        nuevoUsuario.setRol(Role.USUARIO);
        Carrito carrito = new Carrito();
        carrito.setUsuario(nuevoUsuario);
        nuevoUsuario.setCarrito(carrito);

    }
    usuarioRepositorio.save(nuevoUsuario);
    return new ProfileResponse(
            nuevoUsuario.getNombre(),
            nuevoUsuario.getEmail(),
            nuevoUsuario.getRol()
    );

}
    public ProfileResponse obtenerPerfil(String tokenId) {
        if (tokenId == null) return null;

        Token token = tokenRepositorio.findById(tokenId).orElse(null);
        if (token == null) return null;

        Usuario usuario = token.getUsuario();

        return new ProfileResponse(
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol()
        );
    }
    public void logout(String tokenId) {
        tokenRepositorio.deleteById(tokenId);
    }
    public void borrarCuenta(String tokenId) {
        Token token = tokenRepositorio.findById(tokenId).orElse(null);
        if (token == null) {
            throw new IllegalStateException("Sesión inválida");
        }

        Usuario usuario = token.getUsuario();

        // Eliminar el token
        tokenRepositorio.delete(token);

        // Eliminar el usuario (con su carrito si la relación está en cascada)
        usuarioRepositorio.delete(usuario);
    }


}
