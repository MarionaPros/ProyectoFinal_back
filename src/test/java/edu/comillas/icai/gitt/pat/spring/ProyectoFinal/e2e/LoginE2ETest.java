package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.LoginRequest;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.RegisterRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginE2ETest {

        @Autowired
        private TestRestTemplate client;

        @Autowired
        private ObjectMapper objectMapper;

        private static final String NAME = "Name";
        private static final String EMAIL = "name1@email.com";
        private static final String PASS = "aaaaaaA1!";
        private static final String DIRECCION = "Calle Falsa 123";
        private static final Integer TELEFONO = 600112233;
        private static final LocalDate FECHA_NAC = LocalDate.of(2000, 1, 1);

        @BeforeEach
        void registrarUsuario() throws Exception {
            RegisterRequest request = new RegisterRequest(NAME, EMAIL, PASS, DIRECCION, TELEFONO, FECHA_NAC);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String json = objectMapper.writeValueAsString(request);
            HttpEntity<String> entity = new HttpEntity<>(json, headers);

            ResponseEntity<String> response = client.postForEntity("/api/register", entity, String.class);
            Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        }

        @Test
        public void loginOkTest() throws Exception {
            LoginRequest loginRequest = new LoginRequest(EMAIL, PASS);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String json = objectMapper.writeValueAsString(loginRequest);
            HttpEntity<String> entity = new HttpEntity<>(json, headers);

            ResponseEntity<Void> response = client.exchange(
                    "/api/login",
                    HttpMethod.POST,
                    entity,
                    Void.class
            );

            Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
            String cookie = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
            Assertions.assertNotNull(cookie);
            Assertions.assertTrue(cookie.contains("session="));
        }
}


