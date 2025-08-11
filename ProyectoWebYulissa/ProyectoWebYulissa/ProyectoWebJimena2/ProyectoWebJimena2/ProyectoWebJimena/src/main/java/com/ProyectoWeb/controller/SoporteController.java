package com.ProyectoWeb.controller;

import com.ProyectoWeb.domain.Mensaje;
import com.ProyectoWeb.service.SoporteService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class SoporteController {

   
    @Autowired
    private SoporteService soporteService;
    
    @GetMapping("/soporte")
    public String mostrarSoporte(Model model) {
        return "soporte/soporte";
    }
    
    @PostMapping("/soporte/correo")
    public String enviarCorreo(Model model, Mensaje mensaje) 
            throws MessagingException {
             model = soporteService.enviarCorreo(model, mensaje);
             
        return "/soporte/soporte";
    }

    
}