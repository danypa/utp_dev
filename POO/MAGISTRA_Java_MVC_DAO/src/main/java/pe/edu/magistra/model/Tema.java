package pe.edu.magistra.model;

public class Tema {

    private int idTema, idCategoria;
    private String nombre, descripcion;
    private boolean estado;

    public Tema() {
    }

    public Tema(int id, int cat, String n, String d, boolean e) {
        idTema = id;
        idCategoria = cat;
        nombre = n;
        descripcion = d;
        estado = e;
    }

    public int getIdTema() {
        return idTema;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
