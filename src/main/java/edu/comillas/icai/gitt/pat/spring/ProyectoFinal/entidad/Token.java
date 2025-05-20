package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.entidad;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Token {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    public String id;

    @ManyToOne
    @JoinColumn(name="usuario_id",referencedColumnName="id") //Tendremos la clave foránea usuario_id que se conecta con Usuario
    //mediante la clave id.
    public Usuario usuario;

    public void setUsuario(Usuario usuario) {this.usuario = usuario;}
    public Usuario getUsuario() {return usuario;}

}
