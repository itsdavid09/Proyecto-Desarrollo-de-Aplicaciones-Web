/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ProyectoWeb.service.impl;

import com.ProyectoWeb.service.CorreoService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class CorreoServiceImpl implements CorreoService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void enviarCorreoSoporte(
            String para,
            String asunto,
            String contenido)
            throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper
                = new MimeMessageHelper(message,
                        true);
        helper.setFrom(para);
        helper.setTo("gestiondetablas@gmail.com");
        helper.setSubject(asunto);
        helper.setText(contenido + ". Correo de contacto: " + para, true);
        mailSender.send(message);
    }
}
