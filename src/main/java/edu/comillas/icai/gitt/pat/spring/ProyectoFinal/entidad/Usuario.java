package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.entidad;

import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.Role;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable=false)
    private String email;

    @Column(unique=true, nullable=false)
    private String password;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Carrito carrito;

    @Column(nullable = false)
    private Role rol;

    @Column(nullable=false)
    private String nombre;

    @Column(nullable=false)
    private String direccion;

    @Column(nullable=false, unique=true)
    private Integer telefono;

    @Column(nullable=false)
    private LocalDate fechaNacimiento;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Token> tokens = new ArrayList<>();

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
    public String getDireccion() {return direccion;}
    public void setDireccion(String direccion) {this.direccion = direccion;}
    public Integer getTelefono() {return telefono;}
    public void setTelefono(Integer telefono) {this.telefono = telefono;}
    public LocalDate getFechaNacimiento() {return fechaNacimiento;}
    public void setFechaNacimiento(LocalDate fechaNacimiento) {this.fechaNacimiento = fechaNacimiento;}
    public Carrito getCarrito() {return carrito;}
    public void setCarrito(Carrito carrito) {this.carrito = carrito;}
    public void addToken(Token token) {
        tokens.add(token);
        token.setUsuario(this);
    }

    public void removeToken(Token token) {
        tokens.remove(token);
        token.setUsuario(null);
    }
    public List<Token> getTokens() { return tokens; }

    public void setTokens(List<Token> tokens) { this.tokens = tokens; }

}