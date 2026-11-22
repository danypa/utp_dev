package pe.edu.magistra.dao;

import pe.edu.magistra.model.Postulante;
import java.util.Optional;

public interface UsuarioDAO extends CrudDAO<Postulante> {

    Optional<Postulante> buscarPorCorreo(String correo) throws Exception;
}
