package com.ProyectoWeb.controller;

import com.ProyectoWeb.domain.Usuario;
import com.ProyectoWeb.domain.UsuarioSesion;
import com.ProyectoWeb.service.UsuarioService;
import com.ProyectoWeb.service.FirebaseStorageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioSesion usuarioSesion;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "/login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String usuario, @RequestParam String contrasena, Model model, HttpSession session) {
        Usuario usuarioEncontrado = usuarioService.buscarPorUsuario(usuario).orElse(null);

        if (usuarioEncontrado == null) {
            model.addAttribute("error", "Usuario no encontrado.");
            return "/login";
        } else if (!usuarioEncontrado.getContrasena().equals(contrasena)) {
            model.addAttribute("error", "Contraseña incorrecta.");
            return "/login";
        }

        session.setAttribute("usuarioLogueado", usuarioEncontrado);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/cuenta/editar")
    public String usuarioModificar(@RequestParam("usuario") String nombreUsuario,
            @RequestParam("cedula") String cedula,
            @RequestParam("direccion") String direccion,
            @RequestParam("nacimiento") String nacimiento,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("imagen") MultipartFile imagenFile,
            Model model) {

        var sesion = usuarioSesion.obtenerUsuarioLogueado();

        // Actualizar los campos modificables
        sesion.setUsuario(nombreUsuario);
        sesion.setCedula(cedula);
        sesion.setDireccion(direccion);
        sesion.setNacimiento(nacimiento);
        sesion.setEmail(email);
        sesion.setPhoneNumber(phoneNumber);

        // Subir imagen si se seleccionó una nueva
        if (imagenFile != null && !imagenFile.isEmpty()) {
            String url = firebaseStorageService.cargaImagen(imagenFile, "usuarios", sesion.getId());
            sesion.setRutaImagen(url);
        }

        // Guardar en la base de datos
        usuarioService.guardar(sesion);

        // Actualizar la sesión
        usuarioSesion.establecerUsuarioLogueado(sesion);
        model.addAttribute("session", sesion);

        return "redirect:/cuenta";
    }

    @PostMapping("/cuenta/borrar-foto")
    public String borrarFotoPerfil() {
        var sesion = usuarioSesion.obtenerUsuarioLogueado();
        sesion.setRutaImagen(null); // Borrar la foto
        usuarioService.guardar(sesion);
        usuarioSesion.establecerUsuarioLogueado(sesion);
        return "redirect:/cuenta";
    }

}
