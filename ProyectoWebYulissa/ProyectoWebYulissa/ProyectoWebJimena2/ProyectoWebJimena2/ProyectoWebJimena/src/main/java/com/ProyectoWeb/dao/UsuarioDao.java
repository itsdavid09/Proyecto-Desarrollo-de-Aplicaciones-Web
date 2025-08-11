package com.ProyectoWeb.dao;

import com.ProyectoWeb.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioDao extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsuario(String usuario);
}
