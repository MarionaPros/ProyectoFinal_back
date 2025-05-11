package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTestFormularioContactoRepositorio {

    @Autowired
    private MockMvc mockMvc;
    @Test
    void testEnvioFormularioExitosos()throws Exception{
        String json= "{ "
                + "\"nombre\":\"Carlos\","
                + "\"email\":\"carlos@gmail.com\","
                + "\"mensaje\":\"Mensaje para la prueba del test de integración del formulario de contacto\""
                + "}";
        ;
        mockMvc.perform(post("/api/sinusuario/contacto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

    }
}
