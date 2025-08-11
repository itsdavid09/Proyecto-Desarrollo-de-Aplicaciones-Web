package com.ProyectoWeb.service.impl;

import com.ProyectoWeb.domain.Usuario;
import com.ProyectoWeb.dao.UsuarioDao;
import com.ProyectoWeb.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    public Optional<Usuario> buscarPorUsuario(String usuario) {
        return usuarioDao.findByUsuario(usuario);
    }
    
@Override
    public Usuario obtenerUsuarioPorId(Long id) {
    return usuarioDao.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
}

    @Override
    public Usuario guardar(Usuario usuario) {
        return usuarioDao.save(usuario);
    }
}
