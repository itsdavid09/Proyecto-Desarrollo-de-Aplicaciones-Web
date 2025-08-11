package com.ProyectoWeb.service.impl;


import com.ProyectoWeb.domain.Mensaje;
import com.ProyectoWeb.service.CorreoService;
import com.ProyectoWeb.service.SoporteService;
import com.ProyectoWeb.service.UsuarioService;
import jakarta.mail.MessagingException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class SoporteServiceImpl implements SoporteService {

    @Autowired
    private CorreoService correoService;
    @Autowired
    private MessageSource messageSource;
    
    @Override
    public Model enviarCorreo(Model model, Mensaje mensaje) 
            throws MessagingException {                
        String asunto = "Mensaje de Soporte de " + mensaje.getNombre() +" "+ mensaje.getApellido();
        correoService.enviarCorreoSoporte(mensaje.getEmail(), asunto, mensaje.getCuerpo());
        return model;
    }

    
    





}