package com.ProyectoWeb.dao;
import com.ProyectoWeb.domain.Registro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroDao extends JpaRepository<Registro, Long> {
}
