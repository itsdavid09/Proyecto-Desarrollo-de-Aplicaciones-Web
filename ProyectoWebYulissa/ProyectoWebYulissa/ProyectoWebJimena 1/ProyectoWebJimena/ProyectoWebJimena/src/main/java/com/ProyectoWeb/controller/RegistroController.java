package com.ProyectoWeb.controller;

import com.ProyectoWeb.domain.Registro;
import com.ProyectoWeb.service.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private RegistroService registroService;

    @GetMapping("/nuevo")
    public String mostrarFormularioDeRegistro(Model model) {
        model.addAttribute("registro", new Registro());
        return "registro/nuevo";
    }

    @PostMapping
    public String procesarRegistro(Registro registro, Model model) {
        if (registroService.existeUsuarioOCedula(registro.getUsuario(), registro.getCedula())) {
            model.addAttribute("error", "El nombre de usuario o la cédula ya existen.");
            return "registro/nuevo";
        } 
        
        // Validar contras
        else if (!registro.getContrasena().equals(registro.getConfirmarContrasena())) {
            model.addAttribute("error", "Las contraseñas no coinciden.");
            return "registro/nuevo";
        }
        
        // Default
        if (registro.getPlan() == null || registro.getPlan().isEmpty()) {
            registro.setPlan("BASE");
        }
        registroService.guardar(registro);
        model.addAttribute("success", "Usuario registrado exitosamente.");
        return "redirect:/login";
    }
}