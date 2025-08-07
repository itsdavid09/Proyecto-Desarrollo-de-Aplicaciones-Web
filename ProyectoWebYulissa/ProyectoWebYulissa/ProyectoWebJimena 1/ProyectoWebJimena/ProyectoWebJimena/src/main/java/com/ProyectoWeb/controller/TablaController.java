package com.ProyectoWeb.controller;

import com.ProyectoWeb.dao.DatosDao;
import com.ProyectoWeb.domain.Columna;
import com.ProyectoWeb.domain.Datos;
import com.ProyectoWeb.domain.Tabla;
import com.ProyectoWeb.domain.Usuario;
import com.ProyectoWeb.service.TablaService;
import com.ProyectoWeb.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tabla")
public class TablaController {

    @Autowired
    private TablaService tablaService;
    
        @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DatosDao datosDao;

    @GetMapping("/ver/{tablaId}")
    public String verTabla(@PathVariable Long tablaId, Model model) {
        Tabla tabla = tablaService.obtenerPorId(tablaId).orElse(null);

        if (tabla != null) {
            List<Columna> columnas = tablaService.obtenerColumnasPorTablaId(tablaId);

            List<Map<String, String>> filas = new ArrayList<>();
            for (Columna columna : columnas) {
                List<Datos> datosColumna = datosDao.findByColumnaId(columna.getId());

                for (int i = 0; i < datosColumna.size(); i++) {
                    Datos dato = datosColumna.get(i);

                    while (filas.size() <= i) {
                        filas.add(new HashMap<>());
                    }

                    if ("Activo".equals(dato.getEstado())) {
                        filas.get(i).put(columna.getNombre(), dato.getValor());
                        filas.get(i).put("id", String.valueOf(dato.getId()));
                    }
                }
            }

            filas = filas.stream()
                    .filter(fila -> fila.values().stream()
                    .anyMatch(valor -> valor != null && !valor.isEmpty()))
                    .collect(Collectors.toList());

            model.addAttribute("tabla", tabla);
            model.addAttribute("columnas", columnas);
            model.addAttribute("filas", filas);
        }

        return "tabla/verTabla";
    }

    @PostMapping("/agregarDatos")
    public String agregarDatos(@RequestParam Long tablaId, @RequestParam Map<String, String> valores) {
        Optional<Tabla> tablaOpt = tablaService.obtenerPorId(tablaId);
        if (tablaOpt.isPresent()) {
            Tabla tabla = tablaOpt.get();

            List<Columna> columnas = tablaService.obtenerColumnasPorTablaId(tablaId);
            for (Columna columna : columnas) {
                String valor = valores.get(columna.getNombre());
                if (valor != null) {
                    Datos dato = new Datos();
                    dato.setTabla(tabla);
                    dato.setColumna(columna);
                    dato.setValor(valor);
                    datosDao.save(dato);
                }
            }
        }
        return "redirect:/tabla/ver/" + tablaId;
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Long id, Model model) {
        Tabla tabla = tablaService.obtenerPorId(id).orElse(null);
        if (tabla != null) {
            model.addAttribute("tabla", tabla);
            model.addAttribute("columnas", tabla.getColumnas());
            return "tabla/formularioEdicion";
        }
        return "redirect:/tabla/listado";
    }

    @GetMapping("/buscarTablas")
    public String buscarTablas(@RequestParam(value = "query", required = false, defaultValue = "") String query, 
                                HttpSession session, Model model) {
        // Recuperar el usuario logueado desde la sesión
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login"; // Redirigir a la página de login si no hay usuario logueado
        }

        // Obtener las tablas filtradas
        List<Tabla> tablas = tablaService.obtenerTablasPorClienteYEstado(usuario, query);
        model.addAttribute("tablas", tablas);  // Añadir las tablas al modelo

        return "tabla/listado";  // Devolver la vista de listado
    }


    @PostMapping("/actualizar")
    public String actualizarTabla(@ModelAttribute("tabla") Tabla tabla, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }

        Tabla tablaExistente = tablaService.obtenerPorId(tabla.getId()).orElse(null);
        if (tablaExistente != null) {
            tablaExistente.setNombre(tabla.getNombre());
            tablaExistente.setDescripcion(tabla.getDescripcion());
            tablaExistente.setUsuario(usuario);
            tablaService.actualizarTabla(tablaExistente);
        }
        return "redirect:/tabla/listado";
    }

    @GetMapping("/listado")
    public String listarTablas(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }

        List<Tabla> tablas = tablaService.listarTablasActivasPorUsuario(usuario.getId());
        model.addAttribute("tablas", tablas);
        return "tabla/listado";
    }

@PostMapping("/crear")
public String crearTabla(
        @RequestParam String nombreTabla,
        @RequestParam String descripcionTabla,
        @RequestParam("nombreColumna[]") List<String> nombreColumnas,
        @RequestParam("tipoColumna[]") List<String> tipoColumnas,
        HttpSession session,
        RedirectAttributes redirectAttributes) {

    // Cargar el usuario logueado desde la base de datos
    Usuario usuario = usuarioService.obtenerUsuarioPorId(((Usuario) session.getAttribute("usuarioLogueado")).getId());
    if (usuario == null) {
        return "redirect:/login";
    }

    // Determinar el límite de tablas permitidas según el plan
    int limiteTablasPermitido;
    switch (usuario.getPlan()) {
        case "BASE":
            limiteTablasPermitido = 1;
            break;
        case "ORO":
            limiteTablasPermitido = 5;
            break;
        case "PLATINO":
            limiteTablasPermitido = 10;
            break;
        default:
            limiteTablasPermitido = 0; // Caso inesperado
    }

    // Contar solo tablas con estado "Activo"
    int numeroActualTablas = tablaService.contarTablasActivasPorUsuario(usuario.getId());

    if (numeroActualTablas >= limiteTablasPermitido) {
        redirectAttributes.addFlashAttribute("error", "Has alcanzado el límite de tablas permitido para tu plan.");
        return "redirect:/tabla/listado";
    }

    // Crear la tabla si no se alcanzó el límite
    tablaService.crearTablaConColumnas(nombreTabla, descripcionTabla, nombreColumnas, tipoColumnas, usuario.getId());
    return "redirect:/tabla/listado";
}


    @GetMapping("/editarColumnas/{tablaId}")
    public String editarColumnas(@PathVariable Long tablaId, Model model) {
        Tabla tabla = tablaService.obtenerPorId(tablaId).orElse(null);
        if (tabla == null) {
            return "redirect:/tabla/listado";
        }

        model.addAttribute("tabla", tabla);
        model.addAttribute("columnas", tabla.getColumnas());
        return "tabla/formularioEdicion";
    }

    @PostMapping("/actualizarColumnas")
    public String actualizarColumnas(@RequestParam Map<String, String> columnasData) {
        for (Map.Entry<String, String> entry : columnasData.entrySet()) {
            String key = entry.getKey();
            if (key.startsWith("columnas[")) {
                Long columnaId = Long.valueOf(key.substring(key.indexOf('[') + 1, key.indexOf(']')));
                Columna columna = tablaService.obtenerColumnaPorId(columnaId);
                if (columna != null) {
                    if (key.endsWith(".nombre")) {
                        columna.setNombre(entry.getValue());
                    } else if (key.endsWith(".tipoDato")) {
                        columna.setTipoDato(entry.getValue());
                    }
                    tablaService.actualizarColumna(columna);
                }
            }
        }
        return "redirect:/tabla/listado";
    }

    @PostMapping("/eliminarColumna/{columnaId}")
    public String eliminarColumna(@PathVariable Long columnaId) {
        tablaService.eliminarColumna(columnaId);
        return "redirect:/tabla/listado";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarTabla(@PathVariable("id") Long id) {
        tablaService.eliminarTablaConDatos(id);
        return "redirect:/tabla/listado";
    }

    @PostMapping("/tabla/eliminarDato")
    public String eliminarDato(@RequestParam Long datoId, @RequestParam Long tablaId) {
        tablaService.eliminarDato(datoId);
        return "redirect:/tabla/ver/" + tablaId;
    }

    @GetMapping("/ocultar/{id}")
    public String ocultarTabla(@PathVariable("id") Long id) {
        tablaService.ocultarTabla(id);
        return "redirect:/tabla/listado";
    }

    @PostMapping("/borrar")
    public String borrarFila(@RequestParam Long filaId, @RequestParam Long tablaId) {
        Datos datos = datosDao.findById(filaId).orElse(null);
        if (datos != null) {
            datos.setEstado("Inactivo");
            datosDao.save(datos);
        }
        return "redirect:/tabla/ver/" + tablaId;
    }

    @PostMapping("/editar")
    public String editarFila(
            @RequestParam Long filaId,
            @RequestParam Map<String, String> valores,
            @RequestParam Long tablaId) {

        tablaService.editarFila(filaId, valores);
        return "redirect:/tabla/ver/" + tablaId;
    }

}
