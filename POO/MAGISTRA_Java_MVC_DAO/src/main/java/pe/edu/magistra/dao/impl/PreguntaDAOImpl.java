package pe.edu.magistra.dao.impl;

import pe.edu.magistra.dao.PreguntaDAO;
import pe.edu.magistra.model.*;
import pe.edu.magistra.util.ConexionBD;
import java.sql.*;
import java.util.*;

public class PreguntaDAOImpl implements PreguntaDAO {

    public List<Pregunta> listarAleatoriasPorTema(int tema, int cantidad) throws Exception {
        List<Pregunta> l = new ArrayList<>();
        String q = "SELECT id_pregunta,id_tema,enunciado,explicacion,dificultad,estado FROM preguntas WHERE id_tema=? AND estado=1 ORDER BY RAND() LIMIT ?";
        try (Connection c = ConexionBD.getConexion(); PreparedStatement p = c.prepareStatement(q)) {
            p.setInt(1, tema);
            p.setInt(2, cantidad);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    PreguntaOpcionMultiple x = new PreguntaOpcionMultiple(r.getInt(1), r.getInt(2), r.getString(3), r.getString(4), r.getString(5), r.getBoolean(6));
                    x.setAlternativas(alts(c, x.getIdPregunta()));
                    l.add(x);
                }
            }
        }
        return l;
    }

    private List<Alternativa> alts(Connection c, int id) throws SQLException {
        List<Alternativa> l = new ArrayList<>();
        try (PreparedStatement p = c.prepareStatement("SELECT id_alternativa,id_pregunta,texto,correcta,orden FROM alternativas WHERE id_pregunta=? ORDER BY orden")) {
            p.setInt(1, id);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    l.add(new Alternativa(r.getInt(1), r.getInt(2), r.getString(3), r.getBoolean(4), r.getInt(5)));
                }
            }
        }
        return l;
    }
}
