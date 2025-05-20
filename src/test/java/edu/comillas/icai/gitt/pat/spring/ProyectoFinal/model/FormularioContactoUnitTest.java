package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FormularioContactoUnitTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void testValidContacto() {
        FormularioContacto form = new FormularioContacto("Juan", "juan@test.com", "Hola!");
        Set<ConstraintViolation<FormularioContacto>> violations = validator.validate(form);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testEmptyMensaje() {
        FormularioContacto form = new FormularioContacto("Ana", "ana@test.com", "");
        Set<ConstraintViolation<FormularioContacto>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }
}
