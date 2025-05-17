package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model;

import jakarta.validation.constraints.NotBlank;

public record CarritoRequest (
        @NotBlank
        Long productoId,     // ID del producto externo (dummyjson)
        @NotBlank
        String nombre,
        @NotBlank
        String imagenUrl,
        @NotBlank
        double precio,
        @NotBlank
        int cantidad

)

{}

