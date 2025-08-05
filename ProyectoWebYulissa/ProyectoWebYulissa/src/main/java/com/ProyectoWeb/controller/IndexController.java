package com.ProyectoWeb.controller;

import com.ProyectoWeb.domain.UsuarioSesion;
import com.ProyectoWeb.service.TablaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @Autowired
    private UsuarioSesion usuarioSesion;

    @Autowired
    private TablaService tablaService;

    @RequestMapping("/")
    public String paginaInicio(Model model) {
        return "index";
    }

    @RequestMapping("/planes")
    public String paginaPlanes(Model model) {
        return "planes/planes";
    }

    @RequestMapping("/cuenta")
    public String paginaCuenta(Model model) {
        var sesion = usuarioSesion.obtenerUsuarioLogueado();
        var tablasActivas = tablaService.listarTablasPorUsuario(sesion.getId()).stream().filter(tabla -> !"Inactivo".equalsIgnoreCase(tabla.getEstado())).toList();
        model.addAttribute("session", sesion);
        model.addAttribute("tablas", tablasActivas);
        return "cuenta/cuenta";
    }

}
