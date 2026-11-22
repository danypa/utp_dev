package pe.edu.magistra.dao.impl;

import pe.edu.magistra.dao.UsuarioDAO;
import pe.edu.magistra.model.Postulante;
import pe.edu.magistra.util.ConexionBD;
import java.sql.*;
import java.util.*;

public class UsuarioDAOImpl implements UsuarioDAO {

    private Postulante map(ResultSet r) throws SQLException {
        return new Postulante(r.getInt("id_usuario"), r.getString("nombres"), r.getString("apellidos"), r.getString("correo"), r.getString("password_hash"), r.getString("especialidad"), r.getString("region"), r.getTimestamp("fecha_registro").toLocalDateTime(), r.getBoolean("estado"));
    }

    public boolean insertar(Postulante u) throws Exception {
        String s = "INSERT INTO usuarios(nombres,apellidos,correo,password_hash,especialidad,region) VALUES(?,?,?,?,?,?)";
        try (Connection c = ConexionBD.getConexion(); PreparedStatement p = c.prepareStatement(s)) {
            p.setString(1, u.getNombres());
            p.setString(2, u.getApellidos());
            p.setString(3, u.getCorreo());
            p.setString(4, u.getPasswordHash());
            p.setString(5, u.getEspecialidad());
            p.setString(6, u.getRegion());
            return p.executeUpdate() == 1;
        }
    }

    public Optional<Postulante> buscarPorCorreo(String correo) throws Exception {
        String s = "SELECT * FROM usuarios WHERE correo=? AND estado=1";
        try (Connection c = ConexionBD.getConexion(); PreparedStatement p = c.prepareStatement(s)) {
            p.setString(1, correo);
            try (ResultSet r = p.executeQuery()) {
                return r.next() ? Optional.of(map(r)) : Optional.empty();
            }
        }
    }

    public Optional<Postulante> buscarPorId(long id) throws Exception {
        try (Connection c = ConexionBD.getConexion(); PreparedStatement p = c.prepareStatement("SELECT * FROM usuarios WHERE id_usuario=?")) {
            p.setLong(1, id);
            try (ResultSet r = p.executeQuery()) {
                return r.next() ? Optional.of(map(r)) : Optional.empty();
            }
        }
    }

    public List<Postulante> listar() throws Exception {
        List<Postulante> l = new ArrayList<>();
        try (Connection c = ConexionBD.getConexion(); Statement s = c.createStatement(); ResultSet r = s.executeQuery("SELECT * FROM usuarios")) {
            while (r.next()) {
                l.add(map(r));
            }
        }
        return l;
    }

    public boolean actualizar(Postulante u) throws Exception {
        try (Connection c = ConexionBD.getConexion(); PreparedStatement p = c.prepareStatement("UPDATE usuarios SET nombres=?,apellidos=?,especialidad=?,region=? WHERE id_usuario=?")) {
            p.setString(1, u.getNombres());
            p.setString(2, u.getApellidos());
            p.setString(3, u.getEspecialidad());
            p.setString(4, u.getRegion());
            p.setInt(5, u.getIdUsuario());
            return p.executeUpdate() == 1;
        }
    }

    public boolean eliminar(long id) throws Exception {
        try (Connection c = ConexionBD.getConexion(); PreparedStatement p = c.prepareStatement("UPDATE usuarios SET estado=0 WHERE id_usuario=?")) {
            p.setLong(1, id);
            return p.executeUpdate() == 1;
        }
    }
}
