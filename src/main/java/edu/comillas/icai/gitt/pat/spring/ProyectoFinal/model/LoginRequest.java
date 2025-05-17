package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        String email,
        @NotBlank
        String password
) {}
