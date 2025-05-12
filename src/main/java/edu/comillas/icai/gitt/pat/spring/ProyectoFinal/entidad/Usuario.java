package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.entidad;

import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.Role;
import jakarta.persistence.*;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable=false)
    private String email;

    @Column(unique=true, nullable=false)
    private String password;

    @Column(nullable = false)
    private Role rol;

    @Column(nullable=false)
    private String nombre;

    @Column(nullable=false,unique=true)
    private String dni;


    // Getters y setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRol() {
        return rol;
    }

    public void setRol(Role rol) {
        this.rol = rol;
    }

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public String getDni() {return dni;}
    public void setDni(String dni) {this.dni = dni;}

}