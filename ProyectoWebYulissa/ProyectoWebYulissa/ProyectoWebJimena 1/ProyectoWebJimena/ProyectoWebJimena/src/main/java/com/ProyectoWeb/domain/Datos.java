package com.ProyectoWeb.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "datos")
public class Datos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String valor;

    @ManyToOne
    @JoinColumn(name = "tabla_id", nullable = false)
    private Tabla tabla;

    @ManyToOne
    @JoinColumn(name = "columna_id", nullable = false)
    private Columna columna;

    @Column(nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'Activo'")
    private String estado = "Activo";
}
