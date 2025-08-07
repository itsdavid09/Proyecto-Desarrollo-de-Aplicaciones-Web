package com.ProyectoWeb.dao;

import com.ProyectoWeb.domain.Tabla;
import com.ProyectoWeb.domain.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TablaDao extends JpaRepository<Tabla, Long> {

    List<Tabla> findByUsuarioId(Long usuarioId);

    List<Tabla> findByNombreContainingIgnoreCase(String nombre);

    List<Tabla> findByUsuarioAndEstadoAndNombreContainingIgnoreCase(Usuario usuario, String estado, String nombre);

    List<Tabla> findByEstado(String estado);

@Query("SELECT COUNT(t) FROM Tabla t WHERE t.usuario.id = :usuarioId AND t.estado = 'Activo'")
int contarTablasActivasPorUsuario(@Param("usuarioId") Long usuarioId);


    @Query("SELECT t FROM Tabla t WHERE t.usuario.id = :usuarioId AND t.estado = :estado")
    List<Tabla> findByUsuarioIdAndEstado(@Param("usuarioId") Long usuarioId, @Param("estado") String estado);

    public int countByUsuarioId(Long usuarioId);

}
