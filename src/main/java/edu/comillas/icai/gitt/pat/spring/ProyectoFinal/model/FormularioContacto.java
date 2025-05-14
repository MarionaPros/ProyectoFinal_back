package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class FormularioContacto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotBlank(message="El nombre no puede estar vacío")
    String nombre;
    @Email
    @NotBlank(message="El email no puede estar vacío")
    String email;
    @NotBlank(message="El mensaje no puede estar vacío")
    String mensaje;
    public FormularioContacto() {
    }

    public FormularioContacto(String nombre, String email, String mensaje) {
        this.nombre = nombre;
        this.email = email;
        this.mensaje = mensaje;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public String getEmail() {
        return email;
    }
    public String getMensaje() {
        return mensaje;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}


