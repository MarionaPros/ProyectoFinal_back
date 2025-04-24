package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.controller;


import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.FormularioContacto;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.service.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private ServicioUsuario servicioUsuario;

    @PostMapping("/api/sinusuario/contacto")
    @CrossOrigin(origins = "http://localhost:63342")
    public ResponseEntity<FormularioContacto> contactar(@RequestBody FormularioContacto formularioContacto) {
        FormularioContacto guardado = servicioUsuario.contactar(
                formularioContacto.getNombre(),
                formularioContacto.getEmail(),
                formularioContacto.getMensaje()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }
}
