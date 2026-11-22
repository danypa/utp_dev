package pe.edu.magistra.model;

import java.time.LocalDateTime;

public abstract class Usuario {

    protected int idUsuario;
    protected String nombres, apellidos, correo, passwordHash;
    protected LocalDateTime fechaRegistro;
    protected boolean estado;

    public Usuario() {
    }

    public Usuario(int id, String n, String a, String c, String ph, LocalDateTime f, boolean e) {
        idUsuario = id;
        nombres = n;
        apellidos = a;
        correo = c;
        passwordHash = ph;
        fechaRegistro = f;
        estado = e;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int v) {
        idUsuario = v;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String v) {
        nombres = v;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String v) {
        apellidos = v;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String v) {
        correo = v;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String v) {
        passwordHash = v;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime v) {
        fechaRegistro = v;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean v) {
        estado = v;
    }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}
