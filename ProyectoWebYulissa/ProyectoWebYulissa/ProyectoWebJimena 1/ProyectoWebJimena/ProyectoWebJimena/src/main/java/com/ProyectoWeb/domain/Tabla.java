package com.ProyectoWeb.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "tablas")
public class Tabla implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "tabla", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Columna> columnas;

    @Column(nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'Activo'")
    private String estado = "Activo";
}
