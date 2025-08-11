/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoWeb.domain;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioSesion {

    @Autowired
    private HttpSession session;
    public Usuario obtenerUsuarioLogueado() {
        return (Usuario) session.getAttribute("usuarioLogueado");
    }
    public void establecerUsuarioLogueado(Usuario usuario) {
        session.setAttribute("usuarioLogueado", usuario);
    }
    public void cerrarSesion() {
        session.invalidate();
    }
}