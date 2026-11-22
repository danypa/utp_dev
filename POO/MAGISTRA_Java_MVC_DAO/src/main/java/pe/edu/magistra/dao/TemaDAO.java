package pe.edu.magistra.dao;

import pe.edu.magistra.model.Tema;
import java.util.*;

public interface TemaDAO extends CrudDAO<Tema> {

    List<Tema> listarPorCategoria(int idCategoria) throws Exception;
}
