package pe.edu.magistra.controller;

import pe.edu.magistra.dao.UsuarioDAO;
import pe.edu.magistra.model.Postulante;
import pe.edu.magistra.util.*;
import java.time.LocalDateTime;

public class AuthController {

    private final UsuarioDAO dao;

    public AuthController(UsuarioDAO d) {
        dao = d;
    }

    public Postulante login(String correo, String pass) throws Exception {
        Postulante u = dao.buscarPorCorreo(correo.trim().toLowerCase()).orElseThrow(() -> new IllegalArgumentException("Correo o contraseña incorrectos."));
        if (!PasswordUtils.verify(pass, u.getPasswordHash())) {
            throw new IllegalArgumentException("Correo o contraseña incorrectos.");
        }
        Session.iniciar(u);
        return u;
    }

    public void registrar(String n, String a, String correo, String pass, String esp, String region) throws Exception {
        if (n.isBlank() || a.isBlank() || correo.isBlank()) {
            throw new IllegalArgumentException("Completa tus nombres, apellidos y correo electrónico.");
        }
        if (pass.length() < 6) {
            throw new IllegalArgumentException("Usa una contraseña de al menos 6 caracteres.");
        }
        if (dao.buscarPorCorreo(correo.trim().toLowerCase()).isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado.");
        }
        Postulante u = new Postulante(0, n.trim(), a.trim(), correo.trim().toLowerCase(), PasswordUtils.hash(pass), esp.trim(), region.trim(), LocalDateTime.now(), true);
        dao.insertar(u);
    }

    public void logout() {
        Session.cerrar();
    }
}
