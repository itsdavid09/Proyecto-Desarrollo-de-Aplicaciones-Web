package com.ProyectoWeb.dao;

import com.ProyectoWeb.domain.Columna;
import com.ProyectoWeb.domain.Tabla;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnaDao extends JpaRepository<Columna, Long> {
    
    List<Columna> findByTablaId(Long tablaId);
    
    Columna findByNombreAndTablaId(String nombre, Long tablaId);

    List<Columna> findByTabla(Tabla tabla);

    public void deleteByTablaId(Long id);
}