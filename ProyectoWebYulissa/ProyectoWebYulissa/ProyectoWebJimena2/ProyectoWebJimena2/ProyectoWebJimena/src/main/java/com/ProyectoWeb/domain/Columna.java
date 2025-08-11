package com.ProyectoWeb.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "columnas")
public class Columna implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    public Long getId() {
    return id;
}


    private String nombre;

    @Column(name = "tipo_dato")
    private String tipoDato;

    @ManyToOne
    @JoinColumn(name = "tabla_id")
    private Tabla tabla;
}
