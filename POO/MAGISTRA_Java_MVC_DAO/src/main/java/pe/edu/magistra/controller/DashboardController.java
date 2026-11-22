package pe.edu.magistra.controller;

import pe.edu.magistra.dao.*;
import pe.edu.magistra.model.*;
import java.util.*;

public class DashboardController {

    private final CategoriaDAO c;
    private final TemaDAO t;

    public DashboardController(CategoriaDAO c, TemaDAO t) {
        this.c = c;
        this.t = t;
    }

    public List<Categoria> categorias() throws Exception {
        return c.listar();
    }

    public List<Tema> temas(int cat) throws Exception {
        return t.listarPorCategoria(cat);
    }
}
