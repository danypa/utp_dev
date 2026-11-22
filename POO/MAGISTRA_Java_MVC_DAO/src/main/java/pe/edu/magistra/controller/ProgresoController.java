package pe.edu.magistra.controller;

import pe.edu.magistra.dao.ProgresoDAO;
import pe.edu.magistra.model.Progreso;
import pe.edu.magistra.util.Session;
import java.util.*;

public class ProgresoController {

    private final ProgresoDAO d;

    public ProgresoController(ProgresoDAO d) {
        this.d = d;
    }

    public List<Progreso> listar() throws Exception {
        return d.listarPorUsuario(Session.get().getIdUsuario());
    }
}
