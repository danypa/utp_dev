package pe.edu.magistra.dao.impl;

import pe.edu.magistra.dao.IntentoDAO;
import pe.edu.magistra.model.*;
import pe.edu.magistra.util.ConexionBD;
import java.sql.*;
import java.util.*;

public class IntentoDAOImpl implements IntentoDAO {

    public long guardarCompleto(IntentoPractica i, List<RespuestaUsuario> rs, Resultado res) throws Exception {
        Connection c = ConexionBD.getConexion();
        try {
            c.setAutoCommit(false);
            long id;
            try (PreparedStatement p = c.prepareStatement("INSERT INTO intentos_practica(id_usuario,id_tema,fecha_inicio,fecha_fin,limite_segundos,tiempo_total_segundos,estado) VALUES(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
                p.setInt(1, i.getIdUsuario());
                p.setInt(2, i.getIdTema());
                p.setTimestamp(3, Timestamp.valueOf(i.getFechaInicio()));
                p.setTimestamp(4, Timestamp.valueOf(i.getFechaFin()));
                p.setInt(5, i.getLimiteSegundos());
                p.setInt(6, i.getTiempoTotalSegundos());
                p.setString(7, "FINALIZADO");
                p.executeUpdate();
                try (ResultSet g = p.getGeneratedKeys()) {
                    g.next();
                    id = g.getLong(1);
                }
            }
            try (PreparedStatement p = c.prepareStatement("INSERT INTO respuestas_usuario(id_intento,id_pregunta,id_alternativa,correcta,tiempo_segundos,fecha_respuesta) VALUES(?,?,?,?,?,?)")) {
                for (RespuestaUsuario r : rs) {
                    p.setLong(1, id);
                    p.setInt(2, r.getIdPregunta());
                    if (r.getIdAlternativa() == 0) {
                        p.setNull(3, Types.INTEGER);
                    } else {
                        p.setInt(3, r.getIdAlternativa());
                    }
                    p.setBoolean(4, r.isCorrecta());
                    p.setInt(5, r.getTiempoSegundos());
                    p.setTimestamp(6, Timestamp.valueOf(r.getFechaRespuesta()));
                    p.addBatch();
                }
                p.executeBatch();
            }
            try (PreparedStatement p = c.prepareStatement("INSERT INTO resultados(id_intento,correctas,incorrectas,total_preguntas,puntaje,porcentaje,tiempo_total_segundos) VALUES(?,?,?,?,?,?,?)")) {
                p.setLong(1, id);
                p.setInt(2, res.getCorrectas());
                p.setInt(3, res.getIncorrectas());
                p.setInt(4, res.getTotalPreguntas());
                p.setDouble(5, res.getPuntaje());
                p.setDouble(6, res.getPorcentaje());
                p.setInt(7, res.getTiempoTotalSegundos());
                p.executeUpdate();
            }
            c.commit();
            return id;
        } catch (Exception e) {
            c.rollback();
            throw e;
        } finally {
            c.close();
        }
    }

    public List<IntentoPractica> listarPorUsuario(int u) throws Exception {
        List<IntentoPractica> l = new ArrayList<>();
        String q = "SELECT * FROM intentos_practica WHERE id_usuario=? AND estado='FINALIZADO' ORDER BY fecha_inicio DESC";
        try (Connection c = ConexionBD.getConexion(); PreparedStatement p = c.prepareStatement(q)) {
            p.setInt(1, u);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    IntentoPractica i = new IntentoPractica();
                    i.setIdIntento(r.getLong("id_intento"));
                    i.setIdUsuario(u);
                    i.setIdTema(r.getInt("id_tema"));
                    i.setFechaInicio(r.getTimestamp("fecha_inicio").toLocalDateTime());
                    i.setFechaFin(r.getTimestamp("fecha_fin").toLocalDateTime());
                    i.setTiempoTotalSegundos(r.getInt("tiempo_total_segundos"));
                    i.setEstado(r.getString("estado"));
                    l.add(i);
                }
            }
        }
        return l;
    }

    public Optional<Resultado> resultadoDe(long id) throws Exception {
        try (Connection c = ConexionBD.getConexion(); PreparedStatement p = c.prepareStatement("SELECT * FROM resultados WHERE id_intento=?")) {
            p.setLong(1, id);
            try (ResultSet r = p.executeQuery()) {
                if (!r.next()) {
                    return Optional.empty();
                }
                Resultado x = new Resultado();
                x.setIdResultado(r.getLong("id_resultado"));
                x.setIdIntento(id);
                x.setCorrectas(r.getInt("correctas"));
                x.setIncorrectas(r.getInt("incorrectas"));
                x.setTotalPreguntas(r.getInt("total_preguntas"));
                x.setPuntaje(r.getDouble("puntaje"));
                x.setPorcentaje(r.getDouble("porcentaje"));
                x.setTiempoTotalSegundos(r.getInt("tiempo_total_segundos"));
                return Optional.of(x);
            }
        }
    }

    public String nombreTemaDe(long id) throws Exception {
        try (Connection c = ConexionBD.getConexion(); PreparedStatement p = c.prepareStatement("SELECT t.nombre FROM intentos_practica i JOIN temas t ON t.id_tema=i.id_tema WHERE i.id_intento=?")) {
            p.setLong(1, id);
            try (ResultSet r = p.executeQuery()) {
                return r.next() ? r.getString(1) : "";
            }
        }
    }
}
