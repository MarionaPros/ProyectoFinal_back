package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.controller;

import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.Usuario;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.service.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private ServicioUsuario servicioUsuario;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
        if (usuario == null || usuario.getEmail() == null || usuario.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Por favor, ingrese los datos correctamente.");
        }

        Usuario storedUser = servicioUsuario.getUsuarioByEmail(usuario.getEmail());

        if (storedUser == null || !storedUser.getPassword().equals(usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Bienvenido, " + storedUser.getEmail() + ". Rol: " + storedUser.getRol());
    }
}