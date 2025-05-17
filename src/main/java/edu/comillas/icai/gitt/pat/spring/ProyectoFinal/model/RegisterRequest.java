package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.Date;

public record RegisterRequest(
        @NotBlank
        String nombre,
        @NotBlank
        @Email
        String email,
        // Patrón: al menos una mayúscula, una minúscula, y un número, y de longitud más de 7
        @NotBlank @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{8,}$")
        String password,

        @NotBlank
        String direccion,

        @NotNull
        Integer telefono,

        @NotNull
        LocalDate fechaNacimiento

) {
}
