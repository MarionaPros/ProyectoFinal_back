package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.repository;

import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.entidad.Token;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.entidad.Usuario;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Autowired
    TokenRepositorio tokenRepositorio;

    @Test
    void testGuardarUsuarioYToken() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@email.com");
        usuario.setPassword("Aa123456");
        usuario.setNombre("Carlos");
        usuario.setRol(Role.USUARIO);
        usuario.setDireccion("Calle Falsa 123");
        usuario.setTelefono(600123456);
        usuario.setFechaNacimiento(LocalDate.of(1990, 1, 1));

        usuario = usuarioRepositorio.save(usuario);

        Token token = new Token();
        token.setUsuario(usuario);
        token = tokenRepositorio.save(token);

        Assertions.assertNotNull(tokenRepositorio.findByUsuario(usuario));
    }

    @Test
    void testCascadeDelete() {
        Usuario usuario = new Usuario();
        usuario.setEmail("delete@test.com");
        usuario.setPassword("Aa123456");
        usuario.setNombre("Pepe");
        usuario.setRol(Role.USUARIO);
        usuario.setDireccion("Calle Baja 42");
        usuario.setTelefono(699999999);
        usuario.setFechaNacimiento(LocalDate.of(1995, 5, 5));

        usuario = usuarioRepositorio.save(usuario);

        Token token = new Token();
        token.setUsuario(usuario);
        usuario.getTokens().add(token);
        tokenRepositorio.save(token);

        Assertions.assertEquals(1, usuarioRepositorio.count());
        Assertions.assertEquals(1, tokenRepositorio.count());

        // When: borramos el usuario
        usuarioRepositorio.delete(usuario);

        // Then: el usuario debe estar eliminado
        Assertions.assertEquals(0, usuarioRepositorio.count());

        // Y el token asociado también debe haber sido eliminado automáticamente
        Assertions.assertEquals(0, tokenRepositorio.count());
    }
}
