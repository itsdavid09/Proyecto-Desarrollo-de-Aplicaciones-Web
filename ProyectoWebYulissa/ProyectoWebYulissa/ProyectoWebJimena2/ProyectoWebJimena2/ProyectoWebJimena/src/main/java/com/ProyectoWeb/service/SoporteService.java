/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoWeb.service;

import com.ProyectoWeb.domain.Mensaje;
import jakarta.mail.MessagingException;
import org.springframework.ui.Model;

public interface SoporteService {


    public Model enviarCorreo(Model model, Mensaje mensaje) throws MessagingException;
    
}