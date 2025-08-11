package com.ProyectoWeb.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name = "usuarios")
public class Registro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "contrasena")
    private String contrasena;
    
    @Transient
    private String confirmarContrasena;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "nacimiento")
    private LocalDate nacimiento;

    @Column(name = "cedula")
    private String cedula;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "plan")
    private String plan;
    
    @Column(name = "email", unique = true)
    private String email;
    
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;

    @Column(name = "actualizado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualizado;

    public Registro() {
    }

    public Registro(Long id, String usuario, String contrasena, String confirmarContrasena, LocalDate nacimiento, String cedula, String direccion, String plan, String email, String phoneNumber, Date creado, Date actualizado) {
        this.id = id;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.confirmarContrasena = confirmarContrasena;
        this.nacimiento = nacimiento;
        this.cedula = cedula;
        this.direccion = direccion;
        this.plan = plan;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.creado = creado;
        this.actualizado = actualizado;
    }

}