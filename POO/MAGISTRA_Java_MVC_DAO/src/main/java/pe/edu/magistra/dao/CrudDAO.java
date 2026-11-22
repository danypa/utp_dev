package pe.edu.magistra.dao;

import java.util.*;

public interface CrudDAO<T> {

    boolean insertar(T obj) throws Exception;

    boolean actualizar(T obj) throws Exception;

    boolean eliminar(long id) throws Exception;

    Optional<T> buscarPorId(long id) throws Exception;

    List<T> listar() throws Exception;
}
