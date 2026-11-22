package pe.edu.magistra.dao.impl;

import pe.edu.magistra.dao.RecursoDAO;
import pe.edu.magistra.model.Recurso;
import pe.edu.magistra.util.ConexionBD;
import java.sql.*;
import java.util.*;

public class RecursoDAOImpl implements RecursoDAO {

    public List<Recurso> listarActivos() throws Exception {
        List<Recurso> l = new ArrayList<>();
        try (Connection c = ConexionBD.getConexion(); Statement s = c.createStatement(); ResultSet r = s.executeQuery("SELECT id_recurso,id_categoria,titulo,descripcion,tipo,url,estado FROM recursos WHERE estado=1 ORDER BY titulo")) {
            while (r.next()) {
                Integer cat = (Integer) r.getObject(2);
                l.add(new Recurso(r.getInt(1), cat, r.getString(3), r.getString(4), r.getString(5), r.getString(6), r.getBoolean(7)));
            }
        }
        return l;
    }
}
