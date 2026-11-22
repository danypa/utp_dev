package pe.edu.magistra.dao;

import pe.edu.magistra.model.Recurso;
import java.util.*;

public interface RecursoDAO {

    List<Recurso> listarActivos() throws Exception;
}
