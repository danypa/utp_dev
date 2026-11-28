package pe.edu.magistra.model;

import java.io.Serial;
import java.io.Serializable;

public class Recurso implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int idRecurso;
    private Integer idCategoria;
    private String titulo, descripcion, tipo, url;
    private boolean estado;

    public Recurso(int id, Integer c, String t, String d, String ti, String u, boolean e) {
        idRecurso = id;
        idCategoria = c;
        titulo = t;
        descripcion = d;
        tipo = ti;
        url = u;
        estado = e;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
