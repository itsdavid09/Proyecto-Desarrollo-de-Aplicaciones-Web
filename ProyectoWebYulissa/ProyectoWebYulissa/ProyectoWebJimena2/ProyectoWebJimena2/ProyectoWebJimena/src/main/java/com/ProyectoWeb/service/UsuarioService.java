package com.ProyectoWeb.service;

import com.ProyectoWeb.domain.Usuario;
import java.util.Optional;

public interface UsuarioService {
    
    Optional<Usuario> buscarPorUsuario(String usuario);
    
    Usuario obtenerUsuarioPorId(Long id);
 
    Usuario guardar(Usuario usuario);
}
