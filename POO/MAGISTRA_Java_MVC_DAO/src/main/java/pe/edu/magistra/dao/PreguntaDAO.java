package pe.edu.magistra.dao;

import pe.edu.magistra.model.Pregunta;
import java.util.*;

public interface PreguntaDAO {

    List<Pregunta> listarAleatoriasPorTema(int idTema, int cantidad) throws Exception;
}
