package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.entidad;

import jakarta.persistence.*;

@Entity
public class CarritoItem {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        private Carrito carrito;

        private Long productoApiId;
        private String nombre;
        private String imagenUrl;
        private double precio;
        private int cantidad;

        public Carrito getCarrito() {return carrito;}
        public void setCarrito(Carrito carrito) {this.carrito = carrito;}
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public Long getProductoApiId() {return productoApiId;}
    public void setProductoApiId(Long productoApiId) {this.productoApiId = productoApiId;}
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public String getImagenUrl() {return imagenUrl;}
    public void setImagenUrl(String imagenUrl) {this.imagenUrl = imagenUrl;}
    public double getPrecio() {return precio;}
    public void setPrecio(double precio) {this.precio = precio;}
    public int getCantidad() {return cantidad;}
    public void setCantidad(int cantidad) {this.cantidad = cantidad;}

}


