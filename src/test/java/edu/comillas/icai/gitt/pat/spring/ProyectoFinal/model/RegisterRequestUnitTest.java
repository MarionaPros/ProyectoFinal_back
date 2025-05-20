package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validator;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterRequestUnitTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void testValidRequest() {
        RegisterRequest request = new RegisterRequest(
                "Carlos", "carlos@email.com", "Aa123456",
                "Calle 123", 600123456, LocalDate.of(2000, 1, 1)
        );
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidEmail() {
        RegisterRequest request = new RegisterRequest(
                "Carlos", "email-malo", "Aa123456",
                "Calle 123", 600123456, LocalDate.of(2000, 1, 1)
        );
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testWeakPassword() {
        RegisterRequest request = new RegisterRequest(
                "Carlos", "carlos@email.com", "12345678",
                "Calle 123", 600123456, LocalDate.of(2000, 1, 1)
        );
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }
}