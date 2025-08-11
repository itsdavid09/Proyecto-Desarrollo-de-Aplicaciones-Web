package com.ProyectoWeb.dao;

import com.ProyectoWeb.domain.Columna;
import com.ProyectoWeb.domain.Datos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DatosDao extends JpaRepository<Datos, Long> {

    List<Datos> findByTablaId(Long tablaId);

    List<Datos> findByColumnaId(Long columnaId);

    Datos findByColumnaAndTablaId(Columna columna, Long tablaId);

    public void deleteByColumnaId(Long id);

    @Query("SELECT d FROM Datos d WHERE d.tabla.id = :tablaId AND d.estado = 'Activo'")
    List<Datos> findByTablaIdAndEstadoActivo(@Param("tablaId") Long tablaId);
    
    List<Datos> findByTablaIdAndEstado(Long tablaId, String estado);

    List<Datos> findByColumnaIdAndEstado(Long columnaId, String estado);

    
}
