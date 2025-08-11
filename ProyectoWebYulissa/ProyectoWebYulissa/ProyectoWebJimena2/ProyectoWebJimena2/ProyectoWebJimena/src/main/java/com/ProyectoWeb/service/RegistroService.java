package com.ProyectoWeb.service;

import com.ProyectoWeb.domain.Registro;
import java.util.Optional;

public interface RegistroService {

    Registro guardar(Registro registro);

    Optional<Registro> buscarPorUsuario(String usuario);

    boolean existeUsuarioOCedula(String usuario, String cedula);

    String registrarUsuario(Registro registro);
}
