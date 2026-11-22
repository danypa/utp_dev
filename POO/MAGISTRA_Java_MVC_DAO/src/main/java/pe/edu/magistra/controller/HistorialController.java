package pe.edu.magistra.controller;

import pe.edu.magistra.dao.IntentoDAO;
import pe.edu.magistra.model.*;
import pe.edu.magistra.util.Session;
import java.util.*;

public class HistorialController {

    private final IntentoDAO d;

    public HistorialController(IntentoDAO d) {
        this.d = d;
    }

    public List<IntentoPractica> intentos() throws Exception {
        return d.listarPorUsuario(Session.get().getIdUsuario());
    }

    public Optional<Resultado> resultado(long id) throws Exception {
        return d.resultadoDe(id);
    }

    public String tema(long id) throws Exception {
        return d.nombreTemaDe(id);
    }
}
