package com.ProyectoWeb.service.impl;

import com.ProyectoWeb.domain.Registro;
import com.ProyectoWeb.service.RegistroService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RegistroServiceImpl implements RegistroService {

    @PersistenceContext
    private EntityManager entityManager;

    // Método para guardar un nuevo usuario
    @Override
    @Transactional
    public Registro guardar(Registro registro) {
        entityManager.persist(registro);
        return registro;
    }

    // Método para buscar un usuario por su nombre de usuario
    @Override
    @Transactional(readOnly = true)
    public Optional<Registro> buscarPorUsuario(String usuario) {
        String jpql = "SELECT r FROM Registro r WHERE r.usuario = :usuario";
        Query query = entityManager.createQuery(jpql, Registro.class);
        query.setParameter("usuario", usuario);
        return query.getResultStream().findFirst();
    }
    
    // Método para verificar si ya existe un usuario o cédula en la base de datos
    @Override
    @Transactional(readOnly = true)
    public boolean existeUsuarioOCedula(String usuario, String cedula) {
        String jpql = "SELECT COUNT(r) FROM Registro r WHERE r.usuario = :usuario OR r.cedula = :cedula";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("usuario", usuario);
        query.setParameter("cedula", cedula);
        Long count = (Long) query.getSingleResult();
        return count > 0;
    }

    // Método para registrar un nuevo usuario y verificar si existe duplicado (usuario o cédula)
    @Override
    @Transactional
    public String registrarUsuario(Registro registro) {
        if (existeUsuarioOCedula(registro.getUsuario(), registro.getCedula())) {
            return "Error: Ya existe un usuario o cédula con esos datos.";
        }
        guardar(registro);
        return "Registro exitoso.";
    }
}
