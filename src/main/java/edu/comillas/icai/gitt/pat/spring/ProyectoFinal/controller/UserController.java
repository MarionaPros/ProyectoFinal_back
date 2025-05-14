package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.controller;


import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.entidad.Token;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.entidad.Usuario;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.FormularioContacto;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.LoginRequest;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.ProfileResponse;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.RegisterRequest;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.service.ServicioUsuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserController {
    @Autowired
    private ServicioUsuario servicioUsuario;

    @PostMapping("/api/sinusuario/contacto")
    @CrossOrigin(origins = "*")
    //@CrossOrigin(origins = "http://localhost:63342")
    public ResponseEntity<FormularioContacto> contactar(@RequestBody FormularioContacto formularioContacto) {
        FormularioContacto guardado = servicioUsuario.contactar(
                formularioContacto.getNombre(),
                formularioContacto.getEmail(),
                formularioContacto.getMensaje()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PostMapping("/api/login")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest credenciales) {
        Token tokenSesion = servicioUsuario.login(credenciales.email(), credenciales.password());

        if (tokenSesion == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED); //Si el token es nulo
        //quiere decir que dichas credenciales no se encuentran en la base de datos y, por tanto, la persona
        //no está autorizada a entrar.
        ResponseCookie session = ResponseCookie
                .from("session", tokenSesion.id)
                .httpOnly(true)
                .path("/")
                .sameSite("Strict")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.SET_COOKIE, session.toString()).build();
    }

    @PostMapping("/api/register")
    @CrossOrigin(origins = "*")
    public ProfileResponse register(@Valid @RequestBody RegisterRequest register){
        try{
            return servicioUsuario.register(register);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/api/logout")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Void> logout(@RequestHeader(HttpHeaders.COOKIE) String cookie) {
        if (servicioUsuario.logout(cookie)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.SET_COOKIE, "session=; Path=/; Max-Age=0; HttpOnly")
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "La sesión ha expirado o el usuario no ha iniciado sesión.");
        }
    }

    @DeleteMapping("/api/deleteAccount")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Void> deleteAccount(@RequestHeader(HttpHeaders.COOKIE) String cookie) {
        boolean success = servicioUsuario.deleteAccount(cookie);
        if (success) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "La cuenta no se puede eliminar. Asegúrese de haber iniciado sesión.");
        }
    }
}
