
package com.ProyectoWeb.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "mensaje")
public class Mensaje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String nombre;
    private String apellido;
    private String email;
    private String cuerpo;

    public Mensaje() {
    }

    public Mensaje(String nombre, String apellido, String email, String cuerpo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.cuerpo = cuerpo;
    }
    
    

}