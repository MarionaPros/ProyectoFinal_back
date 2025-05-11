package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestFormularioContacto {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void testEmailValido() {
        FormularioContacto form = new FormularioContacto();
        form.setNombre("Carlos");
        form.setEmail("carlos@example.com");
        form.setMensaje("Hola, necesito ayuda");

        Set<ConstraintViolation<FormularioContacto>> errores = validator.validate(form);

        assertTrue(errores.isEmpty(), "No debería haber errores para un email válido");
    }

    @Test
    void testEmailInvalido(){
        FormularioContacto formulario=new FormularioContacto();
        formulario.setNombre("Pepe");
        formulario.setEmail("direccioninvalida.es");
        formulario.setMensaje("Mensaje para prueba de email incorrecto");
        Set<ConstraintViolation<FormularioContacto>> violations=validator.validate(formulario);
        Assertions.assertEquals(1,violations.size(),"Se espera tener una única violación");
        ConstraintViolation<FormularioContacto> violacion=violations.iterator().next();
        Assertions.assertEquals("email",violacion.getPropertyPath().toString());

    }
}
