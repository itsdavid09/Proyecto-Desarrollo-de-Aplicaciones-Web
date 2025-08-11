package com.ProyectoWeb.domain; 

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String usuario;

    private String contrasena;
    private String nacimiento;
    private String cedula;
    private String direccion;
    private String plan;

    @Column(unique = true)
    private String email;
    
    @Column (name = "phone_number")
    private String phoneNumber;
    
    @Column (name = "ruta_imagen")
    private String rutaImagen;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tabla> tablas;
}