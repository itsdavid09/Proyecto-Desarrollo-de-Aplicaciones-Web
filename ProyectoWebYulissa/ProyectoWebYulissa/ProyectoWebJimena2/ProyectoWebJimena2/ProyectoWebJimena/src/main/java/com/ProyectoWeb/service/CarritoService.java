/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoWeb.service;

import com.ProyectoWeb.domain.Carrito;

/**
 *
 * @author dques
 */
public interface CarritoService {   
    
    public Carrito getCarrito(Carrito carrito);
    
    public void save(Carrito carrito);
    
}
