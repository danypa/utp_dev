package pe.edu.magistra.model;

public class Categoria {

    private int idCategoria;
    private String nombre, descripcion;
    private boolean estado;

    public Categoria() {
    }

    public Categoria(int id, String n, String d, boolean e) {
        idCategoria = id;
        nombre = n;
        descripcion = d;
        estado = e;
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
