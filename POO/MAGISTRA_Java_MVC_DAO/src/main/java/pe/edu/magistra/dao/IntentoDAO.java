package pe.edu.magistra.dao;

import pe.edu.magistra.model.*;
import java.util.*;

public interface IntentoDAO {

    long guardarCompleto(IntentoPractica intento, List<RespuestaUsuario> respuestas, Resultado resultado) throws Exception;

    List<IntentoPractica> listarPorUsuario(int idUsuario) throws Exception;

    Optional<Resultado> resultadoDe(long idIntento) throws Exception;

    String nombreTemaDe(long idIntento) throws Exception;
}
