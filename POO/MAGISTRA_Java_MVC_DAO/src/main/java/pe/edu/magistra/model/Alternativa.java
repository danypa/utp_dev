package pe.edu.magistra.model;

import java.io.Serial;
import java.io.Serializable;

public class Alternativa implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int idAlternativa, idPregunta, orden;
    private String texto;
    private boolean correcta;

    public Alternativa(int id, int p, String t, boolean c, int o) {
        idAlternativa = id;
        idPregunta = p;
        texto = t;
        correcta = c;
        orden = o;
    }

    public int getIdAlternativa() {
        return idAlternativa;
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public String getTexto() {
        return texto;
    }

    public boolean isCorrecta() {
        return correcta;
    }

    public int getOrden() {
        return orden;
    }

    @Override
    public String toString() {
        return texto;
    }
}
