package com.ProyectoWeb.service;

import com.ProyectoWeb.domain.Columna;
import com.ProyectoWeb.domain.Datos;
import com.ProyectoWeb.domain.Tabla;
import com.ProyectoWeb.domain.Usuario;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TablaService {

    List<Tabla> listarTablasPorUsuario(Long usuarioId);

    Tabla guardar(Tabla tabla);

int contarTablasActivasPorUsuario(Long usuarioId);

    Tabla crearTablaConColumnas(String nombreTabla, String descripcionTabla, List<String> nombresColumnas, List<String> tiposColumnas, Long usuarioId);

    void actualizarTabla(Tabla tabla);

    Optional<Tabla> obtenerPorId(Long id);

    Columna guardarColumna(Columna columna);

    List<Columna> obtenerColumnasPorTablaId(Long tablaId);

    Columna obtenerColumnaPorId(Long id);

    Columna actualizarColumna(Columna columna);

    void eliminarColumna(Long columnaId);

    void eliminarTablaConDatos(Long id);

    List<Tabla> listarTablasActivasPorUsuario(Long usuarioId);

    List<String> obtenerNombresTablas();

    List<String> buscarTablas(String query);

    List<Tabla> obtenerTodasLasTablas();

    List<Tabla> buscarTablasPorNombre(String nombre);

    List<Tabla> obtenerTablasPorClienteYEstado(Usuario usuario, String query);

    List<Datos> obtenerDatosPorTablaId(Long tablaId);

    Datos guardarDato(Datos dato);

    void eliminarDato(Long datoId);

    void ocultarTabla(Long id);

    void borrarFila(Long filaId);

    void editarFila(Long filaId, Map<String, String> valores);

}
