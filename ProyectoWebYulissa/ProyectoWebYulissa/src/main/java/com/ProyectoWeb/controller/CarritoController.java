
package com.ProyectoWeb.controller;

import com.ProyectoWeb.domain.UsuarioSesion;
import com.ProyectoWeb.service.CarritoService;
import com.ProyectoWeb.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioSesion usuarioSesion;

    @GetMapping("/oro")
    public String mostrarFormularioDeRegistro(Model model) {
        model.addAttribute("precio", 15);
        model.addAttribute("plan", "ORO");
        return "carrito/pago";
    }
    
    @GetMapping("/platino")
    public String mostrarFormularioDeRegistro2(Model model) {
        model.addAttribute("precio", 30);
        model.addAttribute("plan", "PLATINO");
        return "carrito/pago";
    }
   
    @PostMapping("/guardar")
    public String carritoGuardar(@RequestParam String nuevoPlan, Model model) {
        // Obtener el usuario logueado
        var usuario = usuarioSesion.obtenerUsuarioLogueado();

        // Actualizar el plan del usuario
        usuario.setPlan(nuevoPlan); // Asignar el nuevo plan
        usuarioService.guardar(usuario); // Guardar el usuario con el nuevo plan

        // Actualizar la sesión con el usuario modificado
        usuarioSesion.establecerUsuarioLogueado(usuario);

        // Redirigir a la página de cuenta después del pago
        return "redirect:/cuenta";
    }
}