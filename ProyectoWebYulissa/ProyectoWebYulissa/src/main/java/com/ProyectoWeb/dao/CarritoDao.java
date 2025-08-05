package com.ProyectoWeb.dao;

import com.ProyectoWeb.domain.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoDao extends JpaRepository<Carrito, Long> {
}