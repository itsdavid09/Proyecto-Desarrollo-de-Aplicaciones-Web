package com.ProyectoWeb.service.impl;

import com.ProyectoWeb.dao.DatosDao;
import com.ProyectoWeb.dao.ColumnaDao;
import com.ProyectoWeb.dao.TablaDao;
import com.ProyectoWeb.domain.Columna;
import com.ProyectoWeb.domain.Datos;
import com.ProyectoWeb.domain.Tabla;
import com.ProyectoWeb.domain.Usuario;
import com.ProyectoWeb.service.TablaService;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TablaServiceImpl implements TablaService {

    @Autowired
    private TablaDao tablaDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ColumnaDao columnaDao;

    @Autowired
    private DatosDao datosDao;

    @Override
    public List<Tabla> listarTablasPorUsuario(Long usuarioId) {
        return tablaDao.findByUsuarioId(usuarioId);
    }

    @Override
    public Tabla guardar(Tabla tabla) {
        return tablaDao.save(tabla);
    }

    @Override
    public List<Tabla> obtenerTodasLasTablas() {
        return tablaDao.findAll();
    }

    @Override
    public List<Tabla> buscarTablasPorNombre(String nombre) {
        return tablaDao.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Tabla> obtenerTablasPorClienteYEstado(Usuario usuario, String query) {
        return tablaDao.findByUsuarioAndEstadoAndNombreContainingIgnoreCase(usuario, "Activo", query);
    }

    @Override
    public Optional<Tabla> obtenerPorId(Long id) {
        return tablaDao.findById(id);
    }

    @Override
    public int contarTablasActivasPorUsuario(Long usuarioId) {
        return tablaDao.contarTablasActivasPorUsuario(usuarioId);
    }

    @Override
    public Tabla crearTablaConColumnas(String nombreTabla, String descripcionTabla, List<String> nombresColumnas, List<String> tiposColumnas, Long usuarioId) {
        Tabla tabla = new Tabla();

        tabla.setNombre(nombreTabla);
        tabla.setDescripcion(descripcionTabla);

        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        tabla.setUsuario(usuario);

        tabla = tablaDao.save(tabla);

        for (int i = 0; i < nombresColumnas.size(); i++) {
            Columna columna = new Columna();
            columna.setNombre(nombresColumnas.get(i));
            columna.setTipoDato(tiposColumnas.get(i));
            columna.setTabla(tabla);
            columnaDao.save(columna);
        }

        return tabla;
    }

    @Override
    public void actualizarTabla(Tabla tabla) {
        Optional<Tabla> tablaExistenteOpt = obtenerPorId(tabla.getId());
        if (tablaExistenteOpt.isPresent()) {
            Tabla tablaExistente = tablaExistenteOpt.get();
            tablaExistente.setNombre(tabla.getNombre());
            tablaExistente.setDescripcion(tabla.getDescripcion());
            tablaDao.save(tablaExistente);
        }
    }

    @Override
    public Columna guardarColumna(Columna columna) {
        return columnaDao.save(columna);
    }

    @Override
    public List<Columna> obtenerColumnasPorTablaId(Long tablaId) {
        return columnaDao.findByTablaId(tablaId);
    }

    @Override
    public Columna obtenerColumnaPorId(Long id) {
        return columnaDao.findById(id).orElse(null);
    }

    @Override
    public Columna actualizarColumna(Columna columna) {
        Optional<Columna> columnaOpt = columnaDao.findById(columna.getId());
        if (columnaOpt.isPresent()) {
            Columna columnaExistente = columnaOpt.get();
            columnaExistente.setNombre(columna.getNombre());
            columnaExistente.setTipoDato(columna.getTipoDato());
            return columnaDao.save(columnaExistente);
        }
        return null;
    }

    @Override
    public void eliminarColumna(Long columnaId) {
        columnaDao.deleteById(columnaId);
    }

    @Override
    public void eliminarTablaConDatos(Long id) {

        datosDao.deleteByColumnaId(id);

        columnaDao.deleteByTablaId(id);

        tablaDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> buscarTablas(String query) {
        List<String> nombresTablas = obtenerNombresTablas();
        if (query != null && !query.isEmpty()) {
            nombresTablas = nombresTablas.stream()
                    .filter(nombre -> nombre.toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return nombresTablas;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> obtenerNombresTablas() {
        List<String> nombresTablas = new ArrayList<>();
        try {
            DatabaseMetaData metaData = jdbcTemplate.getDataSource().getConnection().getMetaData();
            ResultSet rs = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            while (rs.next()) {
                nombresTablas.add(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombresTablas;
    }

    @Override
    public List<Datos> obtenerDatosPorTablaId(Long tablaId) {
        return datosDao.findByTablaId(tablaId);
    }

    @Override
    public Datos guardarDato(Datos dato) {
        return datosDao.save(dato);
    }

    @Override
    public void eliminarDato(Long datoId) {
        datosDao.deleteById(datoId);
    }

    @Override
    public void ocultarTabla(Long id) {
        Optional<Tabla> tablaOpt = tablaDao.findById(id);
        if (tablaOpt.isPresent()) {
            Tabla tabla = tablaOpt.get();
            tabla.setEstado("Inactivo");
            tablaDao.save(tabla);
        }
    }

    @Override
    public List<Tabla> listarTablasActivasPorUsuario(Long usuarioId) {
        return tablaDao.findByUsuarioIdAndEstado(usuarioId, "Activo");
    }

    @Override
    public void borrarFila(Long filaId) {
        Datos datos = datosDao.findById(filaId).orElse(null);
        if (datos != null) {
            datos.setEstado("Inactivo");
            datosDao.save(datos);
        }
    }

    @Override
    public void editarFila(Long filaId, Map<String, String> valores) {
        Datos fila = datosDao.findById(filaId).orElseThrow(() -> new RuntimeException("Fila no encontrada"));

        for (Map.Entry<String, String> entry : valores.entrySet()) {
            if (!entry.getKey().equals("filaId") && !entry.getKey().equals("tablaId")) {
                fila.setValor(entry.getValue());
            }
        }

        datosDao.save(fila);
    }

}
