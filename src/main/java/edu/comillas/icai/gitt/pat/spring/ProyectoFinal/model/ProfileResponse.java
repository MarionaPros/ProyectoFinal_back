package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model;

public record ProfileResponse(
        String name,
        String email,
        Role role
) {
}
