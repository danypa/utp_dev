package pe.edu.magistra.dao.impl;

import pe.edu.magistra.dao.ProgresoDAO;
import pe.edu.magistra.model.Progreso;
import pe.edu.magistra.util.ConexionBD;
import java.sql.*;
import java.util.*;

public class ProgresoDAOImpl implements ProgresoDAO {

    public void recalcular(int u, int t) throws Exception {
        String q = "INSERT INTO progreso(id_usuario,id_tema,total_intentos,mejor_puntaje,promedio_puntaje,mejor_tiempo_segundos) SELECT ?,?,COUNT(*),MAX(r.puntaje),AVG(r.puntaje),MIN(r.tiempo_total_segundos) FROM intentos_practica i JOIN resultados r ON r.id_intento=i.id_intento WHERE i.id_usuario=? AND i.id_tema=? AND i.estado='FINALIZADO' ON DUPLICATE KEY UPDATE total_intentos=VALUES(total_intentos),mejor_puntaje=VALUES(mejor_puntaje),promedio_puntaje=VALUES(promedio_puntaje),mejor_tiempo_segundos=VALUES(mejor_tiempo_segundos)";
        try (Connection c = ConexionBD.getConexion(); PreparedStatement p = c.prepareStatement(q)) {
            p.setInt(1, u);
            p.setInt(2, t);
            p.setInt(3, u);
            p.setInt(4, t);
            p.executeUpdate();
        }
    }

    public List<Progreso> listarPorUsuario(int u) throws Exception {
        List<Progreso> l = new ArrayList<>();
        String q = "SELECT p.id_progreso,p.id_usuario,p.id_tema,t.nombre,p.total_intentos,p.mejor_puntaje,p.promedio_puntaje,p.mejor_tiempo_segundos FROM progreso p JOIN temas t ON t.id_tema=p.id_tema WHERE p.id_usuario=? ORDER BY t.nombre";
        try (Connection c = ConexionBD.getConexion(); PreparedStatement p = c.prepareStatement(q)) {
            p.setInt(1, u);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    Integer mt = (Integer) r.getObject(8);
                    l.add(new Progreso(r.getLong(1), r.getInt(2), r.getInt(3), r.getString(4), r.getInt(5), r.getDouble(6), r.getDouble(7), mt));
                }
            }
        }
        return l;
    }
}
