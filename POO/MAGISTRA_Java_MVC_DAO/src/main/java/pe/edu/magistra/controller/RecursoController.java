package pe.edu.magistra.controller;

import pe.edu.magistra.dao.RecursoDAO;
import pe.edu.magistra.model.Recurso;
import java.util.*;

public class RecursoController {

    private final RecursoDAO d;

    public RecursoController(RecursoDAO d) {
        this.d = d;
    }

    public List<Recurso> listar() throws Exception {
        return d.listarActivos();
    }
}
