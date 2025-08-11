/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoWeb.service.impl;


import com.ProyectoWeb.dao.CarritoDao;
import com.ProyectoWeb.domain.Carrito;
import com.ProyectoWeb.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarritoServiceImpl implements CarritoService{

    @Autowired
    private CarritoDao carritoDao;
    
    @Override
    @Transactional(readOnly = true)
    public Carrito getCarrito(Carrito carrito) {
        return carritoDao.findById(carrito.getIdpagos()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Carrito carrito) {
        carritoDao.save(carrito);
    }


}

