/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoWeb.service;

import jakarta.mail.MessagingException;

public interface CorreoService {
    public void enviarCorreoSoporte(
            String from, 
            String asunto, 
            String contenido) 
            throws MessagingException;
}
