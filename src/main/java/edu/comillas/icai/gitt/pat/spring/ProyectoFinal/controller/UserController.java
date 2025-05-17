package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.controller;


import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.entidad.Token;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.entidad.Usuario;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.FormularioContacto;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.LoginRequest;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.ProfileResponse;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.RegisterRequest;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.repository.TokenRepositorio;
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

    @Autowired
    private TokenRepositorio tokenRepositorio;

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

    @CrossOrigin(origins = "http://localhost:63342", allowCredentials = "true")
    @PostMapping("/api/login")
    public ResponseEntity<ProfileResponse> login(@Valid @RequestBody LoginRequest credenciales) {
        Token tokenSesion = servicioUsuario.login(credenciales.email(), credenciales.password());

        if (tokenSesion == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        ResponseCookie session = ResponseCookie
                .from("session", tokenSesion.id)
                .httpOnly(true)
                .path("/")
                .sameSite("Strict")
                .build();

        Usuario usuario = tokenSesion.getUsuario();
        ProfileResponse perfil = new ProfileResponse(
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE, session.toString())
                .body(perfil);
    }


    @PostMapping("/api/register")
    @CrossOrigin(origins = "*")
    public ResponseEntity<ProfileResponse> register(@Valid @RequestBody RegisterRequest register){
        try{
            ProfileResponse profile = servicioUsuario.register(register);
            return ResponseEntity.status(HttpStatus.CREATED).body(profile);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/perfil")
    @CrossOrigin(origins = "http://localhost:63342", allowCredentials = "true")
    public ResponseEntity<ProfileResponse> obtenerPerfil(@CookieValue(value = "session", required = false) String tokenId) {
        ProfileResponse perfil = servicioUsuario.obtenerPerfil(tokenId);

        if (perfil == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(perfil);
    }

    @PostMapping("/api/logout")
    @CrossOrigin(origins = "http://localhost:63342", allowCredentials = "true")
    public ResponseEntity<?> logout(@CookieValue(value = "session", required = false) String tokenId) {
        if (tokenId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No hay sesión activa.");
        }

        servicioUsuario.logout(tokenId);

        // Eliminar cookie del navegador
        ResponseCookie cookie = ResponseCookie
                .from("session", "")
                .path("/")
                .httpOnly(true)
                .maxAge(0)
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Sesión cerrada correctamente");
    }
    @DeleteMapping("/api/borrarCuenta")
    @CrossOrigin(origins = "http://localhost:63342", allowCredentials = "true")
    public ResponseEntity<?> borrarCuenta(@CookieValue(value = "session", required = false) String tokenId) {
        if (tokenId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No hay sesión activa.");
        }

        try {
            servicioUsuario.borrarCuenta(tokenId);

            // Eliminar cookie
            ResponseCookie cookie = ResponseCookie
                    .from("session", "")
                    .path("/")
                    .httpOnly(true)
                    .maxAge(0)
                    .sameSite("Strict")
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body("Cuenta eliminada correctamente");

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la cuenta");
        }
    }


}

