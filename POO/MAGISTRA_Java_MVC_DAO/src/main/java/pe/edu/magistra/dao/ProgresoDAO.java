package pe.edu.magistra.dao;

import pe.edu.magistra.model.*;
import java.util.*;

public interface ProgresoDAO {

    void recalcular(int idUsuario, int idTema) throws Exception;

    List<Progreso> listarPorUsuario(int idUsuario) throws Exception;
}
