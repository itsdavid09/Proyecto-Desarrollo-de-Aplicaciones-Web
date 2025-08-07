/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoWeb.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name="pagos")
public class Carrito implements Serializable{
        
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idpagos")
    private Long idpagos;
    private String nombre;
    private String apellido;
    private String direccion;
    private String localidad;
    private String postal;
    private int cobro;

    public Carrito() {
    }

    public Carrito(String nombre, String apellido, String direccion, String localidad, String postal) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.localidad = localidad;
        this.postal = postal;
    }
    
}