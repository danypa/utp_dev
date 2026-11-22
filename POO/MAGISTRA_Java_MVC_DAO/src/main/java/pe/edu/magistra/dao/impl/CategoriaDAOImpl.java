package pe.edu.magistra.dao.impl;

import pe.edu.magistra.dao.CategoriaDAO;
import pe.edu.magistra.model.Categoria;
import pe.edu.magistra.util.ConexionBD;
import java.sql.*;
import java.util.*;

public class CategoriaDAOImpl implements CategoriaDAO {

    private Categoria m(ResultSet r) throws SQLException {
        return new Categoria(r.getInt(1), r.getString(2), r.getString(3), r.getBoolean(4));
    }

    public List<Categoria> listar() throws Exception {
        List<Categoria> l = new ArrayList<>();
        try (Connection c = ConexionBD.getConexion(); Statement s = c.createStatement(); ResultSet r = s.executeQuery("SELECT id_categoria,nombre,descripcion,estado FROM categorias WHERE estado=1 ORDER BY nombre")) {
            while (r.next()) {
                l.add(m(r));
            }
        }
        return l;
    }

    public Optional<Categoria> buscarPorId(long id) throws Exception {
        try (Connection c = ConexionBD.getConexion(); PreparedStatement p = c.prepareStatement("SELECT id_categoria,nombre,descripcion,estado FROM categorias WHERE id_categoria=?")) {
            p.setLong(1, id);
            try (ResultSet r = p.executeQuery()) {
                return r.next() ? Optional.of(m(r)) : Optional.empty();
            }
        }
    }

    public boolean insertar(Categoria o) {
        throw new UnsupportedOperationException();
    }

    public boolean actualizar(Categoria o) {
        throw new UnsupportedOperationException();
    }

    public boolean eliminar(long id) {
        throw new UnsupportedOperationException();
    }
}
